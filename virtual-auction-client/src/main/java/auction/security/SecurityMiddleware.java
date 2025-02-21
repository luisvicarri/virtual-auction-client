package auction.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityMiddleware {
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityMiddleware.class);
    private final DigitalSignatureUtil dSignatureUtil;
    private final AsymmetricUtil asymmetricUtil;

    public SecurityMiddleware() {
        this.dSignatureUtil = new DigitalSignatureUtil();
        this.asymmetricUtil = new AsymmetricUtil();
    }

    public String signRequest(String data, PrivateKey privateKey) {
        logger.info("Signing request data...");
        String signedData = dSignatureUtil.signData(data, privateKey);
        
        if (signedData != null) {
            logger.info("Request successfully signed.");
        } else {
            logger.error("Failed to sign request.");
        }
        return signedData;
    }

    public boolean verifyRequest(String data, String signature, PublicKey publicKey) {
        logger.info("Verifying request signature with server public key");
        boolean isValid = dSignatureUtil.verifySignature(data, signature, publicKey);

        if (isValid) {
            logger.info("Signature verification successful with server public key");
        } else {
            logger.warn("Signature verification failed with server public key");
        }
        return isValid;
    }
    
    public String encryptMessage(String message, PublicKey publicKey) {
        logger.info("Encrypting message for secure transmission...");
        return asymmetricUtil.encrypt(message, publicKey);
    }

    public String decryptMessage(String encryptedMessage, PrivateKey privateKey) {
        logger.info("Decrypting received message...");
        return asymmetricUtil.decrypt(encryptedMessage, privateKey);
    }
}