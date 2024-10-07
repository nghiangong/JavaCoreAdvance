package nghiangong;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Bai2 {
    public static void main(String args[]) {
        System.out.print("Fill n: ");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        WriteTask2 writeTask2 = new WriteTask2();
        scheduler.scheduleAtFixedRate(writeTask2, 0, n, TimeUnit.SECONDS);
        try {
            Thread.sleep(n*60*1000);
            scheduler.shutdown();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}
class WriteTask2 implements Runnable {
    Random random = new Random();

    @Override
    public void run() {
        int number = random.nextInt(100);
        System.out.println(random.nextInt(100));
    }
}
