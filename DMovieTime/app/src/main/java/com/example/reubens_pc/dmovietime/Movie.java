package com.example.reubens_pc.dmovietime;


public class Movie {

    private int _id;
    private String subject;
    private String body;
    private String url;
    private int orderNumber;//id of the movie in the api so i will have access to the details of the movie later on
    private int watched=0;
    public Movie(){}
    public Movie(int id, String subject , String body , String url){
        this._id = id;
        this.subject = subject;
        this.body = body;
        this.url = url;
    }
    public Movie(String subject , String body , String url){
        this.subject = subject;
        this.body = body;
        this.url = url;
    }
    public Movie(String subject , String body){
        this.subject = subject;
        this.body = body;

    }
    public Movie(String subject){
        this.subject = subject;
    }
    public Movie(int id, String subject , String body , String url, int orderNumber){
        this._id = id;
        this.subject = subject;
        this.body = body;
        this.url = url;
        this.orderNumber = orderNumber;
    }
    public Movie(int id, String subject , String body , String url, int orderNumber, int watched){
        this._id = id;
        this.subject = subject;
        this.body = body;
        this.url = url;
        this.orderNumber = orderNumber;
        this.watched=watched;
    }
    public Movie(String subject , String body , String url, int orderNumber){
        this.subject = subject;
        this.body = body;
        this.url = url;
        this.orderNumber = orderNumber;
    }
    public Movie(String subject , String body, int orderNumber){
        this.subject = subject;
        this.body = body;
        this.orderNumber = orderNumber;
    }
    public Movie(String subject, int orderNumber){

        this.subject = subject;
        this.orderNumber = orderNumber;

    }

    public int getWatched() {
        return watched;
    }

    public void setWatched(int watched) {
        this.watched = watched;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
