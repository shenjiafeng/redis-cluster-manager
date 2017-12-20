import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.util.JedisClusterCRC16;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiafengshen 2017/12/20.
 */
public class TestkeySlot {

    private static Logger logger = LoggerFactory.getLogger(TestkeySlot.class);

    public void soltKey(){
        Map<Integer,Integer> solt = new HashMap<Integer,Integer>();
        Map<Integer,StringBuilder> soltkeys = new HashMap<Integer,StringBuilder>();
        int key  = 0;
        for(int i=0;i<Integer.MAX_VALUE;i++){
            key = JedisClusterCRC16.getSlot(""+i);
            if(solt.containsKey(key)){
                solt.put(key,solt.get(key)+1);
                soltkeys.put(key,soltkeys.get(key).append(","+i));
            }else{
                solt.put(key,1);
                soltkeys.put(key,new StringBuilder(""+i));
            }
            if(solt.get(key) >4){
                break ;
            }
        }
        System.out.println(key+":"+solt.get(key)+":"+soltkeys.get(key));

    }

    public static void main(String[] args) {

    }

}
