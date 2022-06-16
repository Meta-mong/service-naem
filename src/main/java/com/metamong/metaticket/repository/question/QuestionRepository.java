package com.metamong.metaticket.repository.question;

import com.metamong.metaticket.domain.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
