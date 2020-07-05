package model;

import java.util.List;

public class VideoRespone {
    private List<VideoKey> results;

    public VideoRespone() {
    }

    public VideoRespone(List<VideoKey> results) {
        this.results = results;
    }

    public List<VideoKey> getResults() {
        return results;
    }

    public void setResults(List<VideoKey> results) {
        this.results = results;
    }
}
