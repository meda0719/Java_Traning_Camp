package java0.conc0303.homework;

import java.util.concurrent.CountDownLatch;

/*
 CountDownLatch+ConcurrentHashMap
 */
public class Homework07 {
    public static void main(String[] args) throws InterruptedException{
        MyHashMap myHashMap = new MyHashMap();
        final CountDownLatch endLatch = new CountDownLatch(1);
        long start=System.currentTimeMillis();
        Fibbo fibo = new Fibbo();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                MyHashMap.addResult(Integer.valueOf(fibo.sum()));
                endLatch.countDown();
            }
        });
        thread.start();
        endLatch.await();

        System.out.println("sum: " + MyHashMap.getResult());
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}
