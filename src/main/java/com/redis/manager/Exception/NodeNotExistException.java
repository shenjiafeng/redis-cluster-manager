/**
 * Copyright (C), 2011-2017, 微贷网.
 */
package com.redis.manager.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jiafengshen 2017/12/19.
 */
public class NodeNotExistException extends Exception{

    private String ip;

    private int port;

    public NodeNotExistException(String ip, int port) {
        super("ip="+ip+",port="+port+",节点在集群不存在");
        this.ip = ip;
        this.port = port;
    }


}
