package auction.security;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class KeyController {

    private final KeyService service;

    public KeyController(KeyService service) {
        this.service = service;
    }

    public KeyPair generateKeyPair() {
        return service.generateAsymmetricKeys();
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
    
    public PublicKey getServerPublicKey() {
        return service.getServerPublicKey();
    }
    
    public SecretKey loadSymmetricKey() {
        return service.loadSymmetricKey();
    }
    
    public void saveSymmetrickey(byte[] encodedKey) {
        service.saveSymmetrickey(encodedKey);
    }
    
    public SecretKey getSymmetricKey() {
        return service.getSymmetricKey();
    }
    
    public IvParameterSpec loadIV() {
        return service.loadIV();
    }
    
    public void saveIV(byte[] ivBytes) {
        service.saveIV(ivBytes);
    }
    
    public IvParameterSpec getIv() {
        return service.getIv();
    }
}