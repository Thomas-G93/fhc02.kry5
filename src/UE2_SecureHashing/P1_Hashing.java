package UE2_SecureHashing;

import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

/*
Implementieren Sie ein Programm, welches Benutzereingaben verarbeitet, um Passwörter einzulesen und
anschließend das eingegebene Passwort mit verschiedenen Algorithmen hasht und alle relevanten Informationen
auf der Konsole ausgibt.

Folgende Hashvarianten müssen Implementiert werden: MD5 SHA256 SHA256 + SALT BCRYPT
+ Ausarbeitung BCRYPT Algorithmus Erklärung (Wie funktioniert BCRYPT?) –
Beschreibung als Kommentar hinzufügen. (Kein Copy & Paste)
*/
public class P1_Hashing {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {

        // Input of password
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please type in password: ");
        String password = scanner.nextLine();

        // Hash the password using various algorithms
        String md5Hash = hashPassword(password, "MD5");
        String sha256Hash = hashPassword(password, "SHA-256");

        // Create salt and hash the password with salt
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        String sha256SaltHash = hashPassword(password, "SHA-256", salt);

        // Create Bcrypt hash of password
        String bcryptHash = hashPasswordBCrypt(password);
        String bcryptHashMindrot = hashPasswordBCryptMindrot(password);

        // Output hash values
        System.out.println("MD5 Hash:               " + md5Hash);
        System.out.println("SHA-256 Hash:           " + sha256Hash);
        System.out.println("SHA-256 Hash with Salt: " + sha256SaltHash);
        System.out.println("BCRYPT Hash:            " + bcryptHash);
        System.out.println("BCRYPT Hash Mindrot:    " + bcryptHashMindrot);
    }

    private static String hashPassword(String password, String algorithm) throws NoSuchAlgorithmException {
        // Create hash function
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

        // Hash the password
        byte[] hash = messageDigest.digest(password.getBytes());

        return toHexString(hash);
    }

    private static String hashPassword(String password, String algorithm, byte[] salt) throws NoSuchAlgorithmException {
        // Create hash function
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

        // combine password and salt
        messageDigest.update(salt);

        // Hash the password
        byte[] hash = messageDigest.digest(password.getBytes());

        return toHexString(hash);
    }


    /*
    BCrypt is a password hashing algorithm that was developed to increase the difficulty of cracking hashed passwords, even with the use of powerful hardware.
    It uses the Blowfish symmetric block cipher in combination with a key derivation function to generate a secure hash of a password.

    1. The user provides a password, which is hashed using the Blowfish cipher.
    2. A salt value is generated randomly and is used to modify the password before it is hashed. The salt value is also stored along with the hashed password.
    3. The Blowfish cipher uses a key schedule to generate subkeys from the password and salt. These subkeys are used to encrypt the password hash.
    4. The encrypted password hash, along with the salt value and other information about the hashing process, is stored in a string called the "hashed password".

    When a user attempts to log in with their password, the password they provide is hashed using the same process as before (including the same salt value).
    The resulting hashed password is then compared to the stored hashed password. If they match, the user is authenticated.

    One of the key features of bcrypt is that it is designed to be slow to compute, making it more resistant to brute-force attacks.
    This is achieved by using a configurable "work factor", which controls the number of iterations of the key schedule that are performed.
    The higher the work factor, the more computationally expensive it is to generate the hashed password, but the more secure it is.

    */

    private static String hashPasswordBCrypt(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Generate a random salt value
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        System.out.println("BCrypt - Salt: " + toHexString(salt));

        // Hash the password with salt
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();

        // Convert the salt and hash to hexadecimal notation
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(toHexString(salt));
        stringBuilder.append(toHexString(hash));
        return stringBuilder.toString();
    }

    private static String hashPasswordBCryptMindrot(String password) {

        // https://mvnrepository.com/artifact/org.mindrot/jbcrypt/0.4

        // Generate a random salt value
        /*
        The BCrypt.gensalt() method is used to generate a random salt value.
        The salt is a random string of characters that is used to add an extra layer of security to the password hash.
        */
        String salt = BCrypt.gensalt();
        System.out.println("BCryptMindrot - Salt: " + toHexString(salt.getBytes()));

        // Hash the password with the salt
        /*
        The BCrypt.hashpw() method is used to compute the BCRYPT hash of the password, using the salt value.
        The BCRYPT algorithm is designed to be slow and resource-intensive,
        making it difficult for attackers to crack the password by guessing or using precomputed hash tables.
        */
        String bcryptHash = BCrypt.hashpw(password, salt);

        /*
        The resulting BCRYPT hash is a string of characters that represents the hashed password.
        This hash can be stored in a database.
        */

        return toHexString(bcryptHash.getBytes());


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
