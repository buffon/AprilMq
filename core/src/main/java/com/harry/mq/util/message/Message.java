package com.harry.mq.util.message;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author chenyehui
 */
public class Message implements Serializable {

    private String topic;

    private byte[] content;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "topic='" + topic + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
