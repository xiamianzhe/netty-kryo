package com.example.max;
 
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
 
/**
 * Created by Administrator on 2017/5/16.
 * ChannelInboundHandlerAdapter extends ChannelHandlerAdapter 用于对网络事件进行读写操作
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
	
    @Override
    public void channelActive(ChannelHandlerContext ctx){
    	System.out.println(ctx.channel().localAddress().toString()+"通道活跃....");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	System.out.println(ctx.channel().localAddress().toString()+"通道不活跃....");

    }
    /**
     * 收到客户端消息，自动触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	ObjectMessage m = (ObjectMessage) msg;
        System.out.println("server:" + m.getContent());
        
    }
 
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    	//向服务端发送数据
        ObjectMessage m = new ObjectMessage();
        m.setId("1234");
        m.setContent("hello yinjihuan");
        ctx.writeAndFlush(m);
    }
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /**当发生异常时，关闭 ChannelHandlerContext，释放和它相关联的句柄等资源 */
    	System.out.print("连接中断"+cause);
        ctx.close();
    }
}