package com.example.android.contact;

public class downloadevent
{
    public String name;
    public String imageurl;
    public String description;

    public downloadevent()
    {

    }

    public downloadevent(String name,String u,String des)
    {
        this.name=name;
        imageurl=u;
        description=des;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return imageurl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesignation(String designation) {
        this.description = designation;
    }

    public void setImage_url(String image_url) {
        imageurl = image_url;
    }
}
