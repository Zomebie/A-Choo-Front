package com.example.turtle.project_achoo.function.service.networkService;

import com.example.turtle.project_achoo.function.model.member.NaverResponseDTO_info;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface NaverLoginService {

    @GET("https://openapi.naver.com/v1/nid/me")
    Call<NaverResponseDTO_info> naverLogin(@Header("Authorization") String header);
}
