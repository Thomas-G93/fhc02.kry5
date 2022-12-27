package UE2_SecureHashing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class P2_RainbowTable {

    /*
    Sie haben folgenden Passwort Hash-Wert aus einer Datenbank erhalten.
    340d600392818df2413382dc7d8325c360d83ea49a262d31760348484bbc10b5

    Schreiben Sie ein Programm, welches eine eigene „Rainbow Table“ erstellt, umso den zugehörigen Plain-Text zu finden.

    Tipp: Verwenden Sie dazu folgende Passwort Liste:
    https://github.com/berandal666/Passwords/blob/master/best110.txt

    Ziel: Eingabe des Hash-Wertes mittels User-Input Ausgabe des Hashes, Plain-Texts und verwendeter Hashfunktion auf der Konsole.
    Erklärung des Codes mittels Kommentare Plain-Text des Hashes als Kommentar festhalten

    */
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        // The path to the text file containing the passwords
        String filePath = "resources/best110.txt";
        // Create rainbow table
        Map<String, Map<String, String>> rainbowTable = createRainbowTable(filePath);
        printRainbowTable(rainbowTable);
        System.out.println();
        System.out.println();

        // Read the input hash from the command line
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter HASH you want to crack or type \"d\" for default hash example: ");
        String inputHash = scanner.nextLine();

        if (inputHash.equals("d")) {
            /*
            Hash: 340d600392818df2413382dc7d8325c360d83ea49a262d31760348484bbc10b5
            Password: windows
            Hash Function: SHA-256
            */
            inputHash = "340d600392818df2413382dc7d8325c360d83ea49a262d31760348484bbc10b5";
        }

        // Check if the input hash is present in the Rainbow Table
        if (rainbowTable.containsKey(inputHash)) {
            // If the hash is present, get the corresponding password and hash function
            String password = rainbowTable.get(inputHash).get("password");
            String hashFunction = rainbowTable.get(inputHash).get("hashFunction");

            // Print the password and hash function
            System.out.println("Hash: " + inputHash);
            System.out.println("Password: " + password);
            System.out.println("Hash Function: " + hashFunction);
        } else {
            // If the hash is not present in the Rainbow Table, print a message
            System.out.println("Hash not found in Rainbow Table.");
        }

    }

    private static Map<String, Map<String, String>> createRainbowTable(String filePath) throws IOException, NoSuchAlgorithmException {
        // Create a map to store the hashes and their corresponding passwords and hash functions
        Map<String, Map<String, String>> rainbowTable = new HashMap<>();

        // Read the passwords from the text file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String password;
            while ((password = reader.readLine()) != null) {

                // Calculate the hashes of the password using multiple hash functions
                String md5Hash = calculateHash(password, "MD5");
                String sha1Hash = calculateHash(password, "SHA-1");
                String sha256Hash = calculateHash(password, "SHA-256");
                String sha512Hash = calculateHash(password, "SHA-512");

                // Add the hashes and the password to the Rainbow Table, along with the hash function used

                Map<String, String> hashInfo1 = new HashMap<>();
                hashInfo1.put("password", password);
                hashInfo1.put("hashFunction", "MD5");
                rainbowTable.put(md5Hash, hashInfo1);

                Map<String, String> hashInfo2 = new HashMap<>();
                hashInfo2.put("password", password);
                hashInfo2.put("hashFunction", "SHA-1");
                rainbowTable.put(sha1Hash, hashInfo2);

                Map<String, String> hashInfo3 = new HashMap<>();
                hashInfo3.put("password", password);
                hashInfo3.put("hashFunction", "SHA-256");
                rainbowTable.put(sha256Hash, hashInfo3);

                Map<String, String> hashInfo4 = new HashMap<>();
                hashInfo4.put("password", password);
                hashInfo4.put("hashFunction", "SHA-512");
                rainbowTable.put(sha512Hash, hashInfo4);
            }
        }

        return rainbowTable;
    }

    private static void printRainbowTable(Map<String, Map<String, String>> rainbowTable) {
        System.out.println("Rainbow Table:");
        // Iterate through the entries in the Rainbow Table and print them with their corresponding password and hash function
        for (Map.Entry<String, Map<String, String>> entry : rainbowTable.entrySet()) {
            String hash = entry.getKey();
            String password = entry.getValue().get("password");
            String hashFunction = entry.getValue().get("hashFunction");
            System.out.println("Hash: " + hash + " - Password: " + password + " - Hash Function: " + hashFunction);
        }
    }

    private static String calculateHash(String input, String algorithm) throws NoSuchAlgorithmException {
        // Use the specified hash algorithm to calculate the hash
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(input.getBytes());
        byte[] hash = md.digest();

        return toHexString(hash);
    }

    private static String toHexString(byte[] data) {
        // Convert the data to hexadecimal notation
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : data) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }

}
