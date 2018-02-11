package com.tang.rpc_client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tang.rpc_client.config.ClientConfig;
import com.tang.rpc_client.proxy.RpcProxy;
import com.tang.rpc_server.service.HelloService;

import junit.framework.Assert;

public class AppTest {
    
	public static void main(String[] args) {
		AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext(ClientConfig.class);
		RpcProxy rpcProxy = acac.getBean(RpcProxy.class);
		HelloService helloService = rpcProxy.create(HelloService.class);
		String result = helloService.hello("三国");
        System.out.println("Remote response::"+result);
        acac.close();
	}
}
