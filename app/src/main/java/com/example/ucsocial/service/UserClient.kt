package com.example.ucsocial.service

import android.provider.ContactsContract.DisplayPhoto
import com.example.ucsocial.model.CommentReq
import com.example.ucsocial.model.Commentku
import com.example.ucsocial.model.Content
import com.example.ucsocial.model.ContentUpdateRequest
import com.example.ucsocial.model.Login
import com.example.ucsocial.model.LoginToken
import com.example.ucsocial.model.Pengguna
import com.example.ucsocial.model.Register
import com.example.ucsocial.model.RegisterInfo
import com.example.ucsocial.model.UserUpdateReq2
import com.example.ucsocial.model.UserUpdateRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.w3c.dom.Comment
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

public interface UserClient {

    @POST("login")
    suspend fun login(@Body login: Login) : LoginToken

    @POST("register")
    suspend fun register(@Body registerInfo: RegisterInfo): Register

    @GET("contents")
    suspend fun getAllContent(@Header("Authorization") token: String) : List<Content>

    @GET("content/{id}")
    suspend fun getKontenById(@Header("Authorization") token: String, @Path("id") contentId: String): Content

    @Multipart
    @POST("content")
    suspend fun createContent(
        @Header("Authorization") token: String,
        @Part("headline") headline: RequestBody,
        @Part("content_text") contentText: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part("user") user: RequestBody,
        @Part file: MultipartBody.Part
    )

    @POST("content")
    suspend fun createContentWithoutPhoto(
        @Header("Authorization") token: String,
        @Body requestBody: RequestBody
    )

    @GET("logout")
    suspend fun logout(@Header("Authorization") token: String)

    @DELETE("content/{id}")
    suspend fun delete(@Header("Authorization") token: String, @Path("id") contentId: String)

    @GET("user")
    suspend fun getUser(@Header("Authorization") token: String): Pengguna

    @GET("user/contents/{userId}")
    suspend fun getUserKonten(@Header("Aauthorization") token: String, @Path("userId") userId: String): List<Content>

    @PATCH("content/{id}")
    suspend fun updateContent(
        @Header("Aauthorization") token: String,
        @Path("id") id: String,
        @Body request: ContentUpdateRequest

    )

    @Multipart
    @PATCH("user")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("bio") bio: RequestBody,
        @Part photo: MultipartBody.Part,
        @Part("password") password: RequestBody,
    )
    @Multipart
    @POST("user/image")
    suspend fun updateIMG(

        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,

        )
    @PATCH("user")
    suspend fun updateUserRaw(
        @Header("Authorization") token: String,
        @Body requestBody: UserUpdateRequest,
    )

    @PATCH("user")
    suspend fun updateUserRaw1(
        @Header("Authorization") token: String,
        @Body requestBody: UserUpdateReq2,
    )

    @GET("user/{id}")
    suspend fun getUserbyId(
        @Header("Authorization") token: String,
        @Path("id") id: String

    ): Pengguna


    @GET("user/contents/{userId}")
    suspend fun getSomeUserContent(
        @Header("Authorization") token: String,
        @Path("userId") id: String

    ) : List<Content>


    @GET("content/{contentId}/comments")
    suspend fun getComment(
        @Header("Authorization") token: String,
        @Path("contentId") id: String,

        ): List<Commentku>

    @POST("comment")
    suspend fun createComment(
        @Header("Authorization") token: String,
        @Body req: CommentReq
    )



    @PATCH("userpp")
    suspend fun gantiPP(

        @Header("Authorization") token: String
    )

}