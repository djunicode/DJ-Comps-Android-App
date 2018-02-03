package io.github.djunicode.djcomps;

/**
 * Created by Admin on 04-02-2018.
 */

public class Documents {
    private String name;
    private String subject;
    private String access;
    private int img;
    private String creator;
    private int downloadNo;
    private int starredNo;
    private boolean isStarred;
    private boolean isDownloaded;

    public Documents() {
    }

    public Documents(String name,String subject, String access, int img, String creator, boolean isStarred, boolean isDownloaded) {
        this.name=name;
        this.subject=subject;
        this.access=access;
        this.img=img;
        this.creator=creator;
        this.isStarred=isStarred;
        this.isDownloaded=isDownloaded;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getAccess() {
        return access;
    }

    public int getImg() {
        return img;
    }

    public String getCreator() {
        return creator;
    }

    public boolean getIsStarred() {
        return isStarred;
    }

    public boolean getIsDownloaded() {
        return isDownloaded;
    }


    public void setName(String name){
        this.name=name;
    }

    public void setSubject(String subject){
        this.subject=subject;
    }

    public void setAccess(String access){
        this.access=access;
    }

    public void setImg(int img){
        this.img=img;
    }

    public void setCreator(String creator){
        this.creator=creator;
    }

    public void setIsStarred(boolean isStarred){
        this.isStarred=isStarred;
    }

    public void setIsDownloaded(boolean isDownloaded){
        this.isDownloaded=isDownloaded;
    }



}
