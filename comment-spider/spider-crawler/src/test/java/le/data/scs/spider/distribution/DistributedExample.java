package le.data.scs.spider.distribution;

import le.data.scs.spider.distribution.task.locktask.DistributedTask;
import le.data.scs.spider.distribution.task.DistributedTaskException;
import le.data.scs.spider.distribution.task.locktask.ZKDistributedLockTaskAdapter;

import java.util.concurrent.TimeUnit;

/**
 * Created by young.yang on 2017/2/25.
 */
public class DistributedExample extends ZKDistributedLockTaskAdapter<String> {
    public DistributedExample(int timeout, TimeUnit timeUnit) {
        super(timeout, timeUnit);
    }

    @Override
    public String task() throws DistributedTaskException, InterruptedException {
        System.out.println("111111111111111111");
            Thread.sleep(120000);
        return "ok";
    }
    public static void main(String[] args) throws Exception {
        DistributedTask example = new DistributedExample(5,TimeUnit.MINUTES);
        example.runTask();
    }
}
