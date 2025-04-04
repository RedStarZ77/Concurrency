import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Main {
    private static final int CARS = 10;
    private static final Object MONITOR = new Object();

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(CARS);
        CountDownLatch countDownLatchFinal = new CountDownLatch(CARS);
        List<Long> list = new ArrayList<>();

        for (int i = 0; i < CARS; i++) {
            list.add(0L);
        }

        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < CARS; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String name = finalI + " водитель";
                    prepare(finalI);
                    countDownLatch.countDown();
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Long before = System.currentTimeMillis();
                    System.out.println(name + " начал поездку по 1 дороге");
                    sleepRandomTime();
                    try {
                        System.out.println(name + " стоит перед въездом в тоннель");
                        semaphore.acquire();
                        System.out.println(name + " заехал в тоннель");
                        sleepRandomTime();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        semaphore.release();
                    }
                    System.out.println(name + " выехал из тоннеля");
                    System.out.println(name + " начал поездку по 2 дороге");
                    sleepRandomTime();
                    Long after = System.currentTimeMillis();
                    Long time = after - before;
                    System.out.println(name + " ЗАКОНЧИЛ ЗАЕЗД ЗА " + time);
                    list.set(finalI, time);
                    countDownLatchFinal.countDown();
                }
            }).start();
        }

        try {
            countDownLatchFinal.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long championTime = 1_000_000;
        int championNumber = 0;
        for (int i = 0; i < 10; i++) {
            if (list.get(i) < championTime) {
                championTime = list.get(i);
                championNumber = i;
            }
            System.out.println("Водитель номер " + i + " проехал трассу за " + list.get(i));
        }
        System.out.println("ПОБЕДИТЕЛЬ " + championNumber + " КОТОРЫЙ ПРОЕХАЛ ТРАССУ ЗА " + championTime);
    }

    private static void sleepRandomTime() {
        try {
            Thread.sleep(new Random().nextInt(1000, 5000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void prepare(int index){
        System.out.println("Водитель "+ index + " начал подготовку к заезду");
        try {
            Thread.sleep(new Random().nextInt(1000, 5000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Водитель "+ index + " закончил подготовку и готов к заезду");
    }
}
