/**
 * 
 */
package com.teqnihome.service.qdsl;

import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.QuerydslRepositoryInvokerAdapter;
import org.springframework.data.repository.support.RepositoryInvoker;

import com.mysema.commons.lang.Assert;
import com.mysema.query.types.Predicate;

/**
 * @author vkandula
 *
 * http://stackoverflow.com/questions/39741927/create-a-collection-resource-with-sort-and-querydsl-predicate-but-no-pageable?rq=1
 */
public class CustomQuerydslRepositoryInvokerAdapter extends QuerydslRepositoryInvokerAdapter {

	private final QueryDslPredicateExecutor<Object> executor;
	private final Predicate predicate;

	public CustomQuerydslRepositoryInvokerAdapter(RepositoryInvoker delegate,
			QueryDslPredicateExecutor<Object> executor, Predicate predicate) {
		super(delegate, executor, predicate);

		Assert.notNull(delegate, "Delegate RepositoryInvoker must not be null!");
		Assert.notNull(executor, "QuerydslPredicateExecutor must not be null!");

		this.executor = executor;
		this.predicate = predicate;
	}

	@Override
	public Iterable<Object> invokeFindAll(Pageable pageable) {
		return executor.findAll(predicate, pageable.getSort());
	}

}
