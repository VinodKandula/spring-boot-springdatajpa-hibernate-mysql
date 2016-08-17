/**
 * 
 */
package com.teqnihome.service.generic;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityNotFoundException;

/**
 * @author vinod
 *
 */
public interface GenericService<T, ID extends Serializable> {

	public <S extends T> S create(S entity);

	public T findById(long id);

	public List<T> findAll();

	public T update(T entity) throws EntityNotFoundException;

	public T delete(long id) throws EntityNotFoundException;
}
