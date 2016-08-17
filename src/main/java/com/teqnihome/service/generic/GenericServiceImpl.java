/**
 * 
 */
package com.teqnihome.service.generic;

import java.io.Serializable;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author vinod
 *
 */
@Transactional
public abstract class GenericServiceImpl<T, ID extends Serializable> implements GenericService<T, ID> {

	@Autowired
    private CrudRepository<T, Long> repository;
	
	@Override
    public <S extends T> S create(S entity) {
        return this.repository.save(entity);
    }

	@Override
	public T findById(long id) {
		return this.repository.findOne(id);
	}

	@Override
	public T delete(long id) throws EntityNotFoundException {
		T entity = (T)this.repository.findOne(id);
		if (entity == null)
			throw new EntityNotFoundException();
		
		this.repository.delete(id);
		return entity;
	}

}
