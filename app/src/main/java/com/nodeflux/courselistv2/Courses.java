package com.nodeflux.courselistv2;

/**
 * Created by cmmuk_000 on 12/16/2016.
 */

public class Courses {

    private String Name;
    private String University;
    private String Country;

    public Courses() {
    }

    public Courses(String country, String name, String university) {
        Country = country;
        Name = name;
        University = university;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUniversity() {
        return University;
    }

    public void setUniversity(String university) {
        University = university;
    }

}
