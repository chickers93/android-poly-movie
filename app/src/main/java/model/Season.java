package model;

public class Season {
    private String air_date = null;
    private float episode_count;
    private String id;
    private String name;
    private String overview;
    private String poster_path = null;
    private float season_number;

    public Season() {
    }

    public Season(String air_date, float episode_count, String id, String name, String overview, String poster_path, float season_number) {
        this.air_date = air_date;
        this.episode_count = episode_count;
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.poster_path = poster_path;
        this.season_number = season_number;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public float getEpisode_count() {
        return episode_count;
    }

    public void setEpisode_count(float episode_count) {
        this.episode_count = episode_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public float getSeason_number() {
        return season_number;
    }

    public void setSeason_number(float season_number) {
        this.season_number = season_number;
    }
}
