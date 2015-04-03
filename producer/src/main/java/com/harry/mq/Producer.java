package com.harry.mq;

import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import java.util.Iterator;

import com.harry.mq.util.Constants;
import com.harry.mq.util.message.Message;

public class Producer {

    private Selector selector;

    private SocketChannel channel;

    public static void main(String args[]) throws Exception {
        Producer producer = new Producer();
        producer.connect();
        //		producer.listen();
    }

    public void connect() throws Exception {
        channel = SocketChannel.open();
        channel.configureBlocking(true);
        //		selector = Selector.open();
        //		channel.register(selector, SelectionKey.OP_CONNECT);

        channel.connect(new InetSocketAddress(Constants.IP, Constants.PORT));

        Message message = new Message();
        message.setTopic("123");
        message.setContent("chenyehui".getBytes());

        ObjectOutputStream oos = new ObjectOutputStream(channel.socket().getOutputStream());
        oos.writeObject(message);
        System.out.println("Send Over.");
    }

    //	public void send() throws Exception{
    //		SocketChannel sChannel = channel.accept();
    //	}

    public void listen() throws Exception {
        selector.select();
        Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();

        while (ite.hasNext()) {
            SelectionKey key = ite.next();

            ite.remove();

            if (key.isConnectable()) {
                SocketChannel channel = (SocketChannel) key.channel();
                if (channel.isConnectionPending()) {
                    channel.finishConnect();
                }

                Message message = new Message();
                message.setTopic("123");
                message.setContent("chenyehui".getBytes());

                channel.configureBlocking(false);
                ObjectOutputStream oos = new ObjectOutputStream(channel.socket().getOutputStream());
                oos.writeObject(message);
                channel.register(selector, SelectionKey.OP_READ);
                selector.close();
                channel.close();
            } else if (key.isReadable()) {
                // read(key);
            }

        }
        System.out.println("producer close");
    }

}
