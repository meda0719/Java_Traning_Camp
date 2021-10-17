package java0.conc0302.lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
    
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");
    
    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super(name);
        }
        @Override public void run() {
            synchronized (u) {
                try {
                    System.out.println("in " + getName());
                    LockSupport.park();
                    Thread.sleep(1);

                } catch (InterruptedException e) {
                    System.out.println("catch InterruptedException");
                }

                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("被中断了");
                    }
                    System.out.println("继续执行");

            }
        }

    }
    
    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(1000L);
        t2.start();
        Thread.sleep(3000L);
        t1.interrupt();
        Thread.sleep(3000L);
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}