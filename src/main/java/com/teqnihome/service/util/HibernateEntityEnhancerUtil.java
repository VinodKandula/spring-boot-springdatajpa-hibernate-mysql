/**
 * 
 */
package com.teqnihome.service.util;

import java.io.ByteArrayInputStream;

import org.hibernate.bytecode.enhance.spi.Enhancer;
import org.hibernate.tool.enhance.EnhancementTask;

import com.teqnihome.service.util.ClazzUtils.CustomClassLoader;

import javassist.ClassPool;
import javassist.CtClass;

/**
 * @author vkandula
 *
 */
public class HibernateEntityEnhancerUtil {

	public static Class<?> enhance(String entityName, byte[] clazzBytes) {
		try {
			ClassPool classPool = new ClassPool( false );
			CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(clazzBytes));
			EnhancementTask enhancementTask = new EnhancementTask();
			Enhancer enhancer = new Enhancer( enhancementTask);
			byte[] result = enhancer.enhance( ctClass.getName(), ctClass.toBytecode() );
			
			CustomClassLoader customClassLoader = new CustomClassLoader(ByteCodeGenUtil.class.getClassLoader());
			Class<?> clazz = customClassLoader.defineClass("com.bytecode.gen.Employee", result);
			return clazz;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
