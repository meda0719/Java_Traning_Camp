package java0.conc0301;

public class Runner1 implements Runnable {
   
    @Override
    public void run() {
        for (int i = 0; i < 10000000; i++) {
            System.out.println("进入Runner1运行状态——————————" + i);
        }
    }
}
