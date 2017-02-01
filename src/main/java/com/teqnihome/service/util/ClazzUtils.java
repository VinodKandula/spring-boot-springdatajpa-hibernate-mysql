/**
 * 
 */
package com.teqnihome.service.util;

import java.lang.reflect.Field;

/**
 * @author vkandula
 *
 */
public class ClazzUtils {

	public static boolean updateInstance(Object object, String fieldName, Object fieldValue) {
	    Class<?> clazz = object.getClass();
	    while (clazz != null) {
	        try {
	            Field field = clazz.getDeclaredField(fieldName);
	            field.setAccessible(true);
	            field.set(object, fieldValue);
	            return true;
	        } catch (NoSuchFieldException e) {
	            clazz = clazz.getSuperclass();
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	    }
	    return false;
	}
	
	public static Object getFieldData(Object object, String fieldName) {
		Class<?> clazz = object.getClass();
	    while (clazz != null) {
	        try {
	            Field field = clazz.getDeclaredField(fieldName);
	            field.setAccessible(true);
	            return field.get(object);
	        } catch (NoSuchFieldException e) {
	            clazz = clazz.getSuperclass();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return null;

		
	}
	
	public static class CustomClassLoader extends ClassLoader {

	    public CustomClassLoader(ClassLoader parent) {
	       super(parent);      
	    }
	    
	    public Class<?> defineClass(String name, byte[] data) {
	    	Class<?> clazz = super.defineClass(name, data, 0, data.length);
	    	super.resolveClass(clazz);
	       return clazz;
	    }
	}
}
