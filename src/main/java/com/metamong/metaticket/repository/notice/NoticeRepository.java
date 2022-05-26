package com.metamong.metaticket.repository.notice;

import com.metamong.metaticket.domain.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
}
