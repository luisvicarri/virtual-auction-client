package auction.models;

import auction.main.ClientAuctionApp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.UUID;

public class User {

    private UUID id;
    private String name;
    private String hashedPassword;
    private String plainPassword;
    private String encodedPrivateKey;
    private String encodedPublicKey;

    public User() {
    }

    public User(String name, String plainPassword, String hashedPassword) {
        this.name = name;
        this.plainPassword = plainPassword;
        this.hashedPassword = hashedPassword;
        generateKeyPair();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getPlainPassword() {
        return plainPassword;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public final void generateKeyPair() {
        if (encodedPrivateKey == null || encodedPublicKey == null) {
            KeyPair keyPair = ClientAuctionApp.frame.getAppController().getKeyController().generateKeyPair();
            this.encodedPrivateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
            this.encodedPublicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

        }
    }
    
    @JsonIgnore
    public PrivateKey getPrivateKey() {
        return ClientAuctionApp.frame.getAppController().getKeyController().getPrivateKey(this.encodedPrivateKey);
    }
    
    @JsonIgnore
    public PublicKey getPublicKey() {
        return ClientAuctionApp.frame.getAppController().getKeyController().getPublicKey(encodedPublicKey);
    }

    @JsonIgnore
    public String getEncodedPrivateKey() {
        return encodedPrivateKey;
    }

    public String getEncodedPublicKey() {
        return encodedPublicKey;
    }
}