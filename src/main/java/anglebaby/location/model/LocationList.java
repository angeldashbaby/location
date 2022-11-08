package anglebaby.location.model;

import com.google.gson.Gson;

import java.util.ArrayList;

public class LocationList {
    private ArrayList<Location> locationsList;

    public LocationList() {
        locationsList = new ArrayList<>();
    }

    public void addLocation(String payload) {
        Location newLocation = new Gson().fromJson(payload, Location.class);
        locationsList.add(newLocation);
    }

    public Location findFreeLocation() {
        for (Location location: locationsList) {
            if (location.getStockID() == null)
                return location;
        }
        return null;
    }

    @Override
    public String toString() {
        String result = "{";
        for (Location location: locationsList) {
            result += location.toString() + ",";
        }
        return result + "}";
    }
}
