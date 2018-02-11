package com.tang.rpc_client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tang.rpc_client.config.ClientConfig;
import com.tang.rpc_client.proxy.RpcProxy;
import com.tang.rpc_server.service.HelloService;

import junit.framework.Assert;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
	@Autowired
    private RpcProxy rpcProxy;
 
    public static void main( String[] args )
    {
//       SpringApplication.run(App.class, args);
       
       AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext(ClientConfig.class);
		RpcProxy rpcProxy = acac.getBean(RpcProxy.class);
		HelloService helloService = rpcProxy.create(HelloService.class);
		String result = helloService.hello("World");
       Assert.assertEquals("Hello! World", result);
       acac.close();
    }
}
