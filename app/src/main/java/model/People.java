package model;

import java.util.List;

public class People {
    private List<KnownFor> known_for;
    private float popularity;
    private String known_for_department;
    private String id;
    private String name;
    private String profile_path;
    private boolean adult;
    private float gender;

    public People() {
    }

    public People(List<KnownFor> known_for, float popularity, String known_for_department, String id, String name, String profile_path, boolean adult, float gender) {
        this.known_for = known_for;
        this.popularity = popularity;
        this.known_for_department = known_for_department;
        this.id = id;
        this.name = name;
        this.profile_path = profile_path;
        this.adult = adult;
        this.gender = gender;
    }

    public List<KnownFor> getKnown_for() {
        return known_for;
    }

    public void setKnown_for(List<KnownFor> known_for) {
        this.known_for = known_for;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
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

    public float getGender() {
        return gender;
    }

    public void setGender(float gender) {
        this.gender = gender;
    }
}
