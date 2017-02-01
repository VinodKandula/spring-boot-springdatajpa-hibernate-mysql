/**
 * 
 */
package com.teqnihome.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.teqnihome.JPAConfigBootstrapper;
import com.teqnihome.service.util.ByteCodeGenUtil;
import com.teqnihome.service.util.ClazzUtils;
import com.teqnihome.service.util.HibernateEntityEnhancerUtil;

/**
 * @author vkandula
 *
 */
@Service
public class CodeGenEntityService {
	
	private static AtomicBoolean IS_MODEL_GEN = new AtomicBoolean(true);
	
	private static List<Class<?>> entityList = new ArrayList<>();
	
	//@javax.persistence.PersistenceContext
    //javax.persistence.EntityManager em;
	
	//@Autowired
	//CodeGenRepository codeGenRepository;

	public Class<?> enhanceEntity(String entityName, byte[] clazzBytes) {
		return HibernateEntityEnhancerUtil.enhance(entityName, clazzBytes);
	}
	
	public String createEntity() {
		try {
			if (IS_MODEL_GEN.get()) {
				generateModels();
			}
				
			Object depEntity = entityList.get(0).newInstance();
			System.out.println("Department toString() : "+depEntity.toString());
			
			ClazzUtils.updateInstance(depEntity, "name", "Engineering");
			ClazzUtils.updateInstance(depEntity, "description", "Engineering");
			
			Object empEntity = entityList.get(1).newInstance();
			System.out.println("Employess toString() : "+empEntity.toString());
			
			ClazzUtils.updateInstance(empEntity, "name", "Vinod");
			ClazzUtils.updateInstance(empEntity, "fullName", "Vinod Kandula");
			ClazzUtils.updateInstance(empEntity, "department", depEntity);
			
			
			JPAConfigBootstrapper jpaCustomConfig = JPAConfigBootstrapper.getInsatnce();
			EntityManager entityManager = jpaCustomConfig.createEntityManager();
			
			try {
				entityManager.getTransaction().begin();

				entityManager.persist(depEntity);
				entityManager.persist(empEntity);

				entityManager.flush();

				Query query = entityManager.createQuery("from Employee");

				for (Object empObj : query.getResultList()) {
					System.out.println("########### Employee Details #############");
					System.out.println("Emp Id : " + ClazzUtils.getFieldData(empObj, "id"));
					System.out.println("Emp Name : " + ClazzUtils.getFieldData(empObj, "name"));
					System.out.println("Emp Full Name : " + ClazzUtils.getFieldData(empObj, "fullName"));

					Object deptObj = ClazzUtils.getFieldData(empObj, "department");
					System.out.println("########### Department Details ################");
					System.out.println("Dept Id : " + ClazzUtils.getFieldData(deptObj, "id"));
					System.out.println("Dept Name : " + ClazzUtils.getFieldData(deptObj, "name"));

				}

				entityManager.getTransaction().commit();

			} finally {
				if (entityManager != null) {
					entityManager.close(); //entityManager is not thread safe
				}
			}
						
			return "SUCCESS";			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object createEntityByHibernateEnhancer(String entityName) {
		byte[] clazzBytes = ByteCodeGenUtil.generateJPAEntityBytes();
		Class<?> enhancedClazzEntity = enhanceEntity(entityName, clazzBytes);
		
		try {
			Object entity = enhancedClazzEntity.newInstance();
			
			System.out.println("toString() : "+entity.toString());
			
			ClazzUtils.updateInstance(entity, "name", "Vinod");
			ClazzUtils.updateInstance(entity, "fullName", "Vinod Kandula");
			
			JPAConfigBootstrapper jpaCustomConfig = JPAConfigBootstrapper.getInsatnce();
			EntityManager entityManager = jpaCustomConfig.createEntityManager();
			
			entityManager.persist(entity);
			
			//em.persist(entity);
			
			return "SUCCESS";
			
			//return codeGenRepository.save(entity);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private synchronized void  generateModels() {
		if (IS_MODEL_GEN.get()) {
			Class<?> deptClazz = ByteCodeGenUtil.generateDepartmentJPAEntity();
			Class<?> empClazz = ByteCodeGenUtil.generateEmployeeJPAEntity(deptClazz);
			
			ByteCodeGenUtil.rebaseDepartmentEntity(deptClazz, empClazz);
			
			entityList.add(deptClazz);
			entityList.add(empClazz);
			
			IS_MODEL_GEN.set(false);
		}
	}
}
