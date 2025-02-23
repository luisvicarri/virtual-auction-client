package auction.security;

import auction.utils.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyRepository {

    private static final Logger logger = LoggerFactory.getLogger(KeyRepository.class);
    private final File file = new File("repositories/security/server_key.json");
    private final ObjectMapper mapper = JsonUtil.getObjectMapper();
    private PublicKey serverPublicKey;
    private SecretKey symmetricKey;
    private IvParameterSpec iv;

    public KeyRepository() {
        this.serverPublicKey = loadServerPublicKey();
        if (this.serverPublicKey == null) {
            logger.warn("No server public key found. You will need to define a new key.");
        } else {
            logger.info("Server public key loaded successfully.");
        }
    }

    public PublicKey getServerPublicKey() {
        return serverPublicKey;
    }

    public void setServerPublicKey(PublicKey serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }

    public SecretKey getSymmetricKey() {
        return symmetricKey;
    }

    public void setSymmetricKey(SecretKey symmetricKey) {
        this.symmetricKey = symmetricKey;
    }

    public IvParameterSpec getIv() {
        return iv;
    }

    public void setIv(IvParameterSpec iv) {
        this.iv = iv;
    }

    public void saveServerPublicKey(String encodedServerPublicKey) {
        try {
            if (file.exists()) {
                Map<String, String> existingData = mapper.readValue(file, Map.class);
                String existingKey = existingData.get("publicKey");

                if (encodedServerPublicKey.equals(existingKey)) {
                    logger.info("Server's public key is already saved. No action required.");
                    return;
                }
            }

            Map<String, String> keyMap = file.exists() ? mapper.readValue(file, Map.class) : new HashMap<>();
            keyMap.put("publicKey", encodedServerPublicKey);

            mapper.writerWithDefaultPrettyPrinter().writeValue(file, keyMap);
            logger.info("Server's public key saved successfully.");

            byte[] publicKeyBytes = Base64.getDecoder().decode(encodedServerPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.serverPublicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        } catch (IOException ex) {
            logger.error("Error saving server public key: {}", ex.getMessage(), ex);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            logger.error("Error processing server public key: {}", ex.getMessage(), ex);
        }
    }

    public final PublicKey loadServerPublicKey() {
        if (!file.exists()) {
            logger.warn("Server public key file does not exist.");
            return null;
        }

        try {
            Map<String, String> keyMap = mapper.readValue(file, Map.class);
            String encodedPublicKey = keyMap.get("publicKey");

            if (encodedPublicKey == null || encodedPublicKey.isEmpty()) {
                logger.warn("Server public key file is empty.");
                return null;
            }

            byte[] publicKeyBytes = Base64.getDecoder().decode(encodedPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

        } catch (IOException ex) {
            logger.error("Error loading server public key: {}", ex.getMessage(), ex);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            logger.error("Error processing server public key: {}", ex.getMessage(), ex);
        }
        return null;
    }

    public void saveSymmetricKey(byte[] encodedSymmetricKey) {
        try {
            String encodedKeyBase64 = Base64.getEncoder().encodeToString(encodedSymmetricKey);

            if (file.exists()) {
                Map<String, String> existingData = mapper.readValue(file, Map.class);
                String existingKey = existingData.get("symmetricKey");

                if (encodedKeyBase64.equals(existingKey)) {
                    logger.info("Server's symmetric key is already saved. No action required.");
                    return;
                }
            }

            Map<String, String> keyMap = file.exists() ? mapper.readValue(file, Map.class) : new HashMap<>();
            keyMap.put("symmetricKey", encodedKeyBase64);

            mapper.writerWithDefaultPrettyPrinter().writeValue(file, keyMap);
            logger.info("Server's symmetric key saved successfully.");

            byte[] decodedKey = Base64.getDecoder().decode(encodedKeyBase64);
            this.symmetricKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        } catch (IOException ex) {
            logger.error("Error saving server symmetric key: {}", ex.getMessage(), ex);
        }
    }

    public SecretKey loadSymmetricKey() {
        if (!file.exists()) {
            logger.warn("Server symmetric key file does not exist.");
            return null;
        }

        try {
            Map<String, String> keyMap = mapper.readValue(file, Map.class);
            String encodedKeyBase64 = keyMap.get("symmetricKey");

            if (encodedKeyBase64 == null || encodedKeyBase64.isEmpty()) {
                logger.warn("Server symmetric key file is empty.");
                return null;
            }

            byte[] decodedKey = Base64.getDecoder().decode(encodedKeyBase64);
            return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        } catch (IOException ex) {
            logger.error("Error loading server symmetric key: {}", ex.getMessage(), ex);
        }
        return null;
    }
    
    public void saveIV(byte[] ivBytes) {
        try {
            String encodedIVBase64 = Base64.getEncoder().encodeToString(ivBytes);
            Map<String, String> keyMap = file.exists() ? mapper.readValue(file, Map.class) : new HashMap<>();
            keyMap.put("iv", encodedIVBase64);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, keyMap);
            logger.info("Server's IV saved successfully.");
            this.iv = new IvParameterSpec(ivBytes);
        } catch (IOException ex) {
            logger.error("Error saving IV: {}", ex.getMessage(), ex);
        }
    }

    public IvParameterSpec loadIV() {
        if (!file.exists()) {
            logger.warn("Server IV's file does not exist.");
            return null;
        }
        try {
            Map<String, String> keyMap = mapper.readValue(file, Map.class);
            String encodedIVBase64 = keyMap.get("iv");
            if (encodedIVBase64 == null || encodedIVBase64.isEmpty()) return null;
            byte[] decodedIV = Base64.getDecoder().decode(encodedIVBase64);
            return new IvParameterSpec(decodedIV);
        } catch (IOException ex) {
            logger.error("Error loading IV: {}", ex.getMessage(), ex);
        }
        return null;
    }
}