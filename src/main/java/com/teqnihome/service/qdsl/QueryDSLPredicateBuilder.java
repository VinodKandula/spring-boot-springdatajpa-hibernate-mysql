/**
 * 
 */
package com.teqnihome.service.qdsl;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.PathBuilder;

/**
 * @author vkandula
 *
 */
public class QueryDSLPredicateBuilder {

	public static Predicate getPredicate(Class<?> type, String instanceVar, String expression) {
		PathBuilder<?> pathBuilder = new PathBuilder<>(type, instanceVar);
		Predicate filter = pathBuilder.getString("name").eq("Vinod");
		return filter;
	}
}
