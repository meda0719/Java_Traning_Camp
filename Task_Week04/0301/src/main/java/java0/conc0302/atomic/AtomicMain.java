
package java0.conc0302.atomic;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class AtomicMain {

    public static void main(String[] args) {
        final AtomicCount count = new AtomicCount();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            new Thread(()-> {
//                @Override
//                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        count.add(); 
                    }
//                }
            }).start();
        }
        System.out.println(" ... " + ( System.currentTimeMillis() - start ));

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("num=" + count.getNum());
    }

}
