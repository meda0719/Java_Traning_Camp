
package java0.conc0301;

import java.io.IOException;

public class RunnerMain {

    public static void main(String[] args) throws IOException {

        Runner1 runner1 = new Runner1();
        Thread thread1 = new Thread(runner1, "meda1");

        Runner2 runner2 = new Runner2();
        Thread thread2 = new Thread(runner2, "mmmmmd2");

        thread1.start();
        thread2.start();

        thread2.interrupt();  // i = true

        System.out.println("dddddddd" + Thread.activeCount());
        
        Thread.currentThread().getThreadGroup().list();
        System.out.println("cccccc" + Thread.currentThread().getThreadGroup().getParent().activeGroupCount());
        Thread.currentThread().getThreadGroup().getParent().list();
    
        
    }
}
