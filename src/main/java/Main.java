import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        long before = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch countDownLatch = new CountDownLatch(3);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                long result = 0;
                for (int i = 0; i <= 1_000_000; i++) {
                    if (i % 2 == 0) {
                        result += i;
                    }
                }
                System.out.println("Сумма всех чётных чисел до миллиона: " + result);
                countDownLatch.countDown();
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                long result = 0;
                for (int i = 0; i <= 1_000_000; i++) {
                    if (i % 7 == 0) {
                        result += i;
                    }
                }
                System.out.println("Сумма всех чисел, которые делятся на 7 без остатка до миллиона: " + result);
                countDownLatch.countDown();
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int randomNumber;
                ArrayList<Integer> array = new ArrayList<>(100);
                for (int i = 0; i < 100; i++) {
                    randomNumber = random.nextInt(1000);
                    array.add(randomNumber);
                }
                long result = 0;
                for (Integer arr : array) {
                    if (arr % 2 == 0) {
                        result += arr;
                    }
                }
                System.out.println("Заполнив коллекцию 1000 элементами и получив сумму чётных чисел в ней, результат: " + result);
                countDownLatch.countDown();
            }
        });
        executorService.shutdown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long after = System.currentTimeMillis();
        System.out.println("Времени было потрачено: " + (after - before));
    }
}
