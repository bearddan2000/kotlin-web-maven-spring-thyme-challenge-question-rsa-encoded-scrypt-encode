package example.service;

import example.repository.ChallengeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
class ChallengeServiceImpl : ChallengeService {

    @Autowired
    private lateinit var challengeRepository: ChallengeRepository

    override fun findAllQuestions(): ArrayList<String> = challengeRepository.findAllQuestions()

    override fun findByQuestion(questionId: Int): String {
      return challengeRepository.findByQuestion(questionId);
    }

}
