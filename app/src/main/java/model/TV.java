package model;

import java.util.ArrayList;
import java.util.List;

public class TV {
    List<Season> seasons;
    private String backdrop_path;
    private String first_air_date;
    private List<Genres> genres;
    private String homepage;
    private String id;
    private boolean in_production;
    private String last_air_date;
    private String name;
    private float number_of_episodes;
    private float number_of_seasons;
    private String original_language;
    private String original_name;
    private String overview;
    private float popularity;
    private String poster_path;
    private String status;
    private String type;
    private float vote_average;
    private float vote_count;
    private List<Integer> episode_run_time;

    public TV() {
    }

    public TV(List<Season> seasons, String backdrop_path, String first_air_date, List<Genres> genres, String homepage, String id, boolean in_production, String last_air_date, String name, float number_of_episodes, float number_of_seasons, String original_language, String original_name, String overview, float popularity, String poster_path, String status, String type, float vote_average, float vote_count, List<Integer> episode_run_time) {
        this.seasons = seasons;
        this.backdrop_path = backdrop_path;
        this.first_air_date = first_air_date;
        this.genres = genres;
        this.homepage = homepage;
        this.id = id;
        this.in_production = in_production;
        this.last_air_date = last_air_date;
        this.name = name;
        this.number_of_episodes = number_of_episodes;
        this.number_of_seasons = number_of_seasons;
        this.original_language = original_language;
        this.original_name = original_name;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.status = status;
        this.type = type;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.episode_run_time = episode_run_time;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIn_production() {
        return in_production;
    }

    public void setIn_production(boolean in_production) {
        this.in_production = in_production;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(float number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public float getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(float number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public float getVote_count() {
        return vote_count;
    }

    public void setVote_count(float vote_count) {
        this.vote_count = vote_count;
    }

    public List<Integer> getEpisode_run_time() {
        return episode_run_time;
    }

    public void setEpisode_run_time(List<Integer> episode_run_time) {
        this.episode_run_time = episode_run_time;
    }
}
