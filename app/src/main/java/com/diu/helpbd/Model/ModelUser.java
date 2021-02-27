package com.diu.helpbd.Model;

public class ModelUser {
    private String id;
    private String username;
    private String email;
    private String contactNo;
    private String NIDno;
    private String imageURL;
    private String type;
    private String district;
    private String upazila;
    private String union;

    public ModelUser() {
    }

    public ModelUser(String id, String username, String email, String contactNo, String NIDno, String imageURL, String type, String district, String upazila, String union) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.contactNo = contactNo;
        this.NIDno = NIDno;
        this.imageURL = imageURL;
        this.type = type;
        this.district = district;
        this.upazila = upazila;
        this.union = union;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getNIDno() {
        return NIDno;
    }

    public void setNIDno(String NIDno) {
        this.NIDno = NIDno;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
