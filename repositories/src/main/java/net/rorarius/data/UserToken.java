package net.rorarius.data;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usertoken")
public class UserToken {

    @Id
    private ObjectId id;
    private String token;
    private Long expires;

    public UserToken() {
    }

    public UserToken(ObjectId id, String token, Long expires) {
        this.id = id;
        this.token = token;
        this.expires = expires;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }
}
