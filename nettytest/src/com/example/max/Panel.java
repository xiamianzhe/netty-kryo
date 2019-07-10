package com.example.max;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;

import io.netty.channel.ChannelHandlerContext;

public class Panel {
public static void main(String[] args) {
	TimeServer times = new TimeServer();
    Thread thread = new Thread(times);
    thread.start();
}
}
