package com.metamong.metaticket.service.user;

import com.metamong.metaticket.domain.log.Log;
import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.domain.user.dto.UserDTO;
import com.metamong.metaticket.repository.log.LogRepository;
import com.metamong.metaticket.repository.user.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpSession session;

    @Value("${serviceId}")
    private String serviceId;

    @Value("${accessKey}")
    private String accessKey;

    @Value("${secretKey}")
    private String secretKey;

    @Value("${from}")
    private String from;

    @Override
    public boolean emailCheck(String email) {
        String parsedEmail = email.trim();
        boolean result = false; //중복된 이메일 없음
        User user = userRepository.findByEmail(parsedEmail);
        if(user!= null) result = true; //중복된 이메일 있음

        return result;
    }

    @Override
    public boolean phoneNumberCheck(String number) {
        String parsedNumber = number.trim();
        boolean result = false; //중복된 전화번호 없음
        User user = userRepository.findByNumber(parsedNumber);
        if(user!=null) result = true; //중복된 전화번호 있음

        return result;
    }

    @Override
    public boolean sendSms(String userNumber, int authNumber) {
        boolean result = false; //default = 실패
        String time = String.valueOf(System.currentTimeMillis());
        String accessKey = this.accessKey;

        String serviceId = this.serviceId;
        String from = this.from; //등록한 번호만 사용 가능
        String to = userNumber;
        String subject = "[meta_ticket 인증]"; //기본 메시지 제목
        String apiUrl = "https://sens.apigw.ntruss.com/sms/v2/services/"+serviceId+"/messages";

        JSONObject bodyJson = new JSONObject();
        JSONObject toJson = new JSONObject();
        JSONArray toArr = new JSONArray();

        toJson.put("to", to);
        toJson.put("content", "인증번호("+authNumber+") 입력시 인증완료");
        toArr.add(toJson);

        bodyJson.put("type", "SMS");
        bodyJson.put("contentType", "COMM");
        bodyJson.put("countryCode", "82");
        bodyJson.put("from", from);
        bodyJson.put("subject", subject);
        bodyJson.put("content", "인증번호 전송"); //to가 우선순위가 높음
        bodyJson.put("messages", toArr);

        String body = bodyJson.toJSONString();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type", "application/json; charset=utf-8");
            conn.setRequestProperty("x-ncp-apigw-timestamp", time);
            conn.setRequestProperty("x-ncp-iam-access-key", accessKey);
            conn.setRequestProperty("x-ncp-apigw-signature-v2", getSignature(time));

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            dos.write(body.getBytes());
            dos.flush();
            dos.close();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            BufferedReader br;
            if(responseCode==202){
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = true; //성공
            } else{
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                result = false; //실패
            }

            String inputLine;
            StringBuilder response = new StringBuilder();
            while((inputLine = br.readLine()) != null){
                response.append(inputLine);
            }
            br.close();

            System.out.println(response.toString());

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException ie){

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public String getSignature(String time) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String serviceId = this.serviceId;
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/" + serviceId + "/messages";

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(time)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);

        return encodeBase64String;
    }

    @Override
    public boolean passwdCheck(String passwd, User user) {
        //비밀번호 일치시 true / 일치하지 않을시 false 리턴
        return passwordEncoder.matches(passwd, user.getPasswd());
    }

    @Override
    public String inquireEmail(UserDTO.FIND_EMAIL dto) throws Exception{
        User user = userRepository.findByNameAndNumber(dto.getName(), dto.getNumber());
        if(user == null) throw new Exception("회원 정보가 없습니다.");
        return user.getEmail();
    }

    @Override
    @Transactional
    public boolean modifyPasswd(HttpSession session, String passwd) {
        //인증과정은 생략됨
        UserDTO.SESSION_USER_DATA userDTO = (UserDTO.SESSION_USER_DATA)session.getAttribute("user");
        userDTO.setPasswd(passwd);
        User user = User.createUser(userDTO, passwordEncoder);
        user.setId(userDTO.getId());
        User modifiedUser = userRepository.save(user);
        if(userDTO.getId()==modifiedUser.getId())return true;
        return false;
    }

    @Override
    @Transactional
    public boolean signUp(UserDTO.SIGN_UP userDTO) { //return type 변경
        //view에서 받은 param들을 Controller에서 UserDTO객체로 받아 넘겨주는 것이 선행되어야 함
        //email이나 passwd 제약은 정규식으로 front단에서 체크

        //이메일 중복 체크
        if(emailCheck(userDTO.getEmail())==true) return false;
        //전화번호 중복체크 -> 일차척으로 front단에서 전화인증 성공 못하면 넘어올 수 없음
        if(phoneNumberCheck(userDTO.getNumber())==true) return false;

        User inputUser = userRepository.save(User.createUser(userDTO ,passwordEncoder));
        System.out.println(inputUser.toString());

        return true;
    }

    @Override
    @Transactional
    public int signIn(UserDTO.SIGN_IN dto, HttpSession session) {
        //0 : 로그인 실패
        //1 : 로그인 성공
        //2 : 탈퇴한 회원 다시 계정 살릴지 ask
        //-1 : 탈퇴한 계정입니다.


        //Controller 단에서 userDTO가 null인지 확인해서 처리
        User user = userRepository.findByEmail(dto.getEmail());
        UserDTO.SESSION_USER_DATA userDTO = null;

        if(user==null) {
            System.out.println("계정 정보 없음");
            return 0;
        }
        boolean passwdCheck = passwdCheck(dto.getPasswd(), user);
        System.out.println("valid 확인 : "+ user.isValid());
        if(passwdCheck==true) {
            if(user.isValid()==false){ //추후 수정 -> user
                userDTO = User.createUserDTO(user);
                System.out.println("패스워드 일치");
                session.setAttribute("user", userDTO);
                Log log = Log.builder()
                        .visitDate(LocalDateTime.now())
                        .user(user)
                        .build();
                Log inputLog = logRepository.save(log);
                System.out.println(inputLog.toString());
                return 1;
            }else if(user.getValid_date().isAfter(LocalDateTime.now())) { //접속일이 회원 정보 유지 유효기간 내 일시
                return 2;
            }else {
                return -1; //회원 정보 복구할 수 있는 기간을 지남
            }
        }else{
            System.out.println("패스워드 불일치");
            return 0;
        }
    }

    @Override
    public void signOut(HttpSession session) {
        session.invalidate();
    }


}
