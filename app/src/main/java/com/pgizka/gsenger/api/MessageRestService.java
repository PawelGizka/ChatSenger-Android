package com.pgizka.gsenger.api;

import com.pgizka.gsenger.jobqueue.sendMessge.PutMessageResponse;
import com.pgizka.gsenger.jobqueue.sendMessge.PutTextMessageRequest;
import com.pgizka.gsenger.jobqueue.setMessageState.MessageStateChangedRequest;
import com.pgizka.gsenger.welcome.registration.UserRegistrationRequest;
import com.pgizka.gsenger.welcome.registration.UserRegistrationResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface MessageRestService {

    @PUT("message/text")
    Call<PutMessageResponse> sendTextMessage(@Body PutTextMessageRequest textMessageRequest);

    @Multipart
    @PUT("message/media")
    Call<PutMessageResponse> sendMediaMessage(
            @Part("data") RequestBody dataRequestBody,
            @Part("metadata") RequestBody metadataRequestBody);

    @POST("message/delivered")
    Call<BaseResponse> setMessageDelivered(@Body MessageStateChangedRequest messageStateChangedRequest);

    @POST("message/viewed")
    Call<BaseResponse> setMessageViewed(@Body MessageStateChangedRequest messageStateChangedRequest);

}
