package com.CRM.opportunity.Status;

public class StatusClas {
    private int id;
    private String name;
    private String user_id;
    private boolean check_status;


    public StatusClas() {

    }

    public StatusClas(int id, String name, String user_id, boolean check_status) {
        this.id = id;
        this.name = name;
        this.user_id = user_id;
        this.check_status = check_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public boolean isCheck_status() {
        return check_status;
    }

    public void setCheck_status(boolean check_status) {
        this.check_status = check_status;
    }
}
