package com.CRM.opportunity.Action;

public class ActionsClass {
    private int idActions;
    private String id_status_actions;
    private int IdOpporunite;
    private String nameOp;
    String Date;
    private String user_id;


    public ActionsClass() {

    }

    public ActionsClass(int idActions, String id_status_actions, int idOpporunite, String nameOp, String date, String user_id) {
        this.idActions = idActions;
        this.id_status_actions = id_status_actions;
        IdOpporunite = idOpporunite;
        this.nameOp = nameOp;
        Date = date;
        this.user_id = user_id;
    }

    public int getIdActions() {
        return idActions;
    }

    public void setIdActions(int idActions) {
        this.idActions = idActions;
    }

    public String getId_status_actions() {
        return id_status_actions;
    }

    public void setId_status_actions(String id_status_actions) {
        this.id_status_actions = id_status_actions;
    }

    public int getIdOpporunite() {
        return IdOpporunite;
    }

    public void setIdOpporunite(int idOpporunite) {
        IdOpporunite = idOpporunite;
    }

    public String getNameOp() {
        return nameOp;
    }

    public void setNameOp(String nameOp) {
        this.nameOp = nameOp;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
