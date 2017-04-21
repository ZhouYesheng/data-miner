package le.data.scs.spider.login;

import le.data.scs.spider.thread.CrawlerThreadPool;
import le.data.scs.spider.utils.CommandTools;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by yangyong3 on 2017/3/13.
 */
public class ImageReco {

    public static class CommandRunner implements Callable<String>{
        private String command;

        private String result;
        public CommandRunner(String command,String result){
            this.command = command;
            this.result = result;
        }

        @Override
        public String call() throws Exception {
            CommandTools.process(command);
//            System.out.println(command);
//            System.out.println(result);
            return FileUtils.readFileToString(new File(result+".txt"));
        }

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CrawlerThreadPool threadPool = new CrawlerThreadPool();
        List<Future<String>> futures = new ArrayList<Future<String>>();
        File file = new File("D:\\le_eco\\customer-voice\\JavaVerify\\download");
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().startsWith("result")&&pathname.getName().endsWith("tiff");
            }
        });
        Callable<String> thread = null;
        for(File f:files){
            futures.add(threadPool.submit(new CommandRunner("tesseract "+f.getPath()+" "+f.getPath()+"_result"+" -l eng",f.getPath()+"_result")));
        }
        for(Future<String> line:futures){
            System.out.println("result:"+line.get());
        }
    }
}
