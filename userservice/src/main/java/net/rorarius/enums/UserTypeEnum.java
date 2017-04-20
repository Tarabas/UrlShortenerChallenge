package net.rorarius.enums;

public enum UserTypeEnum {
    USER(1, "ROLE_USER"), ADMIN(2, "ROLE_ADMIN");

    private int type;
    private String roleName;

    UserTypeEnum(int type, String roleName) {
        this.type = type;
        this.roleName = roleName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
