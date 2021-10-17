package java0.conc0303.homework;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/*
 stream并行计算
 */
public class Homework06 {
    public static void main(String[] args) throws Exception {
        final int ITEM_NUM = 500000; // 计算斐波那契数列的第 ITEM_NUM 项

        System.out.println("开始迭代计算...");
        long begin = System.nanoTime();

        BigInteger fi1 = iterativeFibonacci(ITEM_NUM);

        long end = System.nanoTime();
        double time = (end - begin) / 1E9;
        System.out.printf("迭代计算用时: %.3f\n\n", time);

        /* ------------------------------ */
        System.out.println("开始并行计算...");
        begin = System.nanoTime();

        BigInteger fi2 = parallelFibonacci(ITEM_NUM, 1);

        end = System.nanoTime();
        time = (end - begin) / 1E9;
        System.out.printf("并行计算用时: %.3f\n\n", time);

        /*-------------------------------*/
        System.out.println("开始流式计算...");
        begin = System.nanoTime();
        BigInteger fi3 = streamFibonacci(ITEM_NUM, 10);
        end = System.nanoTime();
        time = (end - begin) / 1E9;
        System.out.printf("流式计算用时: %.3f\n\n", time);

        System.out.println("fi1 == fi2：" + (fi1.equals(fi2)));
        System.out.println("fi1 == fi3：" + (fi1.equals(fi3)));
    }

    static BigInteger iterativeFibonacci(int n) {
        BigInteger n1 = BigInteger.ONE;
        BigInteger n2 = BigInteger.ONE;
        BigInteger fi = BigInteger.valueOf(2); // n1 + n2

        for (int i = 2; i <= n; i++) {
            fi = n1.add(n2);
            n1 = n2;
            n2 = fi;
        }

        return fi;
    }

    static BigInteger parallelFibonacci(int itemNum, int threadNum) throws Exception {
        final Matrix matrix = new Matrix(1, 1, 1, 0);
        final Matrix primary = new Matrix(1, 0, 1, 0); // (f0, 0; f1, 0)
        final int workload = itemNum / threadNum;      // 每个线程要计算的 相乘的项数
        // (num / threadNum) 可能存在除不尽的情况，所以最后一个任务计算所有剩下的项数
        final int lastWorkload = itemNum - workload * (threadNum - 1);

        List<Callable<Matrix>> tasks = new ArrayList<>(threadNum);
        for (int i = 0; i < threadNum; i++) {
            if (i < threadNum - 1) {
                // 为了简洁，使用 Lambda 表达式替代要实现 Callable<Matrix> 的匿名内部类
                tasks.add(() -> matrix.pow(workload));
            } else {
                tasks.add(() -> matrix.pow(lastWorkload));
            }
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(threadNum);
        List<Future<Matrix>> futures = threadPool.invokeAll(tasks); // 执行所有任务，invokeAll 会阻塞直到所有任务执行完毕

        Matrix result = primary.copy();
        for (Future<Matrix> future : futures) { // (matrix ^ n) * (f0, 0; f1, 0)
            result = result.mul(future.get());
        }

        threadPool.shutdown();

        return result.c;
    }

    static BigInteger streamFibonacci(int itemNum, int threadNum) {
        final Matrix matrix = new Matrix(1, 1, 1, 0);
        final Matrix primary = new Matrix(1, 0, 1, 0);
        final int workload = itemNum / threadNum;
        final int lastWorkload = itemNum - workload * (threadNum - 1);

        // 流式 API
        return IntStream.range(0, threadNum)    // 产生 [0, threadNum) 区间，用于将任务切分
                .parallel()                     // 使流并行化
                .map(i -> i < threadNum - 1 ? workload : lastWorkload)
                .mapToObj(w -> matrix.pow(w))   // map    ->  mN = matrix ^ workload
                .reduce((m1, m2) -> m1.mul(m2)) // reduce ->  m = m1 * m2 * ... * mN
                .map(m -> m.mul(primary))       // map    ->  m = m * primary
                .get().c;                       // get    ->  m.c
    }




}
