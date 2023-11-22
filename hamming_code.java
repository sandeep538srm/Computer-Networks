public class hamming_code {
    public static void main(String[] args) {
        // Example data to be encoded (4 bits)
        int[] data = {1, 0, 1, 0};
        
        // Encode the data
        int[] encodedData = encode(data);
        
        // Simulate an error by flipping a bit
        encodedData[3] = 1 - encodedData[3];
        
        // Decode the data
        int[] decodedData = decode(encodedData);
        
        // Display the results
        System.out.println("Original Data: " + arrayToString(data));
        System.out.println("Encoded Data: " + arrayToString(encodedData));
        System.out.println("Decoded Data: " + arrayToString(decodedData));
    }

    // Hamming(7,4) encoding
    public static int[] encode(int[] data) {
        int[] encodedData = new int[7];
        
        // Calculate parity bits (P1, P2, and P4)
        encodedData[2] = data[0];
        encodedData[4] = data[1];
        encodedData[5] = data[2];
        encodedData[6] = data[3];
        
        encodedData[0] = calculateParity(encodedData, 1, 3, 5, 7);
        encodedData[1] = calculateParity(encodedData, 2, 3, 6, 7);
        encodedData[3] = calculateParity(encodedData, 4, 5, 6, 7);
        
        return encodedData;
    }

    // Hamming(7,4) decoding
    public static int[] decode(int[] encodedData) {
        int[] decodedData = new int[4];
        
        int p1 = calculateParity(encodedData, 1, 3, 5, 7);
        int p2 = calculateParity(encodedData, 2, 3, 6, 7);
        int p3 = encodedData[0]; // Stored parity bit
        int p4 = calculateParity(encodedData, 4, 5, 6, 7);
        
        // Check for errors and correct if necessary
        int errorBit = p1 * 1 + p2 * 2 + p3 * 4 + p4 * 8;
        if (errorBit != 0) {
            System.out.println("Error detected at bit " + errorBit + ". Correcting...");
            encodedData[errorBit - 1] = 1 - encodedData[errorBit - 1];
        }
        
        // Extract the original data
        decodedData[0] = encodedData[2];
        decodedData[1] = encodedData[4];
        decodedData[2] = encodedData[5];
        decodedData[3] = encodedData[6];
        
        return decodedData;
    }

    // Calculate parity for a given set of bits
    private static int calculateParity(int[] data, int... bits) {
        int parity = 0;
        for (int bit : bits) {
            parity ^= data[bit - 1];
        }
        return parity;
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