package le.data.scs.spider.distribution.task.leadertask;

import le.data.scs.spider.distribution.task.DistributedTaskException;
import le.data.scs.spider.zkclient.ZKClientFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.state.ConnectionState;

import java.util.Scanner;

/**
 * Created by young.yang on 2017/2/25.
 */
public abstract class ZKDistributedLeaderTaskAdapter<T> implements LeaderTask<T> {

    private static final String base_leader_path = "/spider/leader/";

    protected abstract T task() throws Exception;

    protected abstract T change();

    @Override
    public T runTask(boolean is_stay) throws DistributedTaskException {
        LeaderSelectorListener listener = new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                task();
            }

            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                change();
            }
        };
        CuratorFramework client = ZKClientFactory.getZKClient();
        client.getConnectionStateListenable().addListener(new ZKConnectionStateListener());
        LeaderSelector selector = new LeaderSelector(client, base_leader_path + this.getClass().getName(), listener);
        selector.autoRequeue();
        selector.start();
        if (is_stay)
            new Scanner(System.in).nextLine();
        return null;
    }
}
