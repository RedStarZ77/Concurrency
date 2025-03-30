public class ATM {
    private int value = 1000;
    Object monitor = new Object();

    public void cashDec(String name, int cash) {
        synchronized (monitor) {
            System.out.println(name + " подошёл к банкомату");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (value >= cash) {
                value = value - cash;
                System.out.println(name + " вывел " + cash + " рублей. В банкомате осталось " + value + " рублей");
            } else {
                System.out.println("В банкомате недостаточно средств для " + name);
            }
        }
    }
}
