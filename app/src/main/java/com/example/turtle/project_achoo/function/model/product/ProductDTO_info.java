package com.example.turtle.project_achoo.function.model.product;

import com.example.turtle.project_achoo.function.model.product.ProductDTO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDTO_info {

    @SerializedName("ProductDTO")
    @Expose
    private List<ProductDTO> productDTO = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProductDTO_info (){
    }

    /**
     *
     * @param productDTO
     */
    public ProductDTO_info(List<ProductDTO> productDTO) {
        super();
        this.productDTO = productDTO;
    }

    public List<ProductDTO> getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(List<ProductDTO> productDTO) {
        this.productDTO = productDTO;
    }
}
