package com.example.max;
 
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
 
/**
 * Created by Administrator on 2017/5/16.
 * ChannelInboundHandlerAdapter extends ChannelHandlerAdapter ���ڶ������¼����ж�д����
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
	
    @Override
    public void channelActive(ChannelHandlerContext ctx){
    	System.out.println(ctx.channel().localAddress().toString()+"ͨ����Ծ....");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	System.out.println(ctx.channel().localAddress().toString()+"ͨ������Ծ....");

    }
    /**
     * �յ��ͻ�����Ϣ���Զ�����
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
    	//�����˷�������
        ObjectMessage m = new ObjectMessage();
        m.setId("1234");
        m.setContent("hello yinjihuan");
        ctx.writeAndFlush(m);
    }
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /**�������쳣ʱ���ر� ChannelHandlerContext���ͷź���������ľ������Դ */
    	System.out.print("�����ж�"+cause);
        ctx.close();
    }
}