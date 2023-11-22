import java.util.Scanner;

public class class_full_class_less_menu  {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nIP Address Analysis Menu:");
            System.out.println("1. Analyze Classful IP Address");
            System.out.println("2. Analyze Classless IP Address");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1/2/3): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    analyzeClassfulIPAddress(scanner);
                    break;
                case 2:
                    analyzeClasslessIPAddress(scanner);
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    public static void analyzeClassfulIPAddress(Scanner scanner) {
        System.out.print("Enter the IPv4 address in dotted decimal notation: ");
        String ipAddress = scanner.next();

        // Extract the first octet to determine the network class
        String[] octets = ipAddress.split("\\.");
        int firstOctet = Integer.parseInt(octets[0]);

        char networkClass;
        String networkID;
        String hostID;
        int networkSize;

        if (firstOctet >= 0 && firstOctet <= 127) {
            networkClass = 'A';
            networkID = octets[0];
            hostID = ipAddress.substring(ipAddress.indexOf('.') + 1);
            networkSize = 16_777_214; // 2^24 - 2
        } else if (firstOctet >= 128 && firstOctet <= 191) {
            networkClass = 'B';
            networkID = octets[0] + "." + octets[1];
            hostID = ipAddress.substring(ipAddress.lastIndexOf('.') + 1);
            networkSize = 65_534; // 2^16 - 2
        } else if (firstOctet >= 192 && firstOctet <= 223) {
            networkClass = 'C';
            networkID = octets[0] + "." + octets[1] + "." + octets[2];
            hostID = octets[3];
            networkSize = 254; // 2^8 - 2
        } else if (firstOctet >= 224 && firstOctet <= 239) {
            networkClass = 'D';
            networkID = "N/A";
            hostID = "N/A";
            networkSize = 268_435_456;
        } else {
            networkClass = 'E';
            networkID = "N/A";
            hostID = "N/A";
            networkSize = 268_435_456;
        }

        System.out.println("Network Class: " + networkClass);
        System.out.println("Network ID: " + networkID);
        System.out.println("Host ID: " + hostID);
        System.out.println("Network Size: " + networkSize);
    }

    public static void analyzeClasslessIPAddress(Scanner scanner) {
        System.out.print("Enter the IPv4 address in dotted decimal notation: ");
        String ipAddress = scanner.next();
        System.out.print("Enter the subnet prefix size (e.g., 24 for /24): ");
        int prefixSize = scanner.nextInt();

        // Validate prefix size (must be between 0 and 32)
        if (prefixSize < 0 || prefixSize > 32) {
            System.out.println("Invalid prefix size. It should be between 0 and 32.");
            return;
        }
        // Split the IP address into octets
        String[] octets = ipAddress.split("\\.");
        if (octets.length != 4) {
            System.out.println("Invalid IP address format.");
            return;
        }
        // Handle the case where prefix size is 32 (specific host)
        if (prefixSize == 32) {
            System.out.println("This is a specific host address. No subnet information available.");
            return;
        }
        // Convert the octets to binary strings
        StringBuilder binaryIP = new StringBuilder();
        for (String octet : octets) {
            int value = Integer.parseInt(octet);
            String binaryOctet = String.format("%8s", Integer.toBinaryString(value)).replace(' ', '0');
            binaryIP.append(binaryOctet);
        }

        // Create the subnet mask
        StringBuilder subnetMask = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            if (i < prefixSize) {
                subnetMask.append('1');
            } else {
                subnetMask.append('0');
            }
        }
        String binarySubnetID = binaryIP.substring(0, prefixSize);

        // Calculate the host ID
        int hostIDDecimal = Integer.parseInt(binaryIP.substring(prefixSize), 2);
        int subnetSize = (int) Math.pow(2, 32 - prefixSize);

        // Calculate the decimal subnet ID
        int subnetID = Integer.parseInt(binarySubnetID, 2);
        int subnetIDDecimal = subnetID * subnetSize;

        // Convert the decimal subnet ID to dot-decimal format
        String subnetIDDotDecimal = String.format(
            "%d.%d.%d.%d",
            (subnetIDDecimal >> 24) & 0xFF,
            (subnetIDDecimal >> 16) & 0xFF,
            (subnetIDDecimal >> 8) & 0xFF,
            subnetIDDecimal & 0xFF
        );

        // Convert the decimal host ID to dot-decimal format
        String hostIDDotDecimal = String.format(
            "0.0.%d.%d",
            (hostIDDecimal >> 8) & 0xFF,
            hostIDDecimal & 0xFF
        );

        System.out.println("Subnet ID: " + subnetIDDotDecimal);
        System.out.println("Host ID: " + hostIDDotDecimal);
        System.out.println("Subnet Size: " + subnetSize);
    }
}