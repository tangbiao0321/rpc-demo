package com.tang.rpc_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tang.rpc_server.config.RpcConfig;

@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
//    	AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext(RpcConfig.class);
    	SpringApplication.run(App.class, args);
    }
}
