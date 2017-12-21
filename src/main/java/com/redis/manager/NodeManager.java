package com.redis.manager;

import com.redis.manager.bean.ClusterNodeInfo;
import com.redis.manager.bean.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * node 管理
 * @author jiafengshen 2017/12/19.
 */
public class NodeManager {

    private static Logger logger = LoggerFactory.getLogger(NodeManager.class);

    /**
     * 集群节点情况
     */
    private volatile List<ClusterNodeInfo> result;

    /**
     * 创建集群
     * @param hosts
     */
    public void createCluster(List<Host> hosts){
        Jedis jedis =JedisManager.getJedis(hosts.get(0));
        for(int i=1;i<hosts.size();i++){
            jedis.clusterMeet(hosts.get(i).getIp(),hosts.get(i).getPort());
        }
        jedis.close();
    }

    /**
     * 设置节点的slave
     * @param
     */
    public void  setSlave(Host master,Host slave){
        Jedis jedis =JedisManager.getJedis(slave);
        //
        jedis.clusterReplicate(getNodeName(master));
        System.out.println(11);
        jedis.close();
    }

    /**
     * 添加节点到集群
     * @param hosts
     */
    public void addCluster(Host host,List<Host> hosts){
        Jedis jedis = JedisManager.getJedis(host);
        for(int i=0;i<hosts.size();i++){
            jedis.clusterMeet(hosts.get(i).getIp(),hosts.get(i).getPort());
        }
        jedis.close();
    }

    /**
     * 删除节点到集群
     * @param hosts
     */
    public void removeCluster(Host host,List<Host> hosts){
        Jedis jedis = JedisManager.getJedis(host);
        for(int i=0;i<hosts.size();i++){
            String nodeId = getNodeName(hosts.get(i));
            if(nodeId == null){
                continue;
            }
            jedis.clusterForget(nodeId);
        }
        jedis.close();
    }

    /**
     * 集群节点情况
     * @param host
     * @return
     */
    public List<ClusterNodeInfo> infoNodes(Host host){
        List<ClusterNodeInfo> demo = new ArrayList<ClusterNodeInfo>();
        Jedis jedis = JedisManager.getJedis(host);
        try{
            String info = jedis.clusterNodes();
            int start = 0;
            List<String> nodeInfo= new ArrayList<String>();
            for(int i = start;i<info.length();i++){
                if(info.charAt(i)==' '){
                    nodeInfo.add(info.substring(start,i));
                    start = i+1;
                }else if(info.charAt(i)=='\n'){
                    //一个节点状态结束，
                    nodeInfo.add(info.substring(start,i));
                    ClusterNodeInfo clusterNodeInfo = new ClusterNodeInfo(nodeInfo);
                    demo.add(clusterNodeInfo);
                    //另外一个节点状态开始
                    start = i+1;
                    nodeInfo= new ArrayList<String>();
                }
            }
        }catch (Exception e){
            logger.error("",e);
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        this.result = demo;
        return demo;
    }

    /**
     * 查询 host 在集群中 名称
     * @param host
     * @return
     */
    public String getNodeName(Host host){
        if(result == null || result.size() == 0){
            infoNodes(host);
        }
        for(ClusterNodeInfo clusterNodeInfo:result){
            if(clusterNodeInfo.getIp().equals(host.getIp())&&clusterNodeInfo.getPort()==host.getPort()){
                return clusterNodeInfo.getNodeId();
            }
        }
        return null;
    }
}
