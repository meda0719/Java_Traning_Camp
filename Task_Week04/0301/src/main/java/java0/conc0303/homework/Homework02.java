package java0.conc0303.homework;

import java.util.concurrent.CompletableFuture;

/*
 CompletableFuture
 */
public class Homework02 {
    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
//        CompletableFuture.supplyAsync(()->{return Integer.valueOf(fibo(36));})
//                .thenAccept(v -> { System.out.println(v);});
        //没有返回值，不知道为什么
        CompletableFuture.supplyAsync(()->{return Integer.valueOf(new Fibbo().sum());}).thenAccept(v -> { System.out.println("=====>2.消费");System.out.println("consumer: " + v);});

//        int result = sum(); //这是得到的返回值
//        System.out.println("异步计算结果为：" + result1);
        Thread.sleep(5000);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程

    }

//    private static int sum() {
//        return fibo(36);
//    }
//
//    private static int fibo(int a) {
//        if ( a < 2)
//            return 1;
//        return fibo(a-1) + fibo(a-2);
//    }
}


