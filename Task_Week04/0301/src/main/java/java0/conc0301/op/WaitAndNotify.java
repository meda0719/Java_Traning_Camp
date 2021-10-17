package java0.conc0301.op;

public class WaitAndNotify {
    public static void main(String[] args) {
        MethodClass methodClass = new MethodClass();
        Thread t1 = new Thread(() -> {
            try {
                methodClass.product();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            try {
                methodClass.customer();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }, "t2");
//        Thread t3 = new Thread(() -> {
//            try {
//                methodClass.customer();
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }, "t3");
        t1.start();
        t2.start();
        //t3.start();
        
    }
}

class MethodClass {
    // 定义生产最大量
    private final int MAX_COUNT = 20;
    
    int productCount = 0;
    
    public  void product() throws InterruptedException {
        System.out.println("product here");
        while (true) {
            System.out.println(Thread.currentThread().getName() + ":::run:::" + productCount);
            Thread.sleep(1000);
            synchronized(this) {
                if (productCount >= MAX_COUNT) {
                    System.out.println("货舱已满,,.不必再生产");

                    wait();
                } else {
                    productCount++;
                }

                System.out.println("here");
                notifyAll();
            }
        }
    }
    
    public  void customer() throws InterruptedException {
        System.out.println("customer here");
        while (true) {
            System.out.println(Thread.currentThread().getName() + ":::run:::" + productCount);
            Thread.sleep(1000);
            synchronized (this) {
                if (productCount <= 0) {
                    System.out.println("货舱已无货...无法消费");
                    wait();
                } else {
                    productCount--;
                }

                notifyAll();
            }
        }
    }
}