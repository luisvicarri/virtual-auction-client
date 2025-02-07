package auction.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyService {

    private static final Logger logger = LoggerFactory.getLogger(KeyService.class);
    private final KeyRepository repository;

    public KeyService(KeyRepository repository) {
        this.repository = repository;
    }
    
    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(4096);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            logger.error("Failed to generate RSA key pair: Algorithm not found", ex);
        }
        return null;
    }

    public PublicKey getPublicKey(String encodedPublicKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(encodedPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
        } catch (IllegalArgumentException ex) {
            logger.error("Failed to decode public key: Invalid Base64 encoding", ex);
        } catch (NoSuchAlgorithmException ex) {
            logger.error("Failed to get public key: RSA algorithm not found", ex);
        } catch (InvalidKeySpecException ex) {
            logger.error("Failed to generate public key: Invalid key specification", ex);
        }
        return null;
    }

    public PrivateKey getPrivateKey(String encodedPrivateKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(encodedPrivateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
        } catch (IllegalArgumentException ex) {
            logger.error("Failed to decode private key: Invalid Base64 encoding", ex);
        } catch (NoSuchAlgorithmException ex) {
            logger.error("Failed to get private key: RSA algorithm not found", ex);
        } catch (InvalidKeySpecException ex) {
            logger.error("Failed to generate private key: Invalid key specification", ex);
        }
        return null;
    }
    
    public PublicKey loadKey() {
        return repository.loadServerPublicKey();
    }
    
    public void saveKey(String encodedKey) {
        repository.saveServerPublicKey(encodedKey);
    }
    
    public PublicKey getServerPublicKey() {
        return repository.getServerPublicKey();
    }
}