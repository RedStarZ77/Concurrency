import java.util.ArrayList;

public class Concurrency {
    public static void withConcurrency() {
        float[] list = new float[10_000_000];
        for (int i = 0; i < 10_000_000; i++) {
            list[i] = 1f;
        }
        long before = System.currentTimeMillis();
        float[] list1 = new float[5_000_000];
        float[] list2 = new float[5_000_000];
        System.arraycopy(list, 0, list1, 0, 5_000_000);
        System.arraycopy(list, 5_000_000, list2, 0, 5_000_000);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5_000_000; i++) {
                    float f = (float) i;
                    list1[i] = (float) (list1[i] * Math.sin(0.2f + f / 5) * Math.cos(0.2f + f / 5) * Math.cos(0.4 + f / 2));
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5_000_000; i++) {
                    float f = (float) i + 4_999_999;
                    list2[i] = (float) (list2[i] * Math.sin(0.2f + f / 5) * Math.cos(0.2f + f / 5) * Math.cos(0.4 + f / 2));
                }
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.arraycopy(list1, 0, list, 0, 5_000_000);
        System.arraycopy(list2, 0, list, 5_000_000, 5_000_000);
        long after = System.currentTimeMillis();
        System.out.println("Time with concurrency: " + (after - before));
    }

    public static void withoutConcurrency() {
        float[] list = new float[10_000_000];
        for (int i = 0; i < 10_000_000; i++) {
            list[i] = 1f;
        }
        long before = System.currentTimeMillis();
        for (int i = 0; i < 10_000_000; i++) {
            list[i] = (float) (list[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4 + i / 2));
        }
        long after = System.currentTimeMillis();
        System.out.println("Time without concurrency: " + (after - before));
    }
}
