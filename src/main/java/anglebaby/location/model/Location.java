package anglebaby.location.model;

public class Location {
    private long locationID;
    private int shelfID;
    private String position;
    private String stockID;

    public long getLocationID() {
        return locationID;
    }

    public void setLocationID(long locationID) {
        this.locationID = locationID;
    }

    public int getShelfID() {
        return shelfID;
    }

    public void setShelfID(int shelfID) {
        this.shelfID = shelfID;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStockID() {
        return stockID;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
    }

    @Override
    public String toString() {
        return "{" +
                "locationID:" + locationID +
                ",shelfID:" + shelfID +
                ",position:\"" + position + "\"" +
                ",stockID:\"" + stockID + "\"" +
                '}';
    }
}
