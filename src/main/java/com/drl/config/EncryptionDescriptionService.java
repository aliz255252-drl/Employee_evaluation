package com.drl.config;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class EncryptionDescriptionService {
    private static final String ALGORITHM = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
    private static final String publicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArsYlhtsrLbgHS///uAuxxKOqlnd7jCbtDFrbO7L54XfjOfHmLoFa2CY8F0b7szrdbjCQ4YlYWMhYP1QOH36fRu/28OvceHs4MHYPE68LwwvqMxIByiHUib/TCbat04JHqQBsuPWyeryGsNVAVtbs4A9cUfBOnkSPKHs9NGtuWGXsMiG17mW6DvZ4S0MsfxLASKJDAS1xHvsHgfzmXbDUkOvbeJ6dQP4enqJ2yD9pLNb3ioPKqUKIk7GMgIgNP+9deIl3Eu3BYZvMww747/1AaWER+agPqAk36XJyMoO0FwUr2ccAJFfs/8FmscunfHdueOdN0glncdW0axyDrCa2TQIDAQAB";
    private static final String privateKey="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCuxiWG2ystuAdL//+4C7HEo6qWd3uMJu0MWts7svnhd+M58eYugVrYJjwXRvuzOt1uMJDhiVhYyFg/VA4ffp9G7/bw69x4ezgwdg8TrwvDC+ozEgHKIdSJv9MJtq3TgkepAGy49bJ6vIaw1UBW1uzgD1xR8E6eRI8oez00a25YZewyIbXuZboO9nhLQyx/EsBIokMBLXEe+weB/OZdsNSQ69t4np1A/h6eonbIP2ks1veKg8qpQoiTsYyAiA0/7114iXcS7cFhm8zDDvjv/UBpYRH5qA+oCTfpcnIyg7QXBSvZxwAkV+z/wWaxy6d8d254503SCWdx1bRrHIOsJrZNAgMBAAECggEADuu1qnStlAKhBNj/INxGue3rE1tx/b4ZALQFAWy/LoOIr5q6NwAuHEPvX2Fc/akp3E1jIQ4Tr8iy1/06E3BA8tJrt5e7Q8HpQyHhGaIZE/bBcMi5xGLs/TGWN78TyfnWWlRB8+xt51KPASvDiWZh5KMLE6e7XoGl4yPaTs5ZJkyxDExvih2opft7YTdg7BLQIOuIsCTphPJpINoTXNyshUeCQ3IMOfzO7wWHwNTcuSCmbiyVd506N/Q9UgMBkKQjtu+doo0ZxKoLk81eLUuf5+MRjRZLrFcn1D91eaUR4nED+VDuxEPo0PtGwtXNp9oEGOJ4Q0oS1Q/D7EtTQ3w6tQKBgQDkFsB+oKZv6SKD4l/eYs05ujO+axVeqcruUO+l58LWkZznyvY4JoyJpvkm8dqSD3X2Xjz+7znUTRlI9/pgdSQSEJIw2eyBnAlAIw3hrvE6pBPx69BkgGXLMdIVEYLM4OEi9IQ5Fy8/yxON014GYZxN9EIbEr//i3OINy95D1+oYwKBgQDEKTivM+LxlQ1/hOavRg7ifZv4f7E63bIhjYN5J3U6E3B3LkLhGR180QXWzG/XhutARv80lqIssepWAcojCiH7+MQMVbSYOipDJjTi+KvhvO3jg02LdpLmOXdhPfzC0BDMcBYSLBqr+bJMEz/lwN7hnAAPqf7GDs2+Y0VqNBztjwKBgGvmY3vDzdilJkHTplySytkjP/U1vS9CDZD90tDeOaq9ycdRmXborZU02yrUQPzVuY0p+Zr0WpwXOP3u6ZAV+lFda3sWrK6HvUyNCLUZyF5gXlsUnknc/8rHL8Xtub3oKDkcCSzkzeJU1FREiHdmV8X85gTesttWAfqPeLLBVhRXAoGBAImsFKiCVgkPf4W8FNt25AN5/PmPRSf3aIm79EYXt2KnOgurYCzzn4p2eQ6kNOqjOoDGU17pSbDsvXbXkrK87dUWZyHxFOqyB+9Q5VVXCetI2f0PrmDvO3JxaqPJCZkAxol1MUhXw/BnMwfJitMJZmQZRMG5oaIHycaRjapYIOVpAoGBAMD+tJznSn/Zs6ic5uVjU4e6BrTksddKcUZKhKUOjL11UitZiOJhwdPz61iWG4UojM0UgaDOb+12C8J7SruaYpk9vqk/YQ2Zxjs+2ePV9m2PsB7z1leF1k6db4WK/k/9voP8fCDBRAssa0po3jsZIWGa5BufdeMsvRkirXV7wRA0";


    public static String encrypt(String message) throws Exception {
        // Base64 decode the public key
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // Encrypt the message and return as Base64
        byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedMessage) {
        // Base64 decode the private key
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // Decrypt the message
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
    public static java.security.KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
}
