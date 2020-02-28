package com.example.android.contact;

public class user
{
    private String phone,password;

    public user()
    {

    }
    public user(String pno,String pwd)
    {
        phone=pno;
        password=pwd;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
