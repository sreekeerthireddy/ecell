package com.example.android.contact;

public class download
{
    public String name;
    public String imageurl;
    public String designation;
    public String linkedinlink;
    public String register;
    public download()
    {

    }

    public download(String name,String u,String des,String l,String r)
    {
        this.name=name;
        imageurl=u;
        designation=des;
        linkedinlink=l;
        register=r;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public String getImage_url() {
        return imageurl;
    }
    public String getll()
    {
        return linkedinlink;

    }
    public void setll(String l)
    {
        linkedinlink=l;
    }
    public String getregister()
    {
        return register;
    }
    public void setRegister(String r)
    {
        register=r;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setImage_url(String image_url) {
        imageurl = image_url;
    }
}
