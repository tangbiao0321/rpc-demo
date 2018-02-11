package com.tang.rpc_netty_demo.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ClientHandler extends MessageToByteEncoder{

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		byte[] b = msg.toString().getBytes();
		out.writeInt(b.length);
		out.writeBytes(b);
		System.out.println("client encode:::"+msg);
	}

	

	
}
