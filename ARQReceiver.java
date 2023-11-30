import java.io.*;
import java.net.*;
import java.util.Random;

public class ARQReceiver {
    public static void main(String[] args) {
        final int serverPort = 12345;

        try (ServerSocket serverSocket = new ServerSocket(serverPort);
             Socket clientSocket = serverSocket.accept();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){

            int expectedSequenceNumber = 0;

            while (true) {
                String packet = in.readLine();
                
                if (packet != null) {
                    // Simulate packet loss
                    if (new Random().nextDouble() < 0.2) {
                        System.out.println("Packet lost: " + packet);
                        continue;
                    }
      if(packet.equals(expectedSequenceNumber+": "+"END"))
                        break;
                    
                    int receivedSequenceNumber = Integer.parseInt(packet.split(":")[0].trim());

                    if (receivedSequenceNumber == expectedSequenceNumber) {
                        System.out.println("Receiver received: " + packet);
                        // Send ACK
                        out.println(expectedSequenceNumber + ": ACK");
                        System.out.println("Receiver sent: " + expectedSequenceNumber + ": ACK");
                        expectedSequenceNumber = (expectedSequenceNumber + 1) % 2;
      
                    } else {
                        // Discard out-of-order packets
                        System.out.println("Received out-of-order packet, discarding.");
                    }
      
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}