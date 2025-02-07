package auction.models.dtos;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Request {

//    private String action;
//    private String name;
//    private String password;
//    private Optional<UUID> id;
//    private Optional<String> encodedPublicKey;
//    private Map<String, Object> data;
//
//    public Request() {
//    }
//
//    public Request(String action, String name, String password, Optional<UUID> id, Optional<String> encodedPublicKey) {
//        this.action = action;
//        this.name = name;
//        this.password = password;
//        this.id = id != null ? id : Optional.empty();
//        this.encodedPublicKey = encodedPublicKey != null ? encodedPublicKey : Optional.empty();
//    }
//    
//    public String getAction() {
//        return action;
//    }
//
//    public void setAction(String action) {
//        this.action = action;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Optional<UUID> getId() {
//        return id;
//    }
//
//    public void setId(Optional<UUID> id) {
//        this.id = id;
//    }
//
//    public Optional<String> getEncodedPublicKey() {
//        return encodedPublicKey;
//    }
//
//    public void setEncodedPublicKey(Optional<String> encodedPublicKey) {
//        this.encodedPublicKey = encodedPublicKey;
//    }
    private String status;
    private String message;
    private Map<String, Object> data;

    public Request() {
        this(null, null, null);
    }

    public Request(String status, String message) {
        this(status, message, null);
    }

    public Request(String status, String message, Map<String, Object> data) {
        this.status = status;
        this.message = message;
        this.data = (data == null || data.isEmpty()) ? null : data; // Evita objetos vazios
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Retorna um Optional para indicar que "data" pode estar ausente
    public Optional<Map<String, Object>> getData() {
        return Optional.ofNullable(data);
    }

    public void setData(Map<String, Object> data) {
        this.data = (data == null || data.isEmpty()) ? null : data;
    }

    public void addData(String key, Object value) {
        if (this.data == null) {
            this.data = new HashMap<>();
        }
        this.data.put(key, value);
    }
}
