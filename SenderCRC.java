import java.util.Scanner;

public class SenderCRC {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the message (in binary): ");
        String message = scanner.nextLine();
        
        System.out.print("Enter the CRC polynomial (in binary): ");
        String polynomial = scanner.nextLine();
        
        String encodedMessage = encodeMessage(message, polynomial);
        System.out.println("Encoded Message: " + message+encodedMessage);
        
        scanner.close();
    }
    
    public static String encodeMessage(String message, String polynomial) {
        int messageLength = message.length();
        int polynomialLength = polynomial.length();
        // int totalLength = messageLength + polynomialLength - 1;
        
        StringBuilder encodedMessage = new StringBuilder(message);
        
        for (int i = 0; i < polynomialLength - 1; i++) {
            encodedMessage.append("0");
        }
        
        for (int i = 0; i < messageLength; i++) {
            if (encodedMessage.charAt(i) == '1') {
                for (int j = 0; j < polynomialLength; j++) {
                    char bit1 = encodedMessage.charAt(i + j);
                    char bit2 = polynomial.charAt(j);
                    if (bit1 == bit2) {
                        encodedMessage.setCharAt(i + j, '0');
                    } else {
                        encodedMessage.setCharAt(i + j, '1');
                    }
                }
            }
        }
        
        return encodedMessage.substring(messageLength);
    }
}