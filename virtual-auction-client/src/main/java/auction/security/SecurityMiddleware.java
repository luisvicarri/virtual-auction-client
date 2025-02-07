package auction.security;

import auction.main.ClientAuctionApp;
import auction.models.User;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityMiddleware {
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityMiddleware.class);
    private final DigitalSignatureUtil dSignatureUtil;
    
    public SecurityMiddleware() {
        this.dSignatureUtil = new DigitalSignatureUtil();
    }

    /**
     * (en-US) Signs data using the user's private key.
     * (pt-BR) Assina os dados usando a chave privada do usuário.
     */
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

    /**
     * (en-US) Verifies the signature of an incoming request using the stored public key.
     * (pt-BR) Verifica a assinatura de uma solicitação recebida usando a chave pública armazenada.
     */
    public boolean verifyRequest(String data, String signature, UUID userId) {
        logger.info("Verifying request signature for user ID: {}", userId);

        Optional<User> userOptional = ClientAuctionApp.frame.getAppController().getUserController().findById(userId);
        if (userOptional.isEmpty()) {
            logger.warn("User not found for ID: {}. Signature verification failed.", userId);
            return false;
        }

        PublicKey publicKey = userOptional.get().getPublicKey();
        if (publicKey == null) {
            logger.error("Public key not found for user ID: {}. Cannot verify signature.", userId);
            return false;
        }

        boolean isValid = dSignatureUtil.verifySignature(data, signature, publicKey);
        if (isValid) {
            logger.info("Signature verification successful for user ID: {}", userId);
        } else {
            logger.warn("Signature verification failed for user ID: {}", userId);
        }
        return isValid;
    }
}