package com.tang.rpc_server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.tang.rpc_server.service.RpcServer;

@Configuration
@ComponentScan("com.tang.rpc_server")
public class RpcConfig {
	
	@Value(value = "${server.address}")
	private String serverAddr;
	@Value(value = "${registry.address}")
	private String registryAddr;
	
	@Autowired
	private ServiceRegistry serviceRegistry;
	@Bean
	public ServiceRegistry buildDisco(){System.out.println(registryAddr);
		return new ServiceRegistry(registryAddr);
	}
	@Bean
	public RpcServer buildRpcServer(){System.out.println(serverAddr);System.out.println(registryAddr);
		return new RpcServer(serverAddr, serviceRegistry);
	}
}
