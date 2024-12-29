package com.example.db_project;

import java.lang.reflect.Member;

public class Members {
    private int id;
    private String mfName;
    private String mlName;
    private String mEmail;
    private int mPhoneNumber;
    private String mSubStatus;

    // Constructor
    public Members(int id, String mfName, String mlName, String mEmail, int mPhoneNumber, String mSubStatus) {
        this.id = id;
        this.mfName = mfName;
        this.mlName = mlName;
        this.mEmail = mEmail;
        this.mPhoneNumber = mPhoneNumber;
        this.mSubStatus = mSubStatus;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMfName() {
        return mfName;
    }

    public void setMfName(String mfName) {
        this.mfName = mfName;
    }

    public String getMlName() {
        return mlName;
    }

    public void setMlName(String mlName) {
        this.mlName = mlName;
    }

    public String getMEmail() {
        return mEmail;
    }

    public void setMEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public int getMPhoneNumber() {
        return mPhoneNumber;
    }

    public void setMPhoneNumber(int mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getMSubStatus() {
        return mSubStatus;
    }

    public void setMSubStatus(String mSubStatus) {
        this.mSubStatus = mSubStatus;
    }
}

