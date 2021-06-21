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
package com.iset.member;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

/**
 * Simple repository interface for {@link Customer} instances. The interface is used to declare so called query methods,
 * methods to retrieve single entities or collections of them.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Christoph Strobl
 */
public interface MemberRepository extends CrudRepository<Customer, Long> {


	/**
	 * Find all users with the given lastname. This method will be translated into a query by constructing it directly
	 * from the method name as there is no other query declared.
	 *
	 * @param lastName
	 * @return
	 */
	List<Customer> findByLastName(String lastName);

	/**
	 * Returns all users with the given firstname. This method will be translated into a query using the one declared in
	 * the {@link Query} annotation declared one.
	 *
	 * @param firstName
	 * @return
	 */
	@Query("select u from Customer u where u.firstName = :firstName")
	List<Customer> findByFirstName(@Param("firstName") String firstName);

	/**
	 * Returns all users with the given name as first- or lastname. This makes the query to method relation much more
	 * refactoring-safe as the order of the method parameters is completely irrelevant.
	 *
	 * @param name
	 * @return
	 */
	@Query("select u from Customer u where u.firstName = :name or u.lastName = :name")
	List<Customer> findByFirstNameOrLastName(@Param("name") String name);

	/**
	 * Returns the total number of entries deleted as their lastnames match the given one.
	 *
	 * @param lastName
	 * @return
	 */
	Long removeByLastName(String lastName);

	/**
	 * Returns a {@link Slice} counting a maximum number of {@link Pageable#getPageSize()} users matching given criteria
	 * starting at {@link Pageable#getOffset()} without prior count of the total number of elements available.
	 *
	 * @param lastName
	 * @param page
	 * @return
	 */
	Slice<Customer> findByLastNameOrderByUserNameAsc(String lastName, Pageable page);

	/**
	 * Return the first 2 users ordered by their lastname asc.
	 *
	 * <pre>
	 * Example for findFirstK / findTopK functionality.
	 * </pre>
	 *
	 * @return
	 */
	List<Customer> findFirst2ByOrderByLastNameAsc();

	/**
	 * Return the first 2 users ordered by the given {@code sort} definition.
	 *
	 * <pre>
	 * This variant is very flexible because one can ask for the first K results when a ASC ordering
	 * is used as well as for the last K results when a DESC ordering is used.
	 * </pre>
	 *
	 * @param sort
	 * @return
	 */
	List<Customer> findTop2By(Sort sort);

	/**
	 * Return all the users with the given firstname or lastname. Makes use of SpEL (Spring Expression Language).
	 *
	 * @param customer
	 * @return
	 */
	@Query("select u from Customer u where u.firstName = :#{#customer.firstName} or u.lastName = :#{#customer.lastName}")
	Iterable<Customer> findByFirstNameOrLastName(Customer customer);

	/**
	 * Sample default method.
	 *
	 * @param customer
	 * @return
	 */
	default List<Customer> findByLastName(Customer customer) {
		return findByLastName(customer == null ? null : customer.getLastName());
	}

	/**
	 * Sample method to demonstrate support for {@link Stream} as a return type with a custom query. The query is executed
	 * in a streaming fashion which means that the method returns as soon as the first results are ready.
	 *
	 * @return
	 */
	@Query("select u from Customer u")
	Stream<Customer> streamAllCustomers();

	/**
	 * Sample method to demonstrate support for {@link Stream} as a return type with a derived query. The query is
	 * executed in a streaming fashion which means that the method returns as soon as the first results are ready.
	 *
	 * @return
	 */
	Stream<Customer> findAllByLastNameIsNotNull();

	@Async
	CompletableFuture<List<Customer>> readAllBy();

}
