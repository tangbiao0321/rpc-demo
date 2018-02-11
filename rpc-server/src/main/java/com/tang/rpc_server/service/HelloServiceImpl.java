package com.tang.rpc_server.service;

import com.tang.rpc_server.annotation.RpcService;

@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

	public String hello(String name) {
		// TODO Auto-generated method stub
		return "Hello! " + name;
	}

}
