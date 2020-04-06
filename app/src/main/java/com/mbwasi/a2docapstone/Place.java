package com.mbwasi.a2docapstone;


import java.io.Serializable;

public class Place implements Serializable
{
    private int id;

    private String name;

    private String slug;

    private String image;

    private String address;

    private String city;

    private String state;

    private String area;

    private String postal_code;

    private String country;

    private String website;

    private String phone;

    private String latitude;

    private String longitude;

    private String description;

    private int stars;

    private int price;

    private int votes;

    private int resource_id;

    private String tags;

    private int status;

    private String created_at;

    private String updated_at;

    private String deleted_at;

    private double distance;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setSlug(String slug){
        this.slug = slug;
    }
    public String getSlug(){
        return this.slug;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return this.image;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return this.address;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }
    public void setState(String state){
        this.state = state;
    }
    public String getState(){
        return this.state;
    }
    public void setArea(String area){
        this.area = area;
    }
    public String getArea(){
        return this.area;
    }
    public void setPostal_code(String postal_code){
        this.postal_code = postal_code;
    }
    public String getPostal_code(){
        return this.postal_code;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public String getCountry(){
        return this.country;
    }
    public void setWebsite(String website){
        this.website = website;
    }
    public String getWebsite(){
        return this.website;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getPhone(){
        return this.phone;
    }
    public void setLatitude(String latitude){
        this.latitude = latitude;
    }
    public String getLatitude(){
        return this.latitude;
    }
    public void setLongitude(String longitude){
        this.longitude = longitude;
    }
    public String getLongitude(){
        return this.longitude;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setStars(int stars){
        this.stars = stars;
    }
    public int getStars(){
        return this.stars;
    }
    public void setPrice(int price){
        this.price = price;
    }
    public int getPrice(){
        return this.price;
    }
    public void setVotes(int votes){
        this.votes = votes;
    }
    public int getVotes(){
        return this.votes;
    }
    public void setResource_id(int resource_id){
        this.resource_id = resource_id;
    }
    public int getResource_id(){
        return this.resource_id;
    }
    public void setTags(String tags){
        this.tags = tags;
    }
    public String getTags(){
        return this.tags;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setCreated_at(String created_at){
        this.created_at = created_at;
    }
    public String getCreated_at(){
        return this.created_at;
    }
    public void setUpdated_at(String updated_at){
        this.updated_at = updated_at;
    }
    public String getUpdated_at(){
        return this.updated_at;
    }
    public void setDeleted_at(String deleted_at){
        this.deleted_at = deleted_at;
    }
    public String getDeleted_at(){
        return this.deleted_at;
    }
    public void setDistance(double distance){
        this.distance = distance;
    }
    public double getDistance(){
        return this.distance;
    }
}
