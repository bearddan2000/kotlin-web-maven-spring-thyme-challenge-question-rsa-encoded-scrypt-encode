package example.service;

import java.util.ArrayList;

interface ChallengeService {
  fun findAllQuestions(): ArrayList<String>;
  fun findByQuestion(questionId: Int): String;
}
