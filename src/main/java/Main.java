public class Main {
    public static void main(String[] args) {
        System.out.println("Start");
        MyRunnable thread = new MyRunnable();
        Thread thread1 = new Thread(thread);
        thread1.start();
        for (int i = 0; i < 1000; i++) {
            System.out.print("M");
        }
        System.out.println("\nFinish");
    }
}
