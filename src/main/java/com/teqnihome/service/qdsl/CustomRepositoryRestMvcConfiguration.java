/**
 * 
 */
package com.teqnihome.service.qdsl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.querydsl.QueryDslUtils;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.data.rest.webmvc.config.RootResourceInformationHandlerMethodArgumentResolver;

/**
 * @author vkandula
 *
 */
public class CustomRepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration {

	@Autowired
	ApplicationContext applicationContext;

	@Override
	@Bean
	public RootResourceInformationHandlerMethodArgumentResolver repoRequestArgumentResolver() {

		if (QueryDslUtils.QUERY_DSL_PRESENT) {

			QuerydslBindingsFactory factory = applicationContext.getBean(QuerydslBindingsFactory.class);
			QuerydslPredicateBuilder predicateBuilder = new QuerydslPredicateBuilder(defaultConversionService(),
					factory.getEntityPathResolver());

			return new CustomQuerydslAwareRootResourceInformationHandlerMethodArgumentResolver(repositories(),
					repositoryInvokerFactory(defaultConversionService()),
					resourceMetadataHandlerMethodArgumentResolver(), predicateBuilder, factory);
		}

		return new RootResourceInformationHandlerMethodArgumentResolver(repositories(),
				repositoryInvokerFactory(defaultConversionService()), resourceMetadataHandlerMethodArgumentResolver());
	}

}
