package com.harry.mq.util.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenyehui
 */
public class MessageFactory {

    private static Map<String, Queue<Message>> map = new HashMap<String, Queue<Message>>();

    private static ReentrantLock addLock = new ReentrantLock();

    private static ReentrantLock getLock = new ReentrantLock();

    public static void addMsg(String topic, Message msg) {
        addLock.lock();
        if (!map.containsKey(topic)) {
            Queue<Message> list = new LinkedBlockingDeque<Message>();
            map.put(topic, list);
        }
        map.get(topic).offer(msg);
        addLock.unlock();
    }

    public static Message getMsg(String topic) {
        getLock.lock();
        if (map.containsKey(topic)) {
            return map.get(topic).poll();
        }
        getLock.unlock();
        return null;
    }
}
