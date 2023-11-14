package com.example.look.bean;

public class AccountOpenInfo {

    private String openNum;
    private String openName;
    private String openPhone;
    private String openBank;
    private String bankNum;
    public boolean selectState;

    public AccountOpenInfo(String openNum, String openName, String openPhone, String openBank, String bankNum) {
        this.openNum = openNum;
        this.openName = openName;
        this.openPhone = openPhone;
        this.openBank = openBank;
        this.bankNum = bankNum;
    }

    public String getOpenNum() {
        return openNum;
    }

    public void setOpenNum(String openNum) {
        this.openNum = openNum;
    }

    public String getOpenName() {
        return openName;
    }

    public void setOpenName(String openName) {
        this.openName = openName;
    }

    public String getOpenPhone() {
        return openPhone;
    }

    public void setOpenPhone(String openPhone) {
        this.openPhone = openPhone;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }
}
