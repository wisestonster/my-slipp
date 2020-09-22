package com.wisestone.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wisestone.domain.Answer;
import com.wisestone.domain.AnswerRepository;
import com.wisestone.domain.Question;
import com.wisestone.domain.QuestionRepository;
import com.wisestone.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@PostMapping("")
	public String create(@PathVariable Long questionId, String contents, HttpSession session) {
		Object sessionedUser = session.getAttribute("sessionedUser");

		if (sessionedUser == null) {
			System.out.println("조회권한이 없습니다.");
			return "redirect:/users/loginForm";
		}
		
		User user = (User) sessionedUser;
		Question question = questionRepository.findById(questionId).get();
		
		Answer answer = new Answer(user, question, contents);
		
		answerRepository.save(answer);
		
		return String.format("redirect:/questions/%d", questionId);
	}
}
