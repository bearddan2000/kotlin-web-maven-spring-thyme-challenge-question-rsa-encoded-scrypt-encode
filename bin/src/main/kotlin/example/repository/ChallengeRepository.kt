package example.repository;

import java.util.ArrayList;
import org.springframework.stereotype.Repository;

@Repository("challengeRepository")
interface ChallengeRepository {
  fun findAllQuestions(): ArrayList<String>;
  fun findByQuestion(questionId: Int): String;
}
