import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        startTimer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Concurrency.withConcurrency();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Concurrency.withoutConcurrency();
            }
        }).start();
    }

    private static void startTimer(){
        Thread timer = new Thread(new Runnable() {
            @Override
            public void run() {
                int seconds = 0;
                while (true){
                    System.out.println(seconds++);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        timer.setDaemon(true);
        timer.start();
    }
}
