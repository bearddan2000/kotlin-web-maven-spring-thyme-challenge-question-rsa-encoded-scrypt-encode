package example.model;

import java.util.ArrayList;

data class Challenge(
  var question: Int
  , var answer: String
){
  constructor(): this(0, "")

  companion object Challenge {
    fun listQuestions(): ArrayList<String>
    {
      val list = ArrayList<String>();
      list.add("Year you were born?")
      list.add("Last 4 digits of your SSN?")

      return list;
    }
  }
}
