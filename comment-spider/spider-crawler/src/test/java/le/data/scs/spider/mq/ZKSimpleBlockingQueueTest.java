package le.data.scs.spider.mq;

import le.data.scs.spider.distribution.mq.MQException;
import le.data.scs.spider.distribution.mq.MessageQueue;
import le.data.scs.spider.distribution.mq.support.ZKSimpleBlockingQueue;

/**
 * Created by yangyong3 on 2017/2/22.
 */
public class ZKSimpleBlockingQueueTest {
    public static void main(String[] args) throws MQException {
        MessageQueue queue = new ZKSimpleBlockingQueue("test");
        for(int i=0;i<100;i++){
            queue.offer("yangyong_"+i);
        }
//        queue.offer(null);
        for(int i=0;i<20;i++){
            System.out.println(queue.takes(String.class,2));
        }
        queue.clear();
    }
}
