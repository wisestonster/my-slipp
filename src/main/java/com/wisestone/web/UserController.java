package com.wisestone.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wisestone.domain.User;
import com.wisestone.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
//	private List<User> users = new ArrayList<User>();
	
	@Autowired
	private UserRepository userRepository;
	
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
}
