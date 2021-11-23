package example.controller;

import example.model.User;
import example.model.Challenge;
import example.service.SecurityService;
import example.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
class RestDefaultController {

  @Autowired
  private lateinit var securityService :SecurityService

  @Autowired
  private lateinit var userService :UserService

  @Autowired
  private lateinit var passwordEncoder: PasswordEncoder

  @GetMapping("/register")
  fun register(@RequestParam("username") username: String
  , @RequestParam("password") password: String
  , @RequestParam("question") question: Int
  , @RequestParam("answer") answer: String): String {

      val user: User = User(username
      , passwordEncoder.encode(password)
      , Challenge(question, answer))

      userService.save(user);

      securityService.autoLogin(user.username, user.password);

      println("[LOG] user redirected")
      return "user";
  }

  @GetMapping("/reset")
  fun reset(@RequestParam("username") username: String
  , @RequestParam("password") password: String
  , @RequestParam("question") question: Int
  , @RequestParam("answer") answer: String): String {

      val user: User? = userService.resetPassword(username, password);

      if (user == null) {
      println("[LOG] user not found")
        return "register";
      }

      if (user.challenge.question != question
      || user.challenge.answer != answer) {
      println("[LOG] challenge not met")
       return "reset";
      }

      securityService.autoLogin(user.user, user.pass);

      println("[LOG] user redirected")
      return "user";
  }
}
