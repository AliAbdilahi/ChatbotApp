package com.example.dent_e;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Signupapi {

    @POST("/signup")
    Call<ChatbotResponse> sendMessage(@Body SignupRequest request);
}
