package com.tang.rpc_netty_demo.server;

import com.tang.rpc_netty_demo.client.ClientHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

	public static void main(String[] args) {
		EventLoopGroup parentGroup = new NioEventLoopGroup();
		EventLoopGroup childGroup = new NioEventLoopGroup();
		
		ServerBootstrap boot = new ServerBootstrap();
		
//		handler：监听bootstrap动作；   
//		childHandler：监听已连接客户端Channel动作和状态
		boot.group(parentGroup, childGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline()
//					.addFirst(handlers)
//					.addBefore(baseName, name, handler)
//					.addAfter(baseName, name, handler)
					.addLast(new ServerHandler2())
					.addLast(new ServerHandler());
			}
		})
			.option(ChannelOption.SO_BACKLOG, 100)
			.childOption(ChannelOption.SO_KEEPALIVE, true);
		
		try {
			ChannelFuture futrue = boot.bind(8088).sync();
			futrue.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();System.out.println("err");
		} finally{System.out.println("finally..");
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		}
		
	}
}
