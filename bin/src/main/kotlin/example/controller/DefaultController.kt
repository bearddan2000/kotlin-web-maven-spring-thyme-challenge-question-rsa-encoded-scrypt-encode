package example.controller

import example.service.ChallengeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller

@Controller
class DefaultController {

  @Autowired
  private lateinit var challengeService: ChallengeService;

  @GetMapping("/")
  fun index(): String {
      return "/home"
  }

  @GetMapping("/home")
  fun home(): String {
      return "/home"
  }

  @GetMapping("/admin")
  fun admin(): String {
      return "/admin"
  }

  @GetMapping("/super")
  fun super1(): String {
      return "/super"
  }

  @GetMapping("/user")
  fun user(): String {
      return "/user"
  }

  @GetMapping("/about")
  fun about(): String {
      return "/about"
  }

  @GetMapping("/login")
  fun login(): String  {
      return "/login"
  }

  @GetMapping("/403")
  fun error403(): String {
      return "/error/403"
  }

	@GetMapping("/register")
	fun register(model: Model): String
	{
    model.addAttribute("challengeQuestionList", challengeService.findAllQuestions());
		return "/register";
	}

	@GetMapping("/reset")
	fun reset(model: Model): String
	{
    model.addAttribute("challengeQuestionList", challengeService.findAllQuestions());
		return "/reset";
	}
}
