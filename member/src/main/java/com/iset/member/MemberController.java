package com.iset.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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

	@RequestMapping({"/list/{username}", "/list/"})
	public Object searchCustomer(@PathVariable(name="username", required=false) String username) throws Exception {

		Customer customer = new Customer();
		customer.setLastName(username);
		customer.setFirstName(username);
		Iterable<Customer> customerList = memberRepository.findByFirstNameOrLastName(customer);

		Map map = new HashMap();
		map.put("list",  customerList.iterator().hasNext() ? customerList : memberRepository.findAll());
		map.put("userName",customer.getUserName());
		return map;
	}
}