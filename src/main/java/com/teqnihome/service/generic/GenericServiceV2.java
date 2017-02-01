/**
 * 
 */
package com.teqnihome.service.generic;

import java.io.Serializable;
import java.util.List;

/**
 * @author vkandula
 *
 */
public interface GenericServiceV2<T, D, ID extends Serializable> {

	public D findOne(ID id);
	
	public List<D> findAll();
	
	public void save(D dto);
	
}
