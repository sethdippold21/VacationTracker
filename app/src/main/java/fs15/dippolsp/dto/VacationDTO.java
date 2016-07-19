package fs15.dippolsp.dto;

/**
 * This class is for the vacation attributes.
 * Created by Seth on 10/22/2015.
 */
public class VacationDTO {

    private long cacheID;
    private String tripName;
    private String description;
    private String startDate;
    private String endDate;
    private String longitude;
    private String latitude;
    private String pictureUri;


    public String getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(String pictureUri) {
        this.pictureUri = pictureUri;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String toString(){
        return tripName + "     " + description + "\nStart: " + startDate + "   End: " + endDate + "\nLat: " + latitude + "   Long: " + longitude + "\n" + pictureUri;
    }

    public long getCacheID() {
        return cacheID;
    }

    public void setCacheID(long cacheID) {
        this.cacheID = cacheID;
    }

}
