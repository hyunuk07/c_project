package com.iset.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 회원 관련
 */
@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired // This means to get the bean called userRepository
	private MemberRepository memberRepository;

	@RequestMapping("/hello")
	public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "greeting";
	}

	@RequestMapping({"/edit/{customerId}", "/list"})
	public Object searchCustomer(@PathVariable(name="customerId", required=false) Long customerId) throws Exception {
		Map map = new HashMap();

		if(customerId != null) map.put("edit", memberRepository.findById(customerId));
		else map.put("list", memberRepository.findAll());

		return map;
	}

	@RequestMapping({"/save"})
	public String saveCustomer(Customer customer) throws Exception {
		memberRepository.save(customer);
		return "/list/";
	}
}