import java.util.Scanner;

public class hamming_code {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the length of the data
        System.out.println("Enter the length of the data: ");
        int n = scanner.nextInt();

        // Prompt the user to enter n bits of data
        System.out.println("Enter " + n + " bits of data (separated by spaces): ");
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = scanner.nextInt();
        }

        // Encode the data
        int[] encodedData = encode(data);

        // Simulate an error by flipping a bit
        System.out.println("Simulating an error. Enter the position to flip (1 to " + encodedData.length + "): ");
        int errorPosition = scanner.nextInt();
        encodedData[errorPosition - 1] = 1 - encodedData[errorPosition - 1];

        // Decode the data
        int[] decodedData = decode(encodedData, n);

        // Display the results
        System.out.println("Original Data: " + arrayToString(data));
        System.out.println("Encoded Data: " + arrayToString(encodedData));
        System.out.println("Decoded Data: " + arrayToString(decodedData));

        scanner.close();
    }

    // Hamming encoding
    public static int[] encode(int[] data) {
        int m = 1;
        while (Math.pow(2, m) < (data.length + m + 1)) 
        {
            m++;
        }
        int[] encodedData = new int[data.length + m];
        int dataIndex = 0;
        for (int i = 0; i < encodedData.length; i++) 
        {
            if (isPowerOfTwo(i + 1)) {
                encodedData[i] = 0;  // Initialize parity bits to 0
            } else {
                encodedData[i] = data[dataIndex++];
            }
        }
        for (int i = 0; i < m; i++) 
        {
            int parityBitIndex = (int) Math.pow(2, i) - 1;
            encodedData[parityBitIndex] = calculateParity(encodedData, i + 1, encodedData.length);
        }
        return encodedData;
    }

    // Hamming decoding
    public static int[] decode(int[] encodedData, int k) {
        int m = 1;
        while (Math.pow(2, m) < encodedData.length) {
            m++;
        }

        int[] decodedData = new int[k];

        for (int i = 0; i < k; i++) {
            decodedData[i] = encodedData[(int) Math.pow(2, i) - 1];
        }

        return decodedData;
    }

    // Calculate parity for a given set of bits
    private static int calculateParity(int[] data, int parityBitIndex, int n) {
        int parity = 0;
        for (int i = parityBitIndex - 1; i < n; i += 2 * parityBitIndex) {
            for (int j = i; j < i + parityBitIndex && j < n; j++) {
                parity ^= data[j];
            }
        }
        return parity;
    }

    // Check if a number is a power of two
    private static boolean isPowerOfTwo(int x) {
        return x > 0 && (x & (x - 1)) == 0;
    }

    // Utility function to convert an array to a string
    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int bit : array) {
            sb.append(bit);
        }
        return sb.toString();
    }
}
