package com.example.turtle.project_achoo.function.model.product;

public class LikeProductDTO {

    private String likePcode;

    private String memberId;

    private String pcode;

    public LikeProductDTO(){

    }

    public LikeProductDTO(String memberId, String pcode) {

        this.memberId = memberId;
        this.pcode=pcode;
    }

    public String getLikePcode() {
        return likePcode;
    }

    public void setLikePcode(String likePcode) {
        this.likePcode = likePcode;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }
}
