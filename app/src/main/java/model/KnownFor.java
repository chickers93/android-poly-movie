package model;

import android.os.Parcel;
import android.os.Parcelable;

public class KnownFor implements Parcelable {
    private String original_name;
    private float vote_count;
    private String poster_path;
    private String media_type;
    private String name;
    private float vote_average;
    private String id;
    private String first_air_date;
    private String original_language;
    private String backdrop_path;
    private String overview;
    private String release_date;
    private boolean video;
    private String title;
    private String original_title;
    private boolean adult;

    public KnownFor() {
    }

    public KnownFor(String original_name, float vote_count, String poster_path, String media_type, String name, float vote_average, String id, String first_air_date, String original_language, String backdrop_path, String overview, String release_date, boolean video, String title, String original_title, boolean adult) {
        this.original_name = original_name;
        this.vote_count = vote_count;
        this.poster_path = poster_path;
        this.media_type = media_type;
        this.name = name;
        this.vote_average = vote_average;
        this.id = id;
        this.first_air_date = first_air_date;
        this.original_language = original_language;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
        this.video = video;
        this.title = title;
        this.original_title = original_title;
        this.adult = adult;
    }

    protected KnownFor(Parcel in) {
        original_name = in.readString();
        vote_count = in.readFloat();
        poster_path = in.readString();
        media_type = in.readString();
        name = in.readString();
        vote_average = in.readFloat();
        id = in.readString();
        first_air_date = in.readString();
        original_language = in.readString();
        backdrop_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        video = in.readByte() != 0;
        title = in.readString();
        original_title = in.readString();
        adult = in.readByte() != 0;
    }

    public static final Creator<KnownFor> CREATOR = new Creator<KnownFor>() {
        @Override
        public KnownFor createFromParcel(Parcel in) {
            return new KnownFor(in);
        }

        @Override
        public KnownFor[] newArray(int size) {
            return new KnownFor[size];
        }
    };

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public float getVote_count() {
        return vote_count;
    }

    public void setVote_count(float vote_count) {
        this.vote_count = vote_count;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(original_name);
        dest.writeFloat(vote_count);
        dest.writeString(poster_path);
        dest.writeString(media_type);
        dest.writeString(name);
        dest.writeFloat(vote_average);
        dest.writeString(id);
        dest.writeString(first_air_date);
        dest.writeString(original_language);
        dest.writeString(backdrop_path);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeString(title);
        dest.writeString(original_title);
        dest.writeByte((byte) (adult ? 1 : 0));
    }
}
