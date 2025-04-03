public class Main {

    public static void main(String[] args) {
        MFU mfu = new MFU();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mfu.scan(10);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mfu.print(10);
            }
        }).start();
    }
}
