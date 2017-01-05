package com.ini.controllers;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ini.mongodemo.MongoPrimitive;
import com.utils.PrintUtil;

@Controller
@RequestMapping("/hello")
public class HelloController
{
	
	@RequestMapping("/getLastTime")
	@ResponseBody
	public HashMap<String,String> getLastTime()
	{
		HashMap<String,String> map = new HashMap<String,String>();
		String time = MongoPrimitive.getLastTime();
		map.put("time", time);
		return map;
		
	}
	
	@RequestMapping("/addUser")
	public void addUser(HttpServletRequest request, HttpServletResponse response)
	{
		String name = request.getParameter("name");
		MongoPrimitive.addUser(name);
//		response.getWriter();
		try {
			PrintUtil.print(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
