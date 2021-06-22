/*
 * Copyright 2013-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iset.member.dao;

import com.iset.member.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * Simple repository interface for {@link Customer} instances. The interface is used to declare so called query methods,
 * methods to retrieve single entities or collections of them.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Christoph Strobl
 */
public interface MemberDao extends CrudRepository<Customer, Long> {

	/**
	 * @param lastName
	 * @return
	 */
	List<Customer> findByLastName(String lastName);

	/**
	 * @param firstName
	 * @return
	 */
	@Query("select u from Customer u where u.firstName = :firstName")
	List<Customer> findByFirstName(@Param("firstName") String firstName);

	/**
	 * @param name
	 * @return
	 */
	@Query("select u from Customer u where u.firstName = :name or u.lastName = :name")
	List<Customer> findByFirstNameOrLastName(@Param("name") String name);

	/**
	 * @param lastName
	 * @return
	 */
	Long removeByLastName(String lastName);

	/**
	 * @param lastName
	 * @param page
	 * @return
	 */
	Slice<Customer> findByLastNameOrderByUserNameAsc(String lastName, Pageable page);

	/**
	 * @return
	 */
	List<Customer> findFirst2ByOrderByLastNameAsc();

	/**
	 * @param sort
	 * @return
	 */
	List<Customer> findTop2By(Sort sort);

	/**
	 * @param customer
	 * @return
	 */
	@Query("select u from Customer u where u.firstName = :#{#customer.firstName} or u.lastName = :#{#customer.lastName}")
	Iterable<Customer> findByFirstNameOrLastName(Customer customer);

	/**
	 * @param customer
	 * @return
	 */
	default List<Customer> findByLastName(Customer customer) {
		return findByLastName(customer == null ? null : customer.getLastName());
	}

	/**
	 * @return
	 */
	@Query("select u from Customer u")
	Stream<Customer> streamAllCustomers();

	/**
	 * @return
	 */
	Stream<Customer> findAllByLastNameIsNotNull();

	@Async
	CompletableFuture<List<Customer>> readAllBy();

}
