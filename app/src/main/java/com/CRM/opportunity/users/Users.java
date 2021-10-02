package com.CRM.opportunity.users;

public class Users {

    private String name;
    private String phone;
    private String email;
    private String userId;
    private String status;
    private boolean userStatus;

//    public Users() {
//
//    }


    public Users(String name, String phone, String email, String userId, String status, boolean userStatus) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.userId = userId;
        this.status = status;
        this.userStatus = userStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }
}
