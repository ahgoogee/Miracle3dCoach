package api;

public class SyncTest {
    public static void main(String[] args) throws InterruptedException {
        Object ob = new Object();
        Thread t1 = new Thread(()->{
            synchronized (ob){
                try {
                    System.out.println("释放线程开始sleep");
                    Thread.sleep(5000);
                    System.out.println("释放线程结束sleep");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("释放线程");
                ob.notify();
            }
        });

        Thread t2 = new Thread(()->{
            synchronized (ob){
                try {
                    System.out.println("[[线程等待]]");
                    ob.wait();
                    System.out.println("[[线程结束等待]]");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t2.start();
        t1.start();
        t1.join();

    }








}
