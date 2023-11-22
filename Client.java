import java.io.*;
import java.net.*;

public class Client {
    // Initialize socket and output stream
    private Socket socket = null;
    private DataOutputStream out = null;

    // Constructor to put IP address and port
    public Client(String address, int port) {
        // Establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // Send output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
            return;
        } catch (IOException i) {
            System.out.println(i);
            return;
        }

        // Read input from the terminal
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        String line = "";

        try {
            // Keep reading until "Over" is input
            while (!line.equals("Over")) {
                line = inputReader.readLine();
                out.writeUTF(line);
            }
        } catch (IOException i) {
            System.out.println(i);
        }

        // Close the connection
        try {
            inputReader.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        new Client("192.168.29.171", 5000);
    }
}