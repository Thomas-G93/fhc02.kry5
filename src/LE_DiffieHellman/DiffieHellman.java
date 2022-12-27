package LE_DiffieHellman;

import java.math.BigInteger;
import java.util.Random;

public class DiffieHellman {

    public static void main(String[] args) {
        System.out.println("Diffie-Hellman!");

        // Alice und Bob vereinbaren einen gemeinsamen
        // Schlüssel mit Diffie-Hellman mit p=17 und g=3.
        // Wählen Sie zwei unterschiedliche Zufallszahlen
        // a, b größer als 2 aus und berechnen Sie den
        // gemeinsamen vereinbarten Schlüssel S.

        BigInteger p = BigInteger.valueOf(17);
        System.out.println("p: " + p + " (Primzahl)");
        BigInteger g = BigInteger.valueOf(3);
        System.out.println("g: " + g + " (Primwurzel von p)");

        BigInteger a;
        do {
            a = new BigInteger(8, new Random());
        } while (a.intValue() < 2);
        System.out.println("a: " + a + " (Alice - Private KEY)");

        BigInteger b;
        do {
            b = new BigInteger(8, new Random());
        } while (b.intValue() < 2);
        System.out.println("b: " + b + " (Bob - Private KEY)");


        BigInteger A = g.modPow(a, p);
        System.out.println("A: " + A + " (Alice - Public KEY)");
        BigInteger B = g.modPow(b, p);
        System.out.println("B: " + B + " (Bob - Public KEY)");

        BigInteger S1 = B.modPow(a, p);
        System.out.println("S1: " + S1 + " (Alice - Shared KEY)");
        BigInteger S2 = A.modPow(b, p);
        System.out.println("S2: " + S2 + " (Bob - Shared KEY)");


        if (S1.equals(S2)) {
            System.out.println("S1 == S2");
        } else {
            System.out.println("S1 != S2");
        }
    }


}
