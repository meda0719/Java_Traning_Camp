package java0.conc0303.homework;

/*
  join方法阻塞当前线程，等待子线程完毕
 */
public class Homework05 {
    public static void main(String[] args) throws InterruptedException{
        Fibbo fibo = new Fibbo();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                fibo.sum1();
            }
        });
        thread.start();
        thread.join();
        System.out.println("sum: " + fibo.getSum());
    }
}
