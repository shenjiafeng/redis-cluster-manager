package com.redis.manager;


import com.redis.manager.Exception.NodeNotExistException;
import com.redis.manager.bean.Host;
import redis.clients.jedis.Jedis;

import java.util.List;


/**
 * slots 管理
 * @author jiafengshen 2017/12/19.
 */
public class ClusterManager extends NodeManager{

    /**
     * 添加槽位
     * @param host
     * @param slots
     */
    public void clusterAddSlots(Host host, int ... slots){
        Jedis jedis = JedisManager.getJedis(host);
        jedis.clusterAddSlots(slots);
        jedis.close();
    }



    /**
     * 影响集群对外服务,在集群已经对外服务时候，慎重使用
     * @param host
     * @param slots
     */
    public void clusterRemoveSlots(Host host,int ... slots){
        Jedis jedis = JedisManager.getJedis(host);
        jedis.clusterDelSlots(slots);
        jedis.close();
    }

    /**
     * 转移槽位，重新分片
     * @param src
     * @param target
     * @param slot
     * @throws NodeNotExistException
     */
    public void clusterMoveSlots(Host src,Host target,int slot) throws NodeNotExistException{

        String srcNodeId = getNodeName(src);
        String tarNodeId = getNodeName(target);

        if(srcNodeId == null ){
            throw  new NodeNotExistException(src.getIp(),src.getPort());
        }else if(tarNodeId == null){
            throw  new NodeNotExistException(target.getIp(),target.getPort());
        }

        Jedis jsrc  = JedisManager.getJedis(src);
        Jedis jtarget =  JedisManager.getJedis(target);


        jtarget.clusterSetSlotImporting(slot,srcNodeId);

        jsrc.clusterSetSlotMigrating(slot,tarNodeId);

        Long l =  jsrc.clusterCountKeysInSlot(slot);

        while(true){
            List<String> sk= jsrc.clusterGetKeysInSlot(slot,1000);
            if(sk.size() == 0){
                break;
            }
            for(String sk1:sk){
                jsrc.migrate(target.getIp(),target.getPort(),sk1,0,5000);
            }
        }
        jtarget.clusterSetSlotNode(slot,tarNodeId);

        jtarget.close();
        jsrc.close();

    }




}

