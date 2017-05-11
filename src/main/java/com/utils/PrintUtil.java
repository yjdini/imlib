package com.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.apache.catalina.util.Introspection;

import javax.servlet.http.HttpServletResponse;

public class PrintUtil
{

	public static void responseWithJson(HttpServletResponse response, String str){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(str);
		} catch (IOException e) {
			System.out.print(e.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static void print(Object object)
	{
		if(object == null)
		{
			System.out.println("null");
			return;
		}
		System.out.println("==========="+object.getClass().getName());
		if(object.getClass().isArray())
		{
			Object[] l = (Object[]) object;
			for(int i = 0; i < l.length; i ++){
				System.out.println("the "+ i);
				print(l[i]);
			}
			return;
		}
		else
		{
			printGetMethods(object);
			printFields(object);
		}
	}
	

	//only methods named getXXX() and without parameter will be invoke and print.
	//warn descriptor.getParameterDescriptors() don't imply how many parameters the method has
	private static void printGetMethods(Object object)
	{
		MethodDescriptor[] methodDecriptors = null;
		try {
			methodDecriptors = Introspector.getBeanInfo(object.getClass()).getMethodDescriptors();
		} catch (IntrospectionException e1) {
			e1.printStackTrace();
		}
		
		for(MethodDescriptor methodDecriptor : methodDecriptors)
		{
			Method method = methodDecriptor.getMethod();
			if(methodDecriptor.getName().contains("get") && (method.getParameterTypes() == null || method.getParameterTypes().length == 0))
			{
				try
				{
					System.out.println("***********"+method.getName()+"() : "+method.invoke(object));
				}
				catch(Exception e)
				{
					//do nothing.... some method like , response.getWriter() maybe only permit to be called for one time.
					//so just skip it. 
				}
			}
		}
		
	}
	
	
	private static void printFields(Object object)
	{
		Field[] fields = Introspection.getDeclaredFields(object.getClass());
		for(Field field : fields)
		{
			try {
				field.setAccessible(true);
				System.out.println("****" + ReflectUtil.getModifierStr(field.getModifiers()) + "/ " +
					 field.getClass().toString().replace("class","") + "/ " +
						field.getName()+" : "+field.get(object));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public static void main(String[] args)
	{
		Integer a = 1;
		print(a);
	}
}
