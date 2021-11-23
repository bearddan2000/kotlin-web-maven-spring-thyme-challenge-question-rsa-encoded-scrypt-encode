package example.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
class ChallengeRepositoryImpl : ChallengeRepository {

    override fun findAllQuestions(): ArrayList<String> = example.model.Challenge.listQuestions()

    override fun findByQuestion(questionId: Int): String {
      val list = this.findAllQuestions()
      return list.get(questionId)
    }
}
