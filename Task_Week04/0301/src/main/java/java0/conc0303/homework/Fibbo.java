package java0.conc0303.homework;


import java.util.concurrent.CompletableFuture;

public class Fibbo{
    private int sum;
    public int getSum(){
        return sum;
    }
    private  int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

    public int sum(){
        return sum = fibo(36);
    }

    public void sum1(){
//        sum = fibo(36);
        sum = fibo1(36);
    }

    public int fibo1(final int a){
        if ( a < 2)
            return 1;
        return CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return fibo(a-1);
        }).thenCombine(CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return fibo(a-2);
        }),(s1,s2)->{return (s1 + s2);}).join();
    }
}
