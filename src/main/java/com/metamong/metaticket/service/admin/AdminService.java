package com.metamong.metaticket.service.admin;

import com.metamong.metaticket.domain.admin.Admin;


import javax.servlet.http.HttpSession;

public interface AdminService {

    public boolean adminLogin (String adminId, String password) throws Exception;
    public void adminLogout (HttpSession session);

    public Admin adminInfo (String adminId);

}
