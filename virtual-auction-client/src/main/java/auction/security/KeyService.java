package auction.security;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyService {

    private static final Logger logger = LoggerFactory.getLogger(KeyService.class);
    private final KeyRepository repository;
    private final AsymmetricUtil asymmetricUtil;
    private final SymmetricUtil symmetricUtil;

    public KeyService(KeyRepository repository) {
        this.repository = repository;
        this.asymmetricUtil = new AsymmetricUtil();
        this.symmetricUtil = new SymmetricUtil();
    }

    public KeyPair generateAsymmetricKeys() {
        return asymmetricUtil.generateRSAKeyPair();
    }

    public PublicKey getPublicKey(String encodedPublicKey) {
        try {
            return asymmetricUtil.decodePublicKey(encodedPublicKey);
        } catch (Exception ex) {
            logger.error("Failed to decode public key", ex);
            return null;
        }
    }

    public PrivateKey getPrivateKey(String encodedPrivateKey) {
        try {
            return asymmetricUtil.decodePrivateKey(encodedPrivateKey);
        } catch (Exception ex) {
            logger.error("Failed to decode public key", ex);
            return null;
        }
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

    public SecretKey loadSymmetricKey() {
        return repository.loadSymmetricKey();
    }

    public void saveSymmetrickey(byte[] encodedKey) {
        repository.saveSymmetricKey(encodedKey);
    }

    public SecretKey getSymmetricKey() {
        return repository.getSymmetricKey();
    }

    public IvParameterSpec loadIV() {
        return repository.loadIV();
    }

    public void saveIV(byte[] ivBytes) {
        repository.saveIV(ivBytes);
    }
    
    public IvParameterSpec getIv() {
        return repository.getIv();
    }
}