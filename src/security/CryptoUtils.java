// CryptoUtils.java
package security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.*;
import java.util.Base64;

public class CryptoUtils {
    // AES-GCM for authenticated encryption
    public static SecretKey generateAESKey(int bits) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(bits);
        return kg.generateKey();
    }

    public static byte[] randomIV(int sizeBytes) {
        byte[] iv = new byte[sizeBytes];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    // Encrypt plaintext with AES-GCM
    public static String aesEncrypt(String plaintext, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] ct = cipher.doFinal(plaintext.getBytes("UTF-8"));
        // prepend IV to ciphertext; Base64 both for transport
        byte[] output = new byte[iv.length + ct.length];
        System.arraycopy(iv, 0, output, 0, iv.length);
        System.arraycopy(ct, 0, output, iv.length, ct.length);
        return Base64.getEncoder().encodeToString(output);
    }

    // Decrypt AES-GCM where IV is prepended
    public static String aesDecrypt(String base64, SecretKey key) throws Exception {
        byte[] input = Base64.getDecoder().decode(base64);
        byte[] iv = new byte[12]; // GCM recommended IV size
        byte[] ct = new byte[input.length - 12];
        System.arraycopy(input, 0, iv, 0, 12);
        System.arraycopy(input, 12, ct, 0, ct.length);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] pt = cipher.doFinal(ct);
        return new String(pt, "UTF-8");
    }

    // RSA keypair for server
    public static KeyPair generateRSAKeyPair(int bits) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(bits);
        return kpg.generateKeyPair();
    }

    // Encrypt bytes (e.g., AES key) with RSA public key
    public static String rsaEncrypt(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] ct = cipher.doFinal(data);
        return Base64.getEncoder().encodeToString(ct);
    }

    // Decrypt RSA-encrypted data
    public static byte[] rsaDecrypt(String base64, PrivateKey privateKey) throws Exception {
        byte[] input = Base64.getDecoder().decode(base64);
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(input);
    }
}
