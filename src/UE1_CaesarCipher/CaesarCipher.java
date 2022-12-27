package UE1_CaesarCipher;

public class CaesarCipher {

    public static void main(String[] args) {


        String text = "This Text should be encrypted";
        int key = 5;

        System.out.println("Original:  " + text);

        String toEncrypt = encrypt(text, key);
        System.out.println("Encrypted: " + toEncrypt);

        String toDecrypt = decrypt(toEncrypt, key);
        System.out.println("Decrypted: " + toDecrypt);

    }

    /**
     * Decrypts a text with a key
     * @param s the text to decrypt
     * @param key the key to decrypt with
     * @return the decrypted text
     */
    private static String decrypt(String s, int key) {
        // modulo 26 to get a valid key in case the key is too big
        key = key % 26;

        // new StringBuilder to store the decrypted text
        StringBuilder decrypted = new StringBuilder();
        // loop through the text
        for (int i = 0; i < s.length(); i++) {
            // get the current character
            char c = s.charAt(i);
            // check if the character is a letter -> relevant for whitespaces
            if (Character.isLetter(c)) {
                // check if the character is uppercase -> relevant for matching the right case after decryption
                if (Character.isUpperCase(c)) {
                    // decrypt the character by subtracting the key
                    c = (char) (c - key);
                    // check if the character is out of bounds -> if so, add 26 to get the right character
                    if (c < 'A') {
                        c = (char) (c + 26);
                    }
                    // same as above, but for lowercase characters and with 'a' instead of 'A'
                } else if (Character.isLowerCase(c)) {
                    c = (char) (c - key);
                    if (c < 'a') {
                        c = (char) (c + 26);
                    }
                }
            }
            // append the decrypted character to the StringBuilder
            decrypted.append(c);
        }
        // return the decrypted text
        return decrypted.toString();
    }


    /**
     * Encrypts a text with a key
     * @param s the text to encrypt
     * @param key the key to encrypt with
     * @return the encrypted text
     */
    private static String encrypt(String s, int key){
        // modulo 26 to get a valid key in case the key is too big
        key = key % 26;

        // new StringBuilder to store the encrypted text
        StringBuilder encrypted = new StringBuilder();
        // loop through the input text
        for (int i = 0; i < s.length(); i++) {
            // get the character at the current index
            char c = s.charAt(i);
            // check if the character is a letter // if it is a letter, encrypt it // for whitespaces, just append them
            if (Character.isLetter(c)) {
                // check if the character is uppercase -> relevant for overflow -> (char + key > 'Z')
                if (Character.isUpperCase(c)) {
                    // add offset of key to char to encrypt it
                    c = (char) (c + key);
                    // check if character is greater than 'Z' -> if so, subtract 26 to get the correct character
                    if (c > 'Z') {
                        c = (char) (c - 26);
                    }
                    // same logic as for uppercase, but with 'z'
                } else if (Character.isLowerCase(c)) {
                    c = (char) (c + key);
                    if (c > 'z') {
                        c = (char) (c - 26);
                    }
                }
            }
            // append the encrypted character to the StringBuilder
            encrypted.append(c);
        }
        // return the encrypted text as a String
        return encrypted.toString();
    }


    /*
    // Version 1
    private static String encrypt(String s, int key) {
        StringBuilder encrypted = new StringBuilder();

        // cast input string to lower case
        s = s.toLowerCase(Locale.ROOT);

        // Loop through each character in the string
        for (int i = 0; i < s.length(); i++) {

            // check if char is a whitespace then keep whitespace in encrypted string
            if (s.charAt(i) == ' ') {
                encrypted.append(' ');
            } else {

                // get char from current loop and add key offset ot it
                char c = (char) (s.charAt(i) + key);

                // check if char is out of bounds (=alphabet) and if so, subtract 26 to get back to valid char(s)
                if (c > (int)'z') {
                    c -= 26;
                }

                // append char to encrypted
                encrypted.append(c);
            }

        }
        return encrypted.toString();
    }

     */




}
