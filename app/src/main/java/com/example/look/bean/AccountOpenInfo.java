package com.example.look.bean;

public class AccountOpenInfo {

    private String openNum;
    private String openName;
    private String openPhone;
    private String openBank;
    public boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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

    public AccountOpenInfo(String openNum, String openName, String openPhone, String openBank) {
        this.openNum = openNum;
        this.openName = openName;
        this.openPhone = openPhone;
        this.openBank = openBank;
    }

    public AccountOpenInfo() {
    }
}
