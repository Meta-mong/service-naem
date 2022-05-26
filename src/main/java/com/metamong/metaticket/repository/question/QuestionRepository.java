package com.metamong.metaticket.repository.question;

import com.metamong.metaticket.domain.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
