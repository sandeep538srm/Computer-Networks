import java.util.Scanner;

public class ReceiverCRC {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the received message (in binary): ");
        String receivedMessage = scanner.nextLine();
        
        System.out.print("Enter the CRC polynomial (in binary): ");
        String polynomial = scanner.nextLine();
        
        boolean isValid = checkCRC(receivedMessage, polynomial);
        if (isValid) {
            System.out.println("Received Message is Valid");
        } else {
            System.out.println("Received Message is Invalid");
        }
        
        scanner.close();
    }
    
    public static boolean checkCRC(String receivedMessage, String polynomial) {
        int polynomialLength = polynomial.length();
        StringBuilder tempMessage = new StringBuilder(receivedMessage);
        
        for (int i = 0; i < receivedMessage.length() - (polynomialLength - 1); i++) {
            if (tempMessage.charAt(i) == '1') {
                for (int j = 0; j < polynomialLength; j++) {
                    char bit1 = tempMessage.charAt(i + j);
                    char bit2 = polynomial.charAt(j);
                    if (bit1 == bit2) {
                        tempMessage.setCharAt(i + j, '0');
                    } else {
                        tempMessage.setCharAt(i + j, '1');
                    }
                }
            }
        }
        
        for (int i = receivedMessage.length() - (polynomialLength - 1); i < receivedMessage.length(); i++) {
            if (tempMessage.charAt(i) == '1') {
                return false;
            }
        }
        
        return true;
    }
}
