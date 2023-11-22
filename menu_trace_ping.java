import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class menu_trace_ping {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter your selection:");
            System.out.println("1. Ping any IP address");
            System.out.println("2. Trace route to any IP address");
            System.out.println("3. Exit");

            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    pingIPAddress();
                    break;
                case 2:
                    traceRouteIPAddress();
                    break;
                case 3:
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static int getUserChoice() {
        int choice = 0;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            choice = Integer.parseInt(br.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid choice.");
        }
        return choice;
    }

    private static void pingIPAddress() {
        try {
            System.out.print("Enter the IP address to ping: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String ipAddress = br.readLine();

            ProcessBuilder processBuilder = new ProcessBuilder("ping", ipAddress);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void traceRouteIPAddress() {
        try {
            System.out.print("Enter the IP address for trace route: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String ipAddress = br.readLine();

            ProcessBuilder processBuilder = new ProcessBuilder("tracert", ipAddress);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));                               
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}