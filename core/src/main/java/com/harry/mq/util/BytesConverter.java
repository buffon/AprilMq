package com.harry.mq.util;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Arrays;

import com.harry.mq.util.message.Message;

/**
 * @author chenyehui
 */
public class BytesConverter {

    public static Message bytes2Msg(byte[] data) {
        Message message = null;
//        String topic = new String(Arrays.copyOf(data, 2));
//        byte[] content = Arrays.copyOfRange(data, 2, data.length);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            message = (Message) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}
