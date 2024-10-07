package nghiangong.Bai4;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;

public class Server {
    static final Logger logger = Logger.getLogger(Server.class);

    public static void main(String arg[]) {
        try {
            Properties properties = loadConfig();
            String ip = properties.getProperty("server.ip");
            int port = Integer.parseInt(properties.getProperty("server.port"));
            int receive_timeout = Integer.parseInt(properties.getProperty("receive.timeout"));

            ServerSocket serverSocket = new ServerSocket(port, 5, InetAddress.getByName(ip));
            System.out.println("Server listening on "+ ip + ":" + port);

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    clientSocket.setSoTimeout(receive_timeout);
                    System.out.println("Connection from " + clientSocket.getInetAddress());
                    handleClient(clientSocket);
                }
                catch (IOException e) {
                    logger.error("Connection error: ", e);
                }
            }

        } catch (IOException e) {
            logger.error("Server initialization error: ", e);
        }

    }
    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received: " + message);
            }
        } catch (SocketTimeoutException e) {
            logger.warn("Receive timeout: No data received from client within the specified time.", e);
        } catch (IOException e) {
            logger.error("Error receiving data from client: ", e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                logger.error("Error closing connection: ", e);
            }
        }
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
