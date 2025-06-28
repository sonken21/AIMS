package com.aims.son.entity.media;

import com.aims.son.repositories.DbConnection.SQLiteConnectionManager;
import com.aims.son.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The general media class, for another media it can be done by inheriting this class
 * @author nguyenlm
 */
public class Media {

    private static Logger LOGGER = Utils.getLogger(Media.class.getName());

    protected Statement stm;
    protected int id;
    protected String title;
    protected String category;
    protected int value; // the real price of product (eg: 450)
    protected int price; // the price which will be displayed on browser (eg: 500)
    protected int quantity;
    protected String type;
    protected String imageURL;
    protected double weight;

    public Media() throws SQLException{
        stm = SQLiteConnectionManager.getConnection().createStatement();
    }

    public Media (int id, String title, String category, int value, int price, int quantity, String type, String imageURL, double weight) throws SQLException{
        this.id = id;
        this.title = title;
        this.category = category;
        this.value = value;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.imageURL = imageURL;
        this.weight = weight;
        //stm = DBConnection.getConnection().createStatement();
    }

    public int getQuantity() throws SQLException{
//        int updated_quantity = getMediaById(id).quantity;
//        this.quantity = updated_quantity;
//        return updated_quantity;
        return this.quantity;
    }

//    public Media getMediaById(int id) throws SQLException{
//        String sql = "SELECT * FROM Media ;";
//        Statement stm = SQLiteConnectionManager.getConnection().createStatement();
//        ResultSet res = stm.executeQuery(sql);
//        if(res.next()) {
//
//            return new Media()
//                    .setId(res.getInt("id"))
//                    .setTitle(res.getString("title"))
//                    .setQuantity(res.getInt("quantity"))
//                    .setCategory(res.getString("category"))
//                    .setMediaURL(res.getString("imageUrl"))
//                    .setPrice(res.getInt("price"))
//                    .setType(res.getString("type"));
//        }
//        return null;
//    }
//
//    public List getAllMedia() throws SQLException{
//        Statement stm = SQLiteConnectionManager.getConnection().createStatement();
//        ResultSet res = stm.executeQuery("select * from Media");
//        ArrayList medium = new ArrayList<>();
//        while (res.next()) {
//            Media media = new Media()
//                    .setId(res.getInt("id"))
//                    .setTitle(res.getString("title"))
//                    .setQuantity(res.getInt("quantity"))
//                    .setCategory(res.getString("category"))
//                    .setMediaURL(res.getString("imageUrl"))
//                    .setPrice(res.getInt("price"))
//                    .setType(res.getString("type"))
//                    .setWeight(res.getDouble("weight"));
//            medium.add(media);
//        }
//        return medium;
//    }
//
//    public void updateMediaFieldById(String tbname, int id, String field, Object value) throws SQLException {
//        Statement stm = SQLiteConnectionManager.getConnection().createStatement();
//        if (value instanceof String){
//            value = "\"" + value + "\"";
//        }
//        stm.executeUpdate(" update " + tbname + " set" + " "
//                + field + "=" + value + " "
//                + "where id=" + id + ";");
//    }

    // getter and setter 
    public int getId() {
        return this.id;
    }

    public Media setId(int id){
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Media setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public Media setCategory(String category) {
        this.category = category;
        return this;
    }
    public int getValue() {
        return this.value;
    }
    public Media setValue(int value) {
        this.value = value;
        return this;
    }

    public int getPrice() {
        return this.price;
    }

    public Media setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getImageURL(){
        return this.imageURL;
    }

    public Media setMediaURL(String url){
        this.imageURL = url;
        return this;
    }

    public Media setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Media setType(String type) {
        this.type = type;
        return this;
    }

    public double getWeight() {
        return weight;
    }
    public Media setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", title='" + title + "'" +
                ", category='" + category + "'" +
                ", price='" + price + "'" +
                ", quantity='" + quantity + "'" +
                ", type='" + type + "'" +
                ", imageURL='" + imageURL + "'" +
                "}";
    }

}
