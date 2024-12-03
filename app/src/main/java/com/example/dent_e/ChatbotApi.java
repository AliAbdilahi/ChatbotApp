package com.example.dent_e;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatbotApi {

    @POST("/chatbot")
    Call<ChatbotResponse> sendMessage(@Body ChatbotRequest request);

    @POST("/savemessage")
    Call<ChatbotResponse> saveMessage(@Body ChatbotRequest request);

    @POST("/signup")
    Call<ChatbotResponse> signup(@Body ChatbotRequest request);
    @POST("/login")
    Call<ChatbotResponse> login(@Body ChatbotRequest request);

    @POST("/loadmessages")
    Call<ChatbotResponse> loadmessages(@Body ChatbotRequest request);

    @POST("/loadprofile")
    Call<ChatbotResponse> loadprofile(@Body ChatbotRequest request);
}

