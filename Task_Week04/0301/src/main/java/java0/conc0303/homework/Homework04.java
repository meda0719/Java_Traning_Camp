package java0.conc0303.homework;

/*
  主线程循环等待
 */
public class Homework04 {
    public static void main(String[] args) throws InterruptedException{
        long start=System.currentTimeMillis();
        Fibbo fibo = new Fibbo();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                fibo.sum1();
            }
        });
        thread.start();
        while (fibo.getSum() == 0){
            Thread.sleep(1000);
        }
        System.out.println("sum: " + fibo.getSum());
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }


}
