package tw.com.younite.entity;

public class LoginResponse {
    private UserEntity user;
    private String token;

    public LoginResponse(UserEntity user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
