/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iset.member.service;

import com.iset.member.dao.MemberDao;
import com.iset.member.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 시스템명 : 아이셋 (S/W)
 * 업무구분 : 모바일 서버
 * 업 무 명 : 회원 서비스
 * 작 성 자 : 김현욱
 * 작 성 일 : 2018.9.14
 * 설     명 :
 * -------------------------------------------------------------------------------
 * 변경일            변경자  변경내역
 * -------------------------------------------------------------------------------
 * </pre>
 */
@Service
public class MemberService {

	@Autowired
	private MemberDao memberDao;

	public Map searchCustomer(Long customerId) throws Exception {
		Map map = new HashMap();
		if(customerId != null) map.put("edit", memberDao.findById(customerId));
		else map.put("list", memberDao.findAll());
		return map;
	}

	public String saveCustomer(Customer customer) throws Exception {
		customer.setStoreId("1");
		customer.setAddressId("1");
		if(customer.getCustomerId() == 0) customer.setCustomerId(null);
		memberDao.save(customer);
		return "/list";
	}

	public String deleteCustomer(Long customerId) throws Exception {
		memberDao.deleteById(customerId);
		return "/list";
	}
}
