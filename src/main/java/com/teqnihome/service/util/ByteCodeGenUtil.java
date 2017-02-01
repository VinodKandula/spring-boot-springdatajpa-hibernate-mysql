/**
 * 
 */
package com.teqnihome.service.util;

import java.io.File;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;

import com.teqnihome.model.AbstractEntity;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @author vkandula
 *
 */

public class ByteCodeGenUtil {

	public static Class<?> generateDepartmentJPAEntity() {
		Class<?> dynamicType = null;
		try {
			Unloaded<?>  classByte = new ByteBuddy().subclass(AbstractEntity.class).name("com.bytecode.gen.Department")
					.annotateType(AnnotationDescription.Builder.ofType(Entity.class).build())
					// .defineField("id", Long.class, Visibility.PRIVATE)
					// .annotateField(GenericEntity.class.getField("id").getDeclaredAnnotations())
					.defineField("name", String.class, Visibility.PRIVATE)
					.defineField("description", String.class, Visibility.PRIVATE)
					// .defineMethod("getId", Long.class, Visibility.PUBLIC)
					// .intercept(FieldAccessor.ofField("id"))
					.defineMethod("getName", String.class, Visibility.PUBLIC)
					.intercept(FieldAccessor.ofField("name"))
					.defineMethod("getDescription", String.class, Visibility.PUBLIC)
					.intercept(FieldAccessor.ofField("description"))
					
					.method(ElementMatchers.named("toString"))
					.intercept(FixedValue.value("Department Enity Testing !!!")).make();

			classByte.saveIn(new File("D:/engineering/poc/spring-boot-springdatajpa-hibernate-mysql/target/classes"));

			dynamicType = classByte.load(Persistence.class.getClassLoader()).getLoaded();			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dynamicType;
	}

	public static Class<?> generateEmployeeJPAEntity(Class<?> refClazz) {
		Class<?> dynamicType = null;
		try {
			Unloaded<?> classByte = new ByteBuddy().subclass(AbstractEntity.class).name("com.bytecode.gen.Employee")
					.annotateType(AnnotationDescription.Builder.ofType(Entity.class).build())
					// .defineField("id", Long.class, Visibility.PRIVATE)
					// .annotateField(GenericEntity.class.getField("id").getDeclaredAnnotations())
					.defineField("name", String.class, Visibility.PRIVATE)
					.defineField("fullName", String.class, Visibility.PRIVATE)
					.defineField("department", refClazz, Visibility.PRIVATE)
					.annotateField(AnnotationDescription.Builder.ofType(ManyToOne.class)
							.define("targetEntity", refClazz).define("fetch", FetchType.EAGER).build())
					
					.defineMethod("getName", String.class, Visibility.PUBLIC)
					.intercept(FieldAccessor.ofField("name"))
					.defineMethod("getFullName", String.class, Visibility.PUBLIC)
					.intercept(FieldAccessor.ofField("fullName"))

					.defineMethod("getDepartment", refClazz, Visibility.PUBLIC)
					.intercept(FieldAccessor.ofField("department"))

					.defineMethod("setDepartment", Void.TYPE, Visibility.PUBLIC).withParameter(refClazz)
					.intercept(FieldAccessor.ofField("department"))

					.method(ElementMatchers.named("toString")).intercept(FixedValue.value("Testing !!!")).make();

			classByte.saveIn(new File("D:/engineering/poc/spring-boot-springdatajpa-hibernate-mysql/target/classes"));

			dynamicType = classByte.load(Persistence.class.getClassLoader()).getLoaded();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dynamicType;
	}

	public static byte[] generateJPAEntityBytes() {
		Unloaded<?> classByte;
		try {
			classByte = new ByteBuddy().subclass(AbstractEntity.class).name("com.bytecode.gen.Employee")
					.annotateType(AnnotationDescription.Builder.ofType(Entity.class).build())
					// .defineField("id", Long.class, Visibility.PRIVATE)
					// .annotateField(GenericEntity.class.getField("id").getDeclaredAnnotations())
					.defineField("name", String.class, Visibility.PRIVATE)
					.defineField("fullName", String.class, Visibility.PRIVATE)
					// .defineMethod("getId", Long.class, Visibility.PUBLIC)
					// .intercept(FieldAccessor.ofField("id"))
					.defineMethod("getName", String.class, Visibility.PUBLIC).intercept(FieldAccessor.ofField("name"))
					.defineMethod("getFullName", String.class, Visibility.PUBLIC)
					.intercept(FieldAccessor.ofField("fullName")).method(ElementMatchers.named("toString"))
					.intercept(FixedValue.value("Testing !!!")).make();

			// classByte.saveIn(new
			// File("D:/engineering/workspaces/workspace-java8/codegen/src/main/java/codegen/src"));

			// Class<?> dynamicType =
			// classByte.load(Persistence.class.getClassLoader()).getLoaded();

			return classByte.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static Class<?> rebaseDepartmentEntity(Class<?> deptClazz, Class<?> empClazz) {
		Class<?> dynamicType = null;
		try {
			TypeDescription.Generic typeDescription = TypeDescription.Generic.Builder.parameterizedType(Set.class, empClazz).build();
			
			Unloaded<?> classByte = new ByteBuddy()
					.redefine(deptClazz)
					.defineField("employees", typeDescription, Visibility.PRIVATE)
					.annotateField(AnnotationDescription.Builder.ofType(OneToMany.class)
							.define("targetEntity", empClazz).define("fetch", FetchType.LAZY).build())
					
					.defineMethod("getEmployess", typeDescription, Visibility.PUBLIC)
					.intercept(FieldAccessor.ofField("employees"))

					.defineMethod("setEmployees", Void.TYPE, Visibility.PUBLIC).withParameter(typeDescription)
					.intercept(FieldAccessor.ofField("employees"))
					.make();
			
			classByte.saveIn(new File("D:/engineering/poc/spring-boot-springdatajpa-hibernate-mysql/target/classes"));

			//dynamicType = classByte.load(Persistence.class.getClassLoader()).getLoaded();
			dynamicType = classByte.getClass();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return dynamicType;
	}
}
