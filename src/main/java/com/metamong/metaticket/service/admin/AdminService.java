package com.metamong.metaticket.service.admin;

import com.metamong.metaticket.domain.admin.Admin;


import javax.servlet.http.HttpSession;

public interface AdminService {

    public boolean adminlogin (String adminId, String password) throws Exception;
    public void adminlogout (HttpSession session);

    public Admin adminInfo (String adminId);

}
