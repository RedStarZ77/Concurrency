public class MFU {
    private final Object printMonitor = new Object();
    private final Object scanMonitor = new Object();

    public void scan(int count) {
        synchronized (scanMonitor) {
            try {
                for (int i = 0; i < count; i++) {
                    Thread.sleep(2000);
                    System.out.println("Scanned " + i + " pages.");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void print(int count) {
        synchronized (printMonitor) {
            try {
                for (int i = 0; i < count; i++) {
                    Thread.sleep(3000);
                    System.out.println("Printed " + i + " pages.");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
