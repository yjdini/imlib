package com.db.mongod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test
{
	public StringBuffer a = new StringBuffer();
	public int b = 0;
	public static Test product()
	{
		return new Test();
	}

	@Override
	public void finalize()
	{
		System.out.println("execute the finalize method");
	}

	public static void main(String[] args)
	{
		List<String> codes = Arrays.asList("1112233", "2223344");
		List b = new ArrayList();
		String[] c = {""};
		System.out.println((String)null);//null 而非 空
		System.out.println(codes.getClass());
		System.out.println(codes.getClass().isArray());
		System.out.println(b.getClass());
		System.out.println(c.getClass());
		System.out.println(c.getClass().isArray());
		Test a = product();
		System.out.println(a.hashCode());
		a.a.append("asdfasdf");
		a.b = 123213;
		System.out.println(a.hashCode());
		System.out.println(a);
		System.gc();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
