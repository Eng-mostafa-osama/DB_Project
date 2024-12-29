package com.example.db_project;

public class Subscriptions {
    private int subID;
    private int memberID;
    private String planType;
    private double amount;
    private String startDate;
    private String endDate;
    public Subscriptions(int subID, int memberID, String planType, double amount, String startDate, String endDate) {
        this.subID = subID;
        this.memberID = memberID;
        this.planType = planType;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getSubID() {
        return subID;
    }

    public int getMemberID() {
        return memberID;
    }

    public String getPlanType() {
        return planType;
    }

    public double getAmount() {
        return amount;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}

