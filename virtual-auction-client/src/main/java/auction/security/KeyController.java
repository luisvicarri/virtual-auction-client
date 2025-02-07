package auction.security;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyController {

    private final KeyService service;

    public KeyController(KeyService service) {
        this.service = service;
    }

    public KeyPair generateKeyPair() {
        return service.generateKeyPair();
    }
    
    public PublicKey getPublicKey(String encodedPublicKey) {
        return service.getPublicKey(encodedPublicKey);
    }
    
    public PrivateKey getPrivateKey(String encodedPrivateKey) {
        return service.getPrivateKey(encodedPrivateKey);
    }
    
    public PublicKey loadKey() {
        return service.loadKey();
    }
    
    public void saveKey(String encodedKey) {
        service.saveKey(encodedKey);
    }
    
}
