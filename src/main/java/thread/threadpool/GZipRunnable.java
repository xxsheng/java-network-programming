package thread.threadpool;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class GZipRunnable implements Runnable {
    private final File input;

    public GZipRunnable(File input) {
        this.input = input;
    }

    @Override
    public void run() {
        //不压缩已经压缩的文件
        if (!input.getName().endsWith(".gz")) {
            File file = new File(input.getParentFile(), input.getName() + ".gz");
            if (!file.exists()) { //不覆盖已经存在的文件
                try {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(input));
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
                    int b;
                    while (((b = bufferedInputStream.read()) != -1)) {
                        bufferedOutputStream.write(b);
                    }
                    bufferedOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
