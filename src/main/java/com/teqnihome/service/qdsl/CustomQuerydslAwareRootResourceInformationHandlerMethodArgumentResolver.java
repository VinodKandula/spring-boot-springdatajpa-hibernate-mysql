/**
 * 
 */
package com.teqnihome.service.qdsl;

import java.util.Arrays;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.data.repository.support.Repositories;
import org.springframework.data.repository.support.RepositoryInvoker;
import org.springframework.data.repository.support.RepositoryInvokerFactory;
import org.springframework.data.rest.webmvc.config.ResourceMetadataHandlerMethodArgumentResolver;
import org.springframework.data.rest.webmvc.config.RootResourceInformationHandlerMethodArgumentResolver;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.mysema.query.types.Predicate;

/**
 * @author vkandula
 *
 */
public class CustomQuerydslAwareRootResourceInformationHandlerMethodArgumentResolver extends RootResourceInformationHandlerMethodArgumentResolver {

	private final Repositories repositories;
	private final QuerydslPredicateBuilder predicateBuilder;
	private final QuerydslBindingsFactory factory;

	public CustomQuerydslAwareRootResourceInformationHandlerMethodArgumentResolver(Repositories repositories,
			RepositoryInvokerFactory invokerFactory,
			ResourceMetadataHandlerMethodArgumentResolver resourceMetadataResolver,
			QuerydslPredicateBuilder predicateBuilder, QuerydslBindingsFactory factory) {

		super(repositories, invokerFactory, resourceMetadataResolver);

		this.repositories = repositories;
		this.predicateBuilder = predicateBuilder;
		this.factory = factory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.rest.webmvc.config.
	 * RootResourceInformationHandlerMethodArgumentResolver#postProcess(org.
	 * springframework.data.repository.support.RepositoryInvoker,
	 * java.lang.Class, java.util.Map)
	 */
	@Override
	@SuppressWarnings({ "unchecked" })
	protected RepositoryInvoker postProcess(MethodParameter parameter, RepositoryInvoker invoker, Class<?> domainType,
			Map<String, String[]> parameters) {

		Object repository = repositories.getRepositoryFor(domainType);

		if (!CustomQueryDslPredicateExecutor.class.isInstance(repository)
				|| !parameter.hasParameterAnnotation(QuerydslPredicate.class)) {
			return invoker;
		}

		ClassTypeInformation<?> type = ClassTypeInformation.from(domainType);

		QuerydslBindings bindings = factory.createBindingsFor(null, type);
		Predicate predicate = predicateBuilder.getPredicate(type, toMultiValueMap(parameters), bindings);

		return new CustomQuerydslRepositoryInvokerAdapter(invoker, (QueryDslPredicateExecutor<Object>) repository,
				predicate);
	}

	private static MultiValueMap<String, String> toMultiValueMap(Map<String, String[]> source) {

		MultiValueMap<String, String> result = new LinkedMultiValueMap<String, String>();

		for (String key : source.keySet()) {
			result.put(key, Arrays.asList(source.get(key)));
		}

		return result;
	}

}
