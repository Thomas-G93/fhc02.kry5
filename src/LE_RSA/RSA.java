package LE_RSA;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class RSA {
    public static void main(String[] args) {

        BigInteger p = generatePrime();
        BigInteger q = generatePrime();
        if (p.equals(q)) {
            q = generatePrime();
        }
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = generateE(phi);  // e could be set to 65537
        //e = BigInteger.valueOf(65537);
        BigInteger d = e.modInverse(phi);

        System.out.println("p:      " + p);
        System.out.println("q:      " + q);
        System.out.println("n:      " + n);
        System.out.println("phi:    " + phi);
        System.out.println("e:      " + e);
        System.out.println("d:      " + d);

        String message = "Hello World";
        System.out.println("Message: " + message);

        BigInteger[] c = encrypt(message, e, n);
        System.out.println("Encrypted: " + Arrays.toString(c));

        String decrypted = decrypt(c, d, n);
        System.out.println("Decrypted: " + decrypted);
    }


    private static String decrypt(BigInteger[] c, BigInteger d, BigInteger n) {
        StringBuilder decrypted = new StringBuilder();
        for (BigInteger bigInteger : c) {
            decrypted.append((char) bigInteger.modPow(d, n).intValue());
        }
        return decrypted.toString();
    }


    private static BigInteger[] encrypt(String message, BigInteger e, BigInteger n) {
        BigInteger[] c = new BigInteger[message.length()];
        for (int i = 0; i < message.length(); i++) {
            BigInteger m = BigInteger.valueOf(message.charAt(i));
            c[i] = m.modPow(e, n);
        }
        return c;
    }


    private static BigInteger generateE(BigInteger phi) {
        BigInteger e = new BigInteger(phi.bitLength(), new SecureRandom());
        while (e.compareTo(phi) >= 0 || e.gcd(phi).compareTo(BigInteger.ONE) > 0 || e.compareTo(BigInteger.ONE) <= 0) {
            e = new BigInteger(phi.bitLength(), new Random());
        }
        return e;
    }

    private static BigInteger generatePrime() {
        return BigInteger.probablePrime(32, new SecureRandom());
    }
}