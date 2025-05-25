package ma.zyn.app.zynerator.transverse.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Base64;
@Service
public class FileEncryptionUtil {
@Value("${encryption.key}")
    private String secretKey;
    public void encryptFile(String filePath) throws Exception {
        // Read file content
        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
        // Create key
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        Key key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        // Initialize Cipher for encryption
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // Encrypt file content
        byte[] encryptedContent = cipher.doFinal(fileContent);
        // Write encrypted content back to file
        Files.write(Paths.get(filePath), encryptedContent);
    }
    public void decryptFile(String restorePath) throws Exception {
        // Read encrypted file content
        byte[] encryptedContent = Files.readAllBytes(Paths.get(restorePath));
        // Create key
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        Key key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        // Initialize Cipher for decryption
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        // Decrypt file content
        byte[] decryptedContent = cipher.doFinal(encryptedContent);
        // Write decrypted content back to file
        Files.write(Paths.get(restorePath), decryptedContent);
    }
}
