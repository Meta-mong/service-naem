package com.metamong.metaticket.repository.admin;

import com.metamong.metaticket.domain.admin.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    Admin findByLoginId(String login_id);



}
