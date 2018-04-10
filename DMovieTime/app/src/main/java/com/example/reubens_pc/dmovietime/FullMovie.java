package com.example.reubens_pc.dmovietime;



public class FullMovie extends Movie {
    //extend of movie with extra details on the movie
    private float vote_average;
    private String release_date;
    private int budget;
    private int runtime;//movie length


    public FullMovie(int id, String subject, String body, String url, int No, float vote_average, String release_date, int budget, int runtime) {
        super(id, subject, body, url, No);
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.budget = budget;
        this.runtime = runtime;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getBudget() {
        return budget;
    }

    public int getRuntime() {
        return runtime;
    }

}
