package com.fiek.aes;

import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class CypherUtils {

    private final static String ALGORITHM = "AES/ECB/PKCS5Padding";
    private final static String HEX = "0123456789ABCDEF";

    private CypherUtils(){}

    private static String cipherText(String secretKey, String text) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), secretKey.getBytes(), 128, 256);
        SecretKey secret = factory.generateSecret(spec);
        SecretKey key = new SecretKeySpec(secret.getEncoded(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return convertToHex(cipher.doFinal(text.getBytes()));
    }

    private static String decipherText(String secretKey, String text) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), secretKey.getBytes(), 128, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey key = new SecretKeySpec(tmp.getEncoded(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(convertToByte(text)));
    }

    private static byte[] convertToByte(String hexString) {
        int length = hexString.length() / 2;
        byte[] result = new byte[length];

        for (int i = 0; i < length; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        return result;
    }

    private static String convertToHex(byte[] stringBytes) {
        StringBuffer encryptedText = new StringBuffer(2 * stringBytes.length);
        for (int i = 0; i < stringBytes.length; i++) {
            encryptedText.append(HEX.charAt((stringBytes[i] >> 4) & 0x0f)).append(HEX.charAt(stringBytes[i] & 0x0f));
        }
        return encryptedText.toString();
    }

    public static String encryptText(String secretKey, String password, View view) {
        Snackbar snackbar = Snackbar.make(view, "Ka ndodhur një problem gjatë enkriptimit.", Snackbar.LENGTH_LONG);
        try {
            return cipherText(secretKey, password);
        } catch (Exception e) {
            snackbar.show();
            Log.w("wCypherUtils.encrypt", "Error while encrypting text.", e);
            return null;
        }
    }

    public static String decryptText(String secretKey, String encryptedPassword, View view) {
        Snackbar snackbar = Snackbar.make(view, "Ka ndodhur një problem gjatë dekriptimit.", Snackbar.LENGTH_LONG);
        try {
            return decipherText(secretKey, encryptedPassword);
        } catch (Exception e) {
            snackbar.show();
            Log.w("wCypherUtils.decrypt", "Error while decrypting text.", e);
            return null;
        }
    }



}
