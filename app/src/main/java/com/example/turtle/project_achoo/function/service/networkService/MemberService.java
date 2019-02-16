package com.example.turtle.project_achoo.function.service.networkService;

import com.example.turtle.project_achoo.function.model.member.MemberDTO;
import com.example.turtle.project_achoo.function.model.member.SelfTestResultDTO;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

//API service : 통신하려는 주소, 메소드 정의
public interface MemberService {

    //REST API 명세에 맞게 인터페이스 선언해주기
    //@FormUrlEncoded @Field String user 사용 시

    @POST("login")
    Call<MemberDTO> getLoginResult(@Query("id") String id, @Query("pw") String pw); // 서버에서 전달받을 데이터타입Member

    @POST("duplicationCheckId")
    Call<Integer> duplicationCheckId(@Query("id") String id);

    @POST("duplicationCheckNick")
    Call<Integer> duplicationCheckNick(@Query("nick") String nick);

    @POST("join")
    Call<Integer> join(@Body RequestBody memberDTO);

    @POST("modify")
    Call<Integer> modify(@Body RequestBody memberDTO);

    @POST("setSelfTestResult")
    Call <Integer> setSelfTestResult(@Query("selfT") String selfT, @Query("nick") String nick, @Query("money") int money, @Query("lipColor") String lip);

    @GET("getSelfTestResult")
    Call<SelfTestResultDTO> getSelfTestResult(@Query("id") String id);

    @POST("memberDetailT")
    Call<Integer> memberDetailT(@Body RequestBody memberDTO);

    @POST("info_detailtest")
    Call<Integer> info_detailtest(@Body RequestBody memberDTO);


}
