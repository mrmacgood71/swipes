package it.swipes.app.service;

import java.io.File;
import java.util.List;

import io.reactivex.Single;

import it.swipes.app.account.model.Profile;
import it.swipes.app.news.model.FullPost;
import it.swipes.app.publisher.model.Publisher;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SwipesApi {

    String BASE_URL = "https://swipes-dev.herokuapp.com/";

    @GET("api/v1/publishers")
    Call<List<Publisher>> getPublishers();

    @GET("api/v1/publishers/{id}")
    Call<Publisher> getPublisher(@Path("id") String id);

    @GET("api/v1/profile/{id}")
    Call<Profile> getProfile(@Path("id") String id);
//    https://swipes-dev.herokuapp.com/api/v1/edit/115105241828159907784

    @GET("api/v1/edit/{id}")
    Call<Profile> getProfileToEdit(@Path("id") String id);

    @GET("/api/v2/post")
    Call<List<FullPost>> getPosts();

    @POST("api/v1/edit/{id}")
    Call<Profile> setProfile(@Path("id") String id);

    @POST("api/v1/profile")
    Call<Profile> createProfile(@Body Profile profile);

    @POST("api/v1/publishers")
    Call<Profile> createPublisher(@Body Publisher publisher);

    @POST("api/v0/send")
    Call<String> sendImage(@Body File image, @Query("name") String name);


}
