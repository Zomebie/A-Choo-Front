package com.example.turtle.project_achoo.function.model.member;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NaverResponseDTO {


    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("birthday")
    @Expose
    private String birthday;

    /**
     * No args constructor for use in serialization
     */


    /**
     * @param id
     * @param birthday
     * @param profileImage
     * @param nickname
     * @param email
     * @param name
     * @param age
     * @param gender
     */
    public NaverResponseDTO(String email, String nickname, String profileImage, String age, String gender, String id, String name, String birthday) {
        super();
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.age = age;
        this.gender = gender;
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

}