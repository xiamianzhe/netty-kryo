package com.example.max;
 
import java.nio.charset.Charset;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
 
/**
 * Created by Administrator on 2017/5/16.
 */
public class TimeServer implements Runnable{
    public void run() {
        int port = 8888;
        new TimeServer().bind(port);
    }
 
    public void bind(int port) {
        /**
         * interface EventLoopGroup extends EventExecutorGroup extends ScheduledExecutorService extends ExecutorService
         * ���÷���˵� NIO �̳߳�,���������¼�����ʵ�������Ǿ��� Reactor �߳���
         * bossGroup ���ڷ���˽��ܿͻ������ӣ�workerGroup ���ڽ��� SocketChannel �����д*/
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            /** ServerBootstrap �� Netty �������� NIO ����˵ĸ��������࣬���ڽ��Ϳ����Ѷ�
             * */
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler())
                    .childOption(ChannelOption.SO_KEEPALIVE, true);;
 
            /**����������������������ɺ󣬵��� bind �����󶨼����˿ڣ����� sync ����ͬ���ȴ��󶨲������*/
            ChannelFuture f = b.bind(port).sync();
 
            System.out.println(Thread.currentThread().getName() + ",��������ʼ�����˿ڣ��ȴ��ͻ�������.........");
            /**���������������ȴ����������ӹر�֮�� main �����˳����������
             *
             * */
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            /**�����˳����ͷ��̳߳���Դ*/
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
 
    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel arg0) throws Exception {
        	arg0.pipeline().addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(
                    this.getClass().getClassLoader()
            )));
        	arg0.pipeline().addLast("encoder", new ObjectEncoder());
            arg0.pipeline().addLast(new TimeServerHandler());
            //�����ʽ  
            arg0.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));  

        }
    }
}