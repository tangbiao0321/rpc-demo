package com.tang.rpc_server.reflect;

import java.lang.reflect.Method;
import java.util.Date;

import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

import com.tang.rpc_server.reflect.Invokers.Invoker;

public class ReflectTest {

	public static void main(String[] args) {
		 try {  
		        Date date = new Date();  
		        Method getMethod = Date.class.getMethod("getTime");  
		        getMethod.setAccessible(true);  
		        Method setMethod = Date.class.getMethod("setTime", Long.TYPE);  
		        setMethod.setAccessible(true);  
		        Invoker get = Invokers.newInvoker(getMethod);  
		        Invoker set = Invokers.newInvoker(setMethod);  
		        FastClass fastClass = FastClass.create(Date.class);  
		        FastMethod fastGetMethod = fastClass.getMethod(getMethod);  
		        FastMethod fastSetMethod = fastClass.getMethod(setMethod);  
		  
		        System.out.println(get.invoke(date, new Object[] {}));  
		        set.invoke(date, new Object[] { 333333L });  
		        System.out.println(get.invoke(date, new Object[] {}));  
		        long t0 = System.currentTimeMillis();  
		        for (int i = 0; i < 100000000; i++) {  
		            get.invoke(date, new Object[] {});  
		            set.invoke(date, new Object[] { 333333L });  
		        }  
		        long t1 = System.currentTimeMillis();  
		        System.out.println("Invoker调用耗时：" + (t1 - t0) + "ms");  
		        t1 = System.currentTimeMillis();  
		        for (int i = 0; i < 100000000; i++) {  
		            getMethod.invoke(date, new Object[] {});  
		            setMethod.invoke(date, new Object[] { 333333L });  
		        }  
		        long t2 = System.currentTimeMillis();  
		        System.out.println("JDK反射调用耗时：" + (t2 - t1) + "ms");  
		        t2 = System.currentTimeMillis();  
		        for (int i = 0; i < 100000000; i++) {  
		            fastGetMethod.invoke(date, new Object[] {});  
		            fastSetMethod.invoke(date, new Object[] { 333333L });  
		        }  
		        long t3 = System.currentTimeMillis();  
		        System.out.println("CGLIB反射调用耗时：" + (t3 - t2) + "ms");  
		    } catch (Exception e) {  
		        e.printStackTrace();  
		    }  
		  
	}
}
