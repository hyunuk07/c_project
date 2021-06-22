package com.iset.member;

import com.iset.member.entity.Customer;
import com.iset.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 회원 관련
 */
@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/hello")
	public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "greeting";
	}

	@RequestMapping({"/edit/{customerId}", "/list"})
	public Object searchCustomer(@PathVariable(name="customerId", required=false) Long customerId) throws Exception {
		return memberService.searchCustomer(customerId);
	}

	@RequestMapping({"/save"})
	public String saveCustomer(@RequestBody Customer customer) throws Exception {
		customer.setStoreId("1"); // 더미값
		customer.setAddressId("1"); // 더미값
		memberService.saveCustomer(customer);
		return "/list";
	}

	@RequestMapping({"/delete/{customerId}"})
	public String deleteCustomer(@PathVariable(name="customerId", required=false) Long customerId) throws Exception {
		memberService.deleteCustomer(customerId);
		return "/list";
	}
}