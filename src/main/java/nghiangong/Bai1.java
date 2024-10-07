package nghiangong;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Bai1 {
    public static void main(String args[]) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        WriteTask writeTask = new WriteTask();

        scheduler.scheduleAtFixedRate(writeTask, 1, 1, TimeUnit.SECONDS);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String string = scanner.nextLine();
            if (string.equalsIgnoreCase("stop")) {
                scheduler.shutdown();
                System.out.println("Program stopped!");
                break;
            }
        }
    }
}
class WriteTask implements Runnable {
    Random random = new Random();
    PrintWriter printWriter;
    public WriteTask() {
        try {
            printWriter = new PrintWriter("output.txt");
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        int number = random.nextInt(100);
        printWriter.println(number);
        printWriter.flush();
    }
}
