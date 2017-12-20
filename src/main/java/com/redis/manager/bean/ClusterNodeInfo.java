package com.redis.manager.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiafengshen 2017/12/19.
 */
public class ClusterNodeInfo {


    private String nodeId;

    private String ip;

    private int port;

    private String master;

    private List<Integer> slots;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public List<Integer> getSlots() {
        return slots;
    }

    public void setSlots(List<Integer> slots) {
        this.slots = slots;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public ClusterNodeInfo(List<String> value) {

        nodeId = value.get(0);
        ip  = value.get(1).split(":")[0];
        port = Integer.valueOf(value.get(1).split(":")[1]);
        master = value.get(2);

        if(value.size()>8){
            this.slots = new ArrayList<Integer>();
            for(int i=8;i<value.size();i++){
                String id = value.get(i);
                if(id.contains("->")||id.contains("<-")){
                    continue;
                }else if(id.contains("-")){
                    int min = Integer.valueOf(id.split("-")[0]);
                    int max = Integer.valueOf(id.split("-")[1]);
                    for(int start = min;start <=max;start++){
                        this.slots.add(start);
                    }
                }else{
                    this.slots.add(Integer.valueOf(id));
                }
            }
        }
    }
}
