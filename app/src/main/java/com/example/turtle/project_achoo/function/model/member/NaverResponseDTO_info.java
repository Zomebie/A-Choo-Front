package com.example.turtle.project_achoo.function.model.member;

import com.example.turtle.project_achoo.function.model.member.NaverResponseDTO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NaverResponseDTO_info {

    @SerializedName("resultcode")
    @Expose
    private String resultcode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private NaverResponseDTO response;

    /**
     * No args constructor for use in serialization
     *
     */
    public NaverResponseDTO_info() {
    }

    /**
     *
     * @param response
     * @param message
     * @param resultcode
     */
    public NaverResponseDTO_info(String resultcode, String message, NaverResponseDTO response) {
        super();
        this.resultcode = resultcode;
        this.message = message;
        this.response = response;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NaverResponseDTO getResponse() {
        return response;
    }

    public void setResponse(NaverResponseDTO response) {
        this.response = response;
    }

}