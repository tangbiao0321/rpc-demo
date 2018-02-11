package com.tang.rpc_client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.tang.rpc_client.proxy.RpcProxy;
import com.tang.rpc_server.service.ServiceDiscovery;

@Configuration
@ComponentScan("com.tang.rpc_client")
//@ConfigurationProperties("classpath:application.properties")
public class ClientConfig {
	@Autowired
	private ServiceDiscovery serviceDiscovery;
	
	@Value(value="127.0.0.1:2181")//(value = "${registry.address}")
	private String registryAddr;

	@Bean
	public RpcProxy build(){
		return new RpcProxy(serviceDiscovery);
	}
	
	@Bean
	public ServiceDiscovery buildDisco(){registryAddr="127.0.0.1:2181";System.out.println(registryAddr);
		return new ServiceDiscovery(registryAddr);
	}
}
