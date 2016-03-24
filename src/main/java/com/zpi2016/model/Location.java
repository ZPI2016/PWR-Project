package com.zpi2016.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by filip on 26.02.2016.
 */

@Entity
public class Location extends GenericEntity<Location> {

    @NotNull
    private Float geoLongitude;

    @NotNull
    private Float geoLatitude;

    public Location() {}

    public Location(Float geoLongitude, Float geoLatitude) {
        this.geoLatitude =  geoLatitude;
        this.geoLongitude = geoLongitude;
    }

    public void copy(Location other) {
        this.geoLatitude =  other.geoLatitude;
        this.geoLongitude = other.geoLongitude;
    }

    public Float getGeoLatitude() {
        return geoLatitude;
    }

    public void setGeoLatitude(Float geoLatitude) {
        this.geoLatitude = geoLatitude;
    }

    public Float getGeoLongitude() {
        return geoLongitude;
    }

    public void setGeoLongitude(Float geoLongitude) {
        this.geoLongitude = geoLongitude;
    }
}
