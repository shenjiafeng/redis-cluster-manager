package com.redis.manager.bean;

/**
 * @author jiafengshen 2017/12/19.
 */
public class Host {

    private String ip;

    private int port;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Host host = (Host) o;

        if (port != host.port) return false;
        return ip != null ? ip.equals(host.ip) : host.ip == null;

    }

    @Override
    public int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;
        result = 31 * result + port;
        return result;
    }

    @Override
    public String toString() {
        return "Host{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }

    public Host(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
}
