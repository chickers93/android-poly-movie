package model;

import java.util.List;

public class CastRespone {
    private String id;
    private List<Cast> cast;

    public CastRespone() {
    }

    public CastRespone(String id, List<Cast> cast) {
        this.id = id;
        this.cast = cast;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }
}
