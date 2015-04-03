package com.harry.mq.processor;

import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.harry.mq.util.BytesConverter;
import com.harry.mq.util.message.Message;
import com.harry.mq.util.message.MessageFactory;

/**
 * @author chenyehui
 */
public class ReceiveMsgProcessor extends Thread {

	private Selector selector;

	private SelectionKey key;

	public ReceiveMsgProcessor(Selector selector, SelectionKey key) {
		this.selector = selector;
		this.key = key;
	}

	@Override
	public void run() {
		try {
			// if (key.isConnectable()) {
			// SocketChannel channel = (SocketChannel) key.channel();
			// channel.configureBlocking(false);
			// channel.register(selector, SelectionKey.OP_READ);
			// } else if (key.isReadable()) {
			read(key);
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void read(SelectionKey key) throws Exception {
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(10);
		channel.read(buffer);
		byte[] data = buffer.array();
		Message message = BytesConverter.bytes2Msg(data);
		// ObjectInputStream ois = new
		// ObjectInputStream(channel.socket().getInputStream());
		// Message message = (Message) ois.readObject();
		System.out.println("receive: " + message);
		MessageFactory.addMsg(message.getTopic(), message);
		key.cancel();
	}
}
