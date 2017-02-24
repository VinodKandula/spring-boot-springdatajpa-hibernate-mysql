/**
 * 
 */
package com.teqnihome.service.qdsl;

import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.mysema.query.types.Predicate;

/**
 * @author vkandula
 *
 */
public interface CustomQueryDslPredicateExecutor<T> extends QueryDslPredicateExecutor<T> {
	
	Iterable<T> findAll(Predicate predicate, Sort sort);

}
