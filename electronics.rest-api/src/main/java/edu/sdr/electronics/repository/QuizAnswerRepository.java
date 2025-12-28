package edu.sdr.electronics.repository;

import edu.sdr.electronics.domain.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {
}
