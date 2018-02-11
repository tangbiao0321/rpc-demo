package com.tang.rpc_netty_demo.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ServerHandler2 extends MessageToByteEncoder{

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		System.out.println("server encode::"+msg);
		byte[] b = msg.toString().getBytes();
		out.writeInt(b.length);
		out.writeBytes(b);
	}

	

	
}
