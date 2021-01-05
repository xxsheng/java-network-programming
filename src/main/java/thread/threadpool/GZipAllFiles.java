package thread.threadpool;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GZipAllFiles {
    public final static int THREAD_COUNT = 4;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        for (String arg : args) {

            File file = new File(arg);
            if (file.exists()) {
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    assert files != null;
                    for (File value : files) {
                        if (!value.isDirectory()) { // 不处理递归目录
                            Runnable runnable = new GZipRunnable(value);
                            executorService.submit(runnable);
                        }
                    }
                } else {
                    executorService.submit(new GZipRunnable(file));
                }
            }
        }
        executorService.shutdown();
    }
}
