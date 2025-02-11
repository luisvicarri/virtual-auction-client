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
     * Verifica a assinatura de uma requisição utilizando a chave pública do
     * servidor.
     */
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
}