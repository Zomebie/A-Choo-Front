package com.example.turtle.project_achoo.function.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDTO {

    @SerializedName("swHits")
    @Expose
    private Integer swHits;
    @SerializedName("awHits")
    @Expose
    private Integer awHits;
    @SerializedName("scHits")
    @Expose
    private Integer scHits;
    @SerializedName("wcHits")
    @Expose
    private Integer wcHits;
    @SerializedName("totalHits")
    @Expose
    private Integer totalHits;
    @SerializedName("pcode")
    @Expose
    private String pcode;
    @SerializedName("pbrand")
    @Expose
    private String pbrand;
    @SerializedName("pimg")
    @Expose
    private String pimg;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("pprice")
    @Expose
    private String pprice;
    @SerializedName("pcolor")
    @Expose
    private String pcolor;
    @SerializedName("ptone")
    @Expose
    private String ptone;
    @SerializedName("pcolorCode")
    @Expose
    private String pcolorCode;

    /**
     * No args constructor for use in serialization
     */
    public ProductDTO() {
    }

    /**
     * @param pcolorCode
     * @param swHits
     * @param pprice
     * @param totalHits
     * @param wcHits
     * @param scHits
     * @param pcolor
     * @param ptone
     * @param pname
     * @param awHits
     * @param pcode
     * @param pbrand
     * @param pimg
     */
    public ProductDTO(Integer swHits, Integer awHits, Integer scHits, Integer wcHits, Integer totalHits, String pcode, String pbrand, String pimg, String pname, String pprice, String pcolor, String ptone, String pcolorCode) {
        super();
        this.swHits = swHits;
        this.awHits = awHits;
        this.scHits = scHits;
        this.wcHits = wcHits;
        this.totalHits = totalHits;
        this.pcode = pcode;
        this.pbrand = pbrand;
        this.pimg = pimg;
        this.pname = pname;
        this.pprice = pprice;
        this.pcolor = pcolor;
        this.ptone = ptone;
        this.pcolorCode = pcolorCode;
    }

    public Integer getSwHits() {
        return swHits;
    }

    public void setSwHits(Integer swHits) {
        this.swHits = swHits;
    }

    public Integer getAwHits() {
        return awHits;
    }

    public void setAwHits(Integer awHits) {
        this.awHits = awHits;
    }

    public Integer getScHits() {
        return scHits;
    }

    public void setScHits(Integer scHits) {
        this.scHits = scHits;
    }

    public Integer getWcHits() {
        return wcHits;
    }

    public void setWcHits(Integer wcHits) {
        this.wcHits = wcHits;
    }

    public Integer getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(Integer totalHits) {
        this.totalHits = totalHits;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPbrand() {
        return pbrand;
    }

    public void setPbrand(String pbrand) {
        this.pbrand = pbrand;
    }

    public String getPimg() {
        return pimg;
    }

    public void setPimg(String pimg) {
        this.pimg = pimg;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPcolor() {
        return pcolor;
    }

    public void setPcolor(String pcolor) {
        this.pcolor = pcolor;
    }

    public String getPtone() {
        return ptone;
    }

    public void setPtone(String ptone) {
        this.ptone = ptone;
    }

    public String getPcolorCode() {
        return pcolorCode;
    }

    public void setPcolorCode(String pcolorCode) {
        this.pcolorCode = pcolorCode;
    }

}