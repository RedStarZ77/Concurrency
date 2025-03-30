import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ATM atm = new ATM();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                atm.cashDec("Серёга", 500);
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thread1.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                atm.cashDec("Коля", 250);
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thread2.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                atm.cashDec("Костя", 500);
            }
        });
        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thread3.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                atm.cashDec("Маша", 250);
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
