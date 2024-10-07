package nghiangong.Bai4;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.Random;

public class Client {
    private static final Logger logger = LogManager.getLogger(Client.class);

    public static void main(String args[]) {
        Properties properties = loadConfig();
        String serverIp = properties.getProperty("server.ip");
        int serverPort = Integer.parseInt(properties.getProperty("server.port"));
        int connection_timeout = Integer.parseInt(properties.getProperty("connection.timeout"));
        int send_timeout = Integer.parseInt(properties.getProperty("send.timeout"));

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(serverIp, serverPort), connection_timeout);
            socket.setSoTimeout(send_timeout);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Random random = new Random();

            while (true) {
                String message = generateRandomString(random, 10);
                out.println(message);
                out.flush();
                System.out.println("Sent: " + message);
                Thread.sleep(1000);
            }
        } catch (SocketTimeoutException e) {
            logger.error("Timeout error: ", e);
        } catch (IOException | InterruptedException e) {
            logger.error("Error sending data: ", e);
        }
    }
    private static String generateRandomString(Random random, int length) {
        StringBuilder sb = new StringBuilder(length);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    private static Properties loadConfig () {
        Properties properties = new Properties();
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            logger.error("Error loading config.properties: ", e);
        }
        return properties;
    }
}
