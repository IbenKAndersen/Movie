package dto;

import entities.Movie;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author ibenk
 */
public class MovieDTO {

    private long id;
    private String name;
    private int year;
    private String[] actors;

    public MovieDTO() {
    }

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.name = movie.getName();
        this.year = movie.getYear();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
