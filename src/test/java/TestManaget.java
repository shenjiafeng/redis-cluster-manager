import com.redis.manager.ClusterManager;
import com.redis.manager.bean.Host;
import redis.clients.util.JedisClusterCRC16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiafengshen 2017/12/19.
 */
public class TestManaget {

    ClusterManager clusterManager = new ClusterManager();
    Host host1 = new Host("127.0.0.1", 6379);
    Host host2 = new Host("127.0.0.1", 6389);
    Host host3 = new Host("127.0.0.1", 6399);
    Host host11 = new Host("127.0.0.1", 5379);
    Host host21 = new Host("127.0.0.1", 5389);
    Host host31 = new Host("127.0.0.1", 5399);

    public void create() {
        clusterManager.infoNodes(host1);
        List<Host> hosts = new ArrayList<Host>();
        hosts.add(host1);
        hosts.add(host2);
        hosts.add(host3);
        hosts.add(host11);
        hosts.add(host21);
        hosts.add(host31);

        clusterManager.createCluster(hosts);
        clusterManager.infoNodes(host1);
    }

    public void remove() {
        clusterManager.infoNodes(host1);
        List<Host> hosts = new ArrayList<Host>();
        hosts.add(host21);
        hosts.add(host31);
        hosts.add(host11);
        clusterManager.removeCluster(host1, hosts);
        clusterManager.infoNodes(host1);
    }

    public void add() {
        clusterManager.infoNodes(host1);
        List<Host> add = new ArrayList<Host>();
        add.add(host1);
        //add.add(host21);
        //add.add(host31);
        clusterManager.addCluster(host2, add);
        clusterManager.infoNodes(host1);
    }

    public void publish() {
        int[] i1 = new int[5000];
        int[] i2 = new int[6384];
        int[] i3 = new int[5000];
        for (int i = 0; i <= 16383; i++) {
            if (i <5000) {
                i1[i] = i;
            } else if (i < 10000) {
                i3[i - 5000] = i;
            } else {
                i2[i - 10000] = i;
            }

        }
        clusterManager.clusterAddSlots(host1,i1);
        clusterManager.clusterAddSlots(host2, i2);
        clusterManager.clusterAddSlots(host3, i3);
    }


    public void move() {
        try {
            clusterManager.clusterMoveSlots(host1, host2, 13862);
        } catch (Exception e) {

        }
    }

    public void addSlave() {
        clusterManager.setSlave(host1,host11);
        clusterManager.setSlave(host1,host21);
        clusterManager.setSlave(host1,host31);
    }

    public static void main(String[] args) throws Exception {


        TestManaget testManaget = new TestManaget();
        //testManaget.create();
        //testManaget.add();
        //testManaget.remove();
        testManaget.addSlave();
       // testManaget.publish();
        //testManaget.move();


    }




}
