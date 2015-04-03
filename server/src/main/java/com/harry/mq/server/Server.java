package com.harry.mq.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.harry.mq.processor.ReceiveMsgProcessor;
import com.harry.mq.util.Constants;

public class Server {

    private ServerSocketChannel serverChannel = null;

    private Selector selector = null;

    public Server() {
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(Constants.PORT));

            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() throws Exception {
        System.out.println("server start listen");
        for (; ; ) {
            int sum = selector.select(1000);
            System.out.println( "current keys: " + sum);
            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();

            while (ite.hasNext()) {
                SelectionKey key = ite.next();
                ite.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key
                            .channel();
                    SocketChannel channel = server.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    new ReceiveMsgProcessor(selector, key).start();
                }
            }
        }
    }

//    public void read(SelectionKey key) throws Exception {
//        SocketChannel channel = (SocketChannel) key.channel();
//        ByteBuffer buffer = ByteBuffer.allocate(10);
//        channel.read(buffer);
//        byte[] data = buffer.array();
//        String msg = new String(data).trim();
//        System.out.println("server Get " + msg);
//        key.cancel();
//    }

//    public static void main(String args[]) throws Exception {
//        Server server = new Server();
//        server.init();
//        server.listen();
//    }
}
