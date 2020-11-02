package thread.task;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultithreadMaxFinder {

    public static int max(int[] data ) throws ExecutionException, InterruptedException {
        if (data.length == 1) {
            return data[0];
        } else if (data.length == 0) {
            throw new IllegalArgumentException();
        }

        FindMaxTask findMaxTask = new FindMaxTask(data, 0, data.length / 2);
        FindMaxTask findMaxTask1 = new FindMaxTask(data, data.length/2, data.length);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> submit = executorService.submit(findMaxTask);
        Future<Integer> submit1 = executorService.submit(findMaxTask1);
        return Math.max(submit.get(), submit1.get());
    }
}
