package com.example.turtle.project_achoo.function.model.member;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberDTO {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("pw")
    @Expose
    private String pw;
    @SerializedName("nick")
    @Expose
    private String nick;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("selfT")
    @Expose
    private String selfT;
    @SerializedName("detailT")
    @Expose
    private Object detailT;

    @SerializedName("priceRange")
    @Expose
    private Object priceRange;

    @SerializedName("colorRange")
    @Expose
    private Object colorRange;

    public MemberDTO() {
    }

    public MemberDTO(String id,String pw,String nick,String email){
        this.id = id;
        this.pw = pw;
        this.nick = nick;
        this.email = email;

    }
    public MemberDTO(String id, String pw, String nick, String email, String selfT, Object detailT, Object priceRange, Object colorRange) {
        this.id = id;
        this.pw = pw;
        this.nick = nick;
        this.email = email;
        this.selfT = selfT;
        this.detailT = detailT;
        this.priceRange = priceRange;
        this.colorRange = colorRange;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSelfT() {
        return selfT;
    }

    public void setSelfT(String selfT) {
        this.selfT = selfT;
    }

    public Object getDetailT() {
        return detailT;
    }

    public void setDetailT(Object detailT) {
        this.detailT = detailT;
    }

    public Object getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(Object priceRange) {
        this.priceRange = priceRange;
    }

    public Object getColorRange() {
        return colorRange;
    }

    public void setColorRange(Object colorRange) {
        this.colorRange = colorRange;
    }
}