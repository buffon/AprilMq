package com.harry.mq;

import com.harry.mq.server.Server;

/**
 * @author chenyehui
 */
public class Startup {

    public static void main(String[] args) throws Exception {
        new Server().listen();
    }
}
