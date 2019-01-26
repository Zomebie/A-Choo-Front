package com.example.turtle.project_achoo.function.service.networkService;

import com.example.turtle.project_achoo.function.model.product.ProductDTO_info;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProductService {

    @GET("productList")
    Call<ProductDTO_info> getProductList();

    @GET("userToneProductList")
    Call<ProductDTO_info> getUserToneProductList(@Query("selfT") String selfT);

    @POST("countHits")
    Call<Integer> countHits(@Query("id") String id, @Query("pcode") String pcode);

//    @GET("productImage")
//    Call<Bitmap> getProductImage();
}
