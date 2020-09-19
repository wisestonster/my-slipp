package com.wisestone.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wisestone.domain.Question;
import com.wisestone.domain.QuestionRepository;
import com.wisestone.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;
	
	@PostMapping("")
	public String create(String title, String contents, HttpSession session)
	{
		Object sessionedUser = session.getAttribute("sessionedUser");

		if (sessionedUser == null) {
			return "redirect:/users/loginForm";
		}
		
		User user = (User) sessionedUser;
		Question newQuestion = new Question(user, title, contents);
		
		questionRepository.save(newQuestion);
		
		return "redirect:/";
	}

	
	@GetMapping("/form")
	public String form(HttpSession session) {
		
		Object sessionedUser = session.getAttribute("sessionedUser");

		if (sessionedUser == null) {
			return "redirect:/users/loginForm";
		}
		
		return "/qna/form";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute("question", questionRepository.findById(id).get());
		
		return "/qna/show";	
	}

}
