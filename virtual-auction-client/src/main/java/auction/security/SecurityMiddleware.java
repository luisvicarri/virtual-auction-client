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
    
    /**
     * (en-US) Encrypts a JSON message using the receiver's public key.
     * (pt-BR) Criptografa uma mensagem JSON usando a chave pública do destinatário.
     */
    public String encryptMessage(String message, PublicKey publicKey) {
        logger.info("Encrypting message for secure transmission...");
        return asymmetricUtil.encrypt(message, publicKey);
    }

    /**
     * (en-US) Decrypts a received message using the user's private key.
     * (pt-BR) Descriptografa uma mensagem recebida usando a chave privada do usuário.
     */
    public String decryptMessage(String encryptedMessage, PrivateKey privateKey) {
        logger.info("Decrypting received message...");
        return asymmetricUtil.decrypt(encryptedMessage, privateKey);
    }
}