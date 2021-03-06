package com.pgizka.gsenger.api;

import com.pgizka.gsenger.api.dtos.messages.MessageBatchResponse;
import com.pgizka.gsenger.api.dtos.messages.MessagesStateChangedRequest;
import com.pgizka.gsenger.api.dtos.messages.PutMessageResponse;
import com.pgizka.gsenger.api.dtos.messages.PutTextMessageRequest;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface MessageRestService {

    @PUT("message/text")
    Call<PutMessageResponse> sendTextMessage(@Body PutTextMessageRequest textMessageRequest);

    @Multipart
    @PUT("message/media")
    Call<PutMessageResponse> sendMediaMessage(
            @Part("data") RequestBody dataRequestBody,
            @Part("metadata") RequestBody metadataRequestBody);

    @GET("message/mediaData/{messageId}")
    Call<ResponseBody> getMediaMessageData(@Path("messageId") int messageId);

    @POST("message/updateState")
    Call<ResponseBody> updateMessageState(@Body MessagesStateChangedRequest messagesStateChangedRequest);

    @GET("message/chatMessages/{chatId}")
    Call<MessageBatchResponse> getMessagesForChat(@Path("chatId") String chatId);

}
