package com.diu.helpbd.Model;

public class ModelApplication {

    private String userId;
    private String name;
    private String fatherName;
    private String motherName;
    private String birthDate;
    private String nidNo;
    private String account;
    private String balance;
    private String district;
    private String upazila;
    private String union;
    private String status;
    private String gotFund;
    private String id;

    public ModelApplication() {
    }

    public ModelApplication(String userId, String name, String fatherName, String motherName, String birthDate, String nidNo, String account, String balance, String district, String upazila, String union, String status, String gotFund, String id) {
        this.userId = userId;
        this.name = name;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.birthDate = birthDate;
        this.nidNo = nidNo;
        this.account = account;
        this.balance = balance;
        this.district = district;
        this.upazila = upazila;
        this.union = union;
        this.status = status;
        this.gotFund = gotFund;
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNidNo() {
        return nidNo;
    }

    public void setNidNo(String nidNo) {
        this.nidNo = nidNo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getUpazila() {
        return upazila;
    }

    public void setUpazila(String upazila) {
        this.upazila = upazila;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGotFund() {
        return gotFund;
    }

    public void setGotFund(String gotFund) {
        this.gotFund = gotFund;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
