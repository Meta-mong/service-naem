package com.metamong.metaticket.service.admin;

import com.metamong.metaticket.domain.admin.Admin;
import com.metamong.metaticket.repository.admin.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public boolean adminLogin(String loginId, String password) throws Exception {
        boolean result = false;
        try {
            Admin admin = adminRepository.findByLoginId(loginId);
            if(admin.getPassword().equals(password)){
                result = true; //로그인 성공
            }
        }catch (Exception e){

        }
        // 로그인 결과 보냄
        return result;
    }

    @Override
    public Admin adminInfo(String loginId) {
        return adminRepository.findByLoginId(loginId);
    }


    @Override
    public void adminLogout(HttpSession session) {
    session.invalidate(); //세션 자름



    }
}
