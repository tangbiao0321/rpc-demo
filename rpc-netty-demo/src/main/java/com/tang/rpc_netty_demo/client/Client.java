package com.tang.rpc_netty_demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

	public static void main(String[] args) {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap boot = new Bootstrap();
		boot.group(group).channel(NioSocketChannel.class)
		.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// TODO Auto-generated method stub
				ch.pipeline()
				.addLast(new ClentHandler2())
				.addLast(new ClientHandler());
			}
		})
		.option(ChannelOption.SO_KEEPALIVE, true);
		
		try {
			ChannelFuture future = boot.connect("127.0.0.1", 8088).sync();
			future.channel().writeAndFlush("123").sync();
			future.channel().closeFuture().sync();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();System.out.println("err");
		} finally {
			group.shutdownGracefully();System.out.println("finally...");
		}
	}
}
