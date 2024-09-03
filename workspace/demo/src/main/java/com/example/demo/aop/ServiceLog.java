package com.example.demo.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLog {
	@Pointcut("execution(* com.example.demo.service..*.*(..))")
	private void cut() {}
	
	@Before("cut()")
	public void before(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method method = signature.getMethod();
		System.out.println("==================");
		System.out.print("service : "+method.getName()+"(");
		
		Object[] args = joinPoint.getArgs();
		Parameter[] params = method.getParameters();
		System.out.print("Parameter : ");
		if(args.length > 0) {
			System.out.print(getArgString(args[0], params[0].getName()));
			for(int i=1;i<args.length;i++) {
				System.out.print(", "+getArgString(args[i],params[i].getName()));
			}
		}
		System.out.println(")");
	}
	
	//cut() 메소드 이후에 호출
	//obj 라는 매개변수에 return 하는 값을 담아놓고 호출 
	@AfterReturning(value="cut()", returning = "obj")
	public void afterReturning(JoinPoint joinPoint, Object obj) {
		System.out.println("Return : "+getArgString(obj,"return"));
		System.out.println("==================");
	}
	private String getArgString(Object obj, String name) {
		String result = "";
		result += obj;
		result += "(";
		if(name != null) {
			result += name;
		}
		if(obj != null) {
			result +="-"+obj.getClass().getSimpleName();
		}
		result += ")";
		return result;
	}
}