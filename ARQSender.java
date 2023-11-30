import java.io.*;
import java.net.*;

public class ARQSender {
    public static void main(String[] args) {
        final int serverPort = 12345;
        final String serverAddress = "localhost";
        final int TIMEOUT = 2000; // Timeout in milliseconds
        final int MAX_TRIES = 3; // Maximum number of retransmission attempts

        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            String message = "Hello, Receiver!";
            int sequenceNumber = 0;

            for (int tries = 0; tries < MAX_TRIES; tries++) {
                // Send the message with sequence number
                String packet = sequenceNumber + ": " + message;
                out.println(packet);
                System.out.println("Sender sent: " + packet);

                try {
                    // Set a timeout for acknowledgment
      if(message.equals("END"))
                        break;
                    socket.setSoTimeout(TIMEOUT);

                    // Wait for ACK
                    String ack = in.readLine();
                    System.out.println("Sender received: " + ack);

                    // Check if the received ACK matches the expected sequence number
                    if (ack != null && ack.equals(sequenceNumber + ": ACK")) {
                        // Move to the next sequence number
                        sequenceNumber = (sequenceNumber + 1) % 2;
                    }
                    
                    message = "END";
                } catch (SocketTimeoutException e) {
                    System.out.println("Timeout, retransmitting...");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}