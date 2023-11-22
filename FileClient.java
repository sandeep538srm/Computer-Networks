import java.io.*;
import java.net.Socket;
public class FileClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 12345;
        String fileName = "example.txt";

        try (Socket socket = new Socket(serverAddress, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            // Send file name to the server
            out.writeObject(fileName);
            // Open the file on the client
            File file = new File(fileName);
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                // Send file content to the server
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);}
                out.flush();
                System.out.println("File sent successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
