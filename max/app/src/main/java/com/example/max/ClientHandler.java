package com.example.max;

import android.util.Log;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //向服务端发送数据
        ObjectMessage m = new ObjectMessage();
        m.setId("1234");
        m.setContent("hello yinjihuan");
        ctx.writeAndFlush(m);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //读取服务端发过来的数据
        ObjectMessage m = (ObjectMessage) msg;
        System.err.println("client:" + m.getContent());
    }
}