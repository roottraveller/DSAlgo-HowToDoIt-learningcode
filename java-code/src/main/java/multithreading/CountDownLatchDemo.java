package multithreading;

import java.util.concurrent.CountDownLatch;

/*
 * A CountDownLatch in Java is a synchronization aid that allows one or more threads to wait until a 
 * set of operations being performed in other threads completes. It is initialized with a count, and 
 * each call to the countDown() method decrements the count. Threads can wait for the count to reach 
 * zero by calling the await() method. Once the count reaches zero, all waiting threads are released. 
 * the CountDownLatch cannot be reused.
 */

/*
 * CountDownLatch Class Main Methods:
 * 
 * CountDownLatch:
 *       ├── await()
 *       ├── await(long timeout, TimeUnit unit)
 *       ├── countDown()
 *       └── getCount()
 */


public class CountDownLatchDemo {
    public static void main(String[] args) {
        // Create a CountDownLatch with a count of 3
        CountDownLatch latch = new CountDownLatch(3);

        // Create and start three worker threads
        Thread worker1 = new Thread(new WorkerRunnable("Worker 1", latch));
        Thread worker2 = new Thread(new WorkerRunnable("Worker 2", latch));
        Thread worker3 = new Thread(new WorkerRunnable("Worker 3", latch));

        worker1.start();
        worker2.start();
        worker3.start();

        try {
            // Main thread waits for the latch to count down to zero
            latch.await();
            System.out.println("All workers have completed their tasks.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class WorkerRunnable implements Runnable {
        private final CountDownLatch latch;

        public WorkerRunnable(String name, CountDownLatch latch) {
            super(name); // Set the thread name
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " is performing its task...");
                Thread.sleep((long) (Math.random() * 1000)); // Simulate task execution time
                System.out.println(Thread.currentThread().getName() + " has completed its task.");
                latch.countDown(); // Decrement the count
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
