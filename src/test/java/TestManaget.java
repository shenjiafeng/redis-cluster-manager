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

    public void create() {
        clusterManager.infoNodes(host1);
        List<Host> hosts = new ArrayList<Host>();
        hosts.add(host1);
        hosts.add(host2);
        hosts.add(host3);
        clusterManager.createCluster(hosts);
        clusterManager.infoNodes(host1);
    }

    public void remove() {
        clusterManager.infoNodes(host1);
        List<Host> hosts = new ArrayList<Host>();
        hosts.add(host3);
        clusterManager.removeCluster(host1, hosts);
        clusterManager.infoNodes(host1);
    }

    public void add() {
        clusterManager.infoNodes(host1);
        List<Host> add = new ArrayList<Host>();
        add.add(host3);
        clusterManager.addCluster(host1, add);
        clusterManager.infoNodes(host1);
    }

    public void publish() {
        int[] i1 = new int[5000];
        int[] i2 = new int[6384];
        int[] i3 = new int[5000];
        for (int i = 0; i <= 16383; i++) {
            if (i <= 5000) {
                i1[i - 1] = i;
            } else if (i <= 10000) {
                i3[i - 5000 - 1] = i;
            } else {
                i2[i - 10000 - 1] = i;
            }

        }
        // clusterManager.clusterAddSlots(host1,i1);
        clusterManager.clusterAddSlots(host2, i2);
        clusterManager.clusterAddSlots(host3, i3);
    }


    public void move() {
        try {
            clusterManager.clusterMoveSlots(host1, host2, 13862);
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws Exception {


        TestManaget testManaget = new TestManaget();
        //testManaget.create();
        //testManaget.remove();
        //testManaget.add();
        //testManaget.publish();
        //testManaget.move();


    }




}
