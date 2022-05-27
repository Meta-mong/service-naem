package com.metamong.metaticket.service.user;

import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.domain.user.dto.UserDTO;
import com.metamong.metaticket.repository.user.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@PropertySource("classpath:application-sms.yml")
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public UserRepository userRepository;

    @Value("${serviceId}")
    public String serviceId;

    @Value("${accessKey}")
    public String accessKey;

    @Value("${secretKey}")
    public String secretKey;

    @Value("${from}")
    public String from;

    @Override
    public boolean emailCheck(String email) {
        boolean result = false; //중복된 이메일 없음
        User user = null;
        try{
            user = userRepository.findByEmail(email);
            if(user!= null) result = true; //중복된 이메일 있음
        } catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean phoneNumberCheck(String number) {
        boolean result = false; //중복된 전화번호 없음
        User user = null;
        try{
            user = userRepository.findByNumber(number);
            if(user!=null) result = true; //중복된 전화번호 있음
        } catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /*
    @Override
    public boolean sendSms(String userNumber, int authNumber) {
        System.out.println("accessKey : "+this.accessKey);
        boolean result = false; //default = 실패
        String time = String.valueOf(System.currentTimeMillis());
        String accessKey = this.accessKey;

        //System.out.println("인증 번호 : "+authNumber);
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
        bodyJson.put("content", "인증번호 전송"); //둘 중 하나 제거해도 될 듯 -> to가 우선순위가 높음
        bodyJson.put("messages", toArr);

        String body = bodyJson.toJSONString();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type", "application/json");
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
     */

    @Override
    public boolean hashPasswdCheck(User user, String passwd) {
        //비밀번호 일치시 true / 일치하지 않을시 false 리턴
        boolean correct = BCrypt.checkpw("3241", user.getPasswd());
        return correct;
    }

    @Override
    public String inquireEmail(String name, String number) throws Exception{
        User user = userRepository.findByNameAndNumber(name, number);
        if(user == null) throw new Exception("회원 정보가 없습니다.");
        return user.getEmail();
    }

    @Override
    public boolean modifyPasswd(HttpSession session, String passwd) {
        UserDTO userDTO = (UserDTO)session.getAttribute("user");
        User user = userRepository.findById(userDTO.getId()).get();
        user.passwordEncode(passwd);
        User modifiedUser = userRepository.save(user);
        if(userDTO.getId()==modifiedUser.getId())return true;
        return false;
    }

    @Override
    public boolean SignUp() {
        return false;
    }

    @Override
    public UserDTO login(String email, String passwd) {
        return null;
    }
}
