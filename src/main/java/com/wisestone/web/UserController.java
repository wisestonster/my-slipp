package com.wisestone.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wisestone.domain.User;
import com.wisestone.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
//	private List<User> users = new ArrayList<User>();
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/loginForm")
	public String loginForm() {

		return "/user/login";
	}

	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session)
	{
		User user = userRepository.findByUserId(userId);
		
		if (user == null) {
			System.out.println("해당 사용자가 없습니다.");
			return "redirect:/users/loginForm";
		}
		
		if (!password.equals(user.comparePassword())) {
			System.out.println("비밀번호 입력 오류");
			return "redirect:/users/loginForm";
		}

		session.setAttribute("sessionedUser", user);
		System.out.println("로그인성공:" + user.toString());
		
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionedUser");
		
		return "redirect:/";
	}

	
	@GetMapping("/form")
	public String form() {

		return "/user/form";
	}
	
	@PostMapping("")
	public String create(User user) {
		System.out.println("userId: " + user.toString());
//		users.add(user);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String list(Model model) {
//		model.addAttribute("users", users);
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {

		Object sessionedUser = session.getAttribute("sessionedUser");

		if (sessionedUser == null) {
			return "redirect:/users/loginForm";
		}
		User user = userRepository.findById(id).get();

		// model 객체가 있으면, 다시 추가하면 오류 발생.
		if (model.equals(user)) {
			model.addAttribute("sessionedUser", user); 
		}
		
//		model.addAttribute("user", userRepository.findOne(id));
		
		return "/user/updateForm";
	}

	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User newUser) {
		User user = userRepository.findById(id).get();
		
		user.update(newUser);
		userRepository.save(user);
		
		return "redirect:/users";
	}
}
