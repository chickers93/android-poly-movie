package api;


import model.CastRespone;
import model.Movie;
import model.MovieRespone;
import model.PeopleDetails;
import model.PeopleRespone;
import model.TV;
import model.TVRespone;
import model.VideoRespone;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    //Movie
    @GET("movie/popular")
    Call<MovieRespone> getPopularMovie(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/now_playing")
    Call<MovieRespone> getNowPlayingMovie(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/upcoming")
    Call<MovieRespone> getComingMovie(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/top_rated")
    Call<MovieRespone> getTopMovie(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{id}")
    Call<Movie> getInforByMovie(@Path("id") String id, @Query("api_key") String apiKey, @Query("append_to_response") String videos, @Query("language") String language);

    @GET("movie/{id}/credits")
    Call<CastRespone> getCredits(@Path("id") String id, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<VideoRespone> getVideos(@Path("id") String id, @Query("api_key") String apiKey);


    //TV Show
    @GET("tv/popular")
    Call<TVRespone> getPopularTV(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/airing_today")
    Call<TVRespone> getAiringTV(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/on_the_air")
    Call<TVRespone> getOnTV(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/top_rated")
    Call<TVRespone> getTopTV(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/{id}")
    Call<TV> getInforByTV(@Path("id") String id, @Query("api_key") String apiKey, @Query("append_to_response") String videos, @Query("language") String language);

    @GET("tv/{id}/credits")
    Call<CastRespone> getCreditsTV(@Path("id") String id, @Query("api_key") String apiKey);

    @GET("tv/{id}/videos")
    Call<VideoRespone> getVideosTV(@Path("id") String id, @Query("api_key") String apiKey);

    //people
    @GET("person/popular")
    Call<PeopleRespone> getPopularPeople(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("person/{id}")
    Call<PeopleDetails> getPeopleDetails(@Path("id") String id, @Query("api_key") String apiKey, @Query("language") String language);

    //search
    @GET("search/movie")
    Call<MovieRespone> getSearchMovie(@Query("api_key") String apiKey, @Query("query") String query);

    @GET("search/tv")
    Call<TVRespone> getSearchTV(@Query("api_key") String apiKey, @Query("query") String query);

    @GET("search/person")
    Call<PeopleRespone> getSearchPerson(@Query("api_key") String apiKey, @Query("query") String query);


}