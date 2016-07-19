package fs15.dippolsp.dto;

/**
 * this class is for the bucket list attributes
 * Created by Seth on 10/27/2015.
 */
public class BucketListDTO {

    private long cacheID;
    private String tripName;
    private String description;
    private String city;
    private String state;
    private String country;
    private String pictureUri;


    public String getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(String pictureUri) {
        this.pictureUri = pictureUri;
    }

    public long getCacheID() {
        return cacheID;
    }

    public void setCacheID(long cacheID) {
        this.cacheID = cacheID;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String toString(){
        return tripName + "     " + description + "\nCity: " + city + "   State: " + state + "   Country:" + country + "\n" + pictureUri;
    }

}
