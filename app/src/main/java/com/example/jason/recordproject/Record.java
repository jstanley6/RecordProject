package com.example.jason.recordproject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Record {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("created_at")
    @Expose
    private Date createdAt;
    @SerializedName("updated_at")
    @Expose
    private Date updatedAt;

    /**
     * No args constructor for use in serialization
     *
     */
    public Record() {
    }

    /**
     *
     * @param updatedAt
     * @param id
     * @param price
     * @param createdAt
     * @param description
     * @param name
     * @param image
     * @param rating
     */
    public Record(Integer id, String name, String description, Double price, Integer rating, String image, Date createdAt, Date updatedAt) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
//@Entity(tableName = "records")
//public class Record {
//
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "recordID")
//    private long recordID;
//
//    @ColumnInfo(name = "name")
//    private String name;
//
//    @ColumnInfo(name = "description")
//    private String description;
//
//    @ColumnInfo(name =  "price")
//    private double price;
//
//    @ColumnInfo(name =  "rating")
//    private int rating;
//
//    @TypeConverters(Converters.class)
//    @ColumnInfo(name = "dateModified")
//    private Date dateModified;
//
//    @TypeConverters(Converters.class)
//    @ColumnInfo(name = "dateCreated")
//    private Date dateCreated;
//
//    public long getRecordID() {
//        return recordID;
//    }
//
//    public void setRecordID(long recordID) {
//        this.recordID = recordID;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public int getRating() {
//        return rating;
//    }
//
//    public void setRating(int rating) {
//        this.rating = rating;
//    }
//
//    public Date getDateModified() {
//        return dateModified;
//    }
//
//    public void setDateModified(Date dateModified) {
//        this.dateModified = dateModified;
//    }
//
//    public Date getDateCreated() {
//        return dateCreated;
//    }
//
//    public void setDateCreated(Date dateCreated) {
//        this.dateCreated = dateCreated;
//    }
//}
