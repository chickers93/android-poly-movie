package model;

public class PeopleDetails {
    private String birthday;
    private String known_for_department;
    private String deathday;
    private String id;
    private String name;
    private float gender;
    private String biography;
    private float popularity;
    private String place_of_birth;
    private String profile_path = null;
    private boolean adult;
    private String imdb_id;

    public PeopleDetails() {
    }

    public PeopleDetails(String birthday, String known_for_department, String deathday, String id, String name, float gender, String biography, float popularity, String place_of_birth, String profile_path, boolean adult, String imdb_id) {
        this.birthday = birthday;
        this.known_for_department = known_for_department;
        this.deathday = deathday;
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.biography = biography;
        this.popularity = popularity;
        this.place_of_birth = place_of_birth;
        this.profile_path = profile_path;
        this.adult = adult;
        this.imdb_id = imdb_id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
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

    public float getGender() {
        return gender;
    }

    public void setGender(float gender) {
        this.gender = gender;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }
}
