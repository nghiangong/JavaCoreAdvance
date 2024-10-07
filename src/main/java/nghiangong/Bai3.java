package nghiangong;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Bai3 {
    public static BlockingDeque<Message> messages = new LinkedBlockingDeque<>(10);

    public static void main(String args[]) {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();
    }
}
class Message {
    private String content;

    public Message(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

class Consumer implements Runnable {

    @Override
    public void run() {
        Random random = new Random();
        try {
            while (true) {
                Message message = Bai3.messages.take();
                System.out.println("Consumed: " + message.getContent());

                Thread.sleep(random.nextInt(10)*100);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
class Producer implements Runnable {

    @Override
    public void run() {
        try {
            int messageId = 1;
            Random random = new Random();
            while (true) {
                String content = "Message " + messageId;
                Message message = new Message(content);
                System.out.println("Produced: " + content);

                Bai3.messages.put(message);
                messageId++;

                Thread.sleep(random.nextInt(10)*100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

