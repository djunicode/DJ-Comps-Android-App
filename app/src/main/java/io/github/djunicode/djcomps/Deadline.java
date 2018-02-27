package io.github.djunicode.djcomps;

/**
 * Created by Admin on 04-02-2018.
 */

public class Deadline {

    private String name;
    private String subject;
    private String date;
    private int img;

    public Deadline() {
    }

    public Deadline(String name,String subject, String date, int img) {
        this.name=name;
        this.subject=subject;
        this.date=date;
        this.img=img;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }

    public int getImg() {
        return img;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setSubject(String subject){
        this.subject=subject;
    }

    public void setDate(String date){
        this.date=date;
    }

    public void setImg(int img){
        this.img=img;
    }






}
