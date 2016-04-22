package com.zpi2016.location.domain;

import com.zpi2016.support.common.GenericEntity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Created by filip on 26.02.2016.
 */

@Entity
public class Location extends GenericEntity {

    @NotNull
    private Double geoLongitude;

    @NotNull
    private Double geoLatitude;

    public Location() {}

    public Location(Double geoLongitude, Double geoLatitude) {
        this.geoLatitude =  geoLatitude;
        this.geoLongitude = geoLongitude;
    }

    public void copy(Location other) {
        if (other.geoLatitude != null && !this.geoLatitude.equals(other.geoLatitude))  this.geoLatitude =  other.geoLatitude;
        if (other.geoLongitude != null && !this.geoLongitude.equals(other.geoLongitude)) this.geoLongitude = other.geoLongitude;
    }

    public Double getGeoLatitude() {
        return geoLatitude;
    }

    public void setGeoLatitude(Double geoLatitude) {
        this.geoLatitude = geoLatitude;
    }

    public Double getGeoLongitude() {
        return geoLongitude;
    }

    public void setGeoLongitude(Double geoLongitude) {
        this.geoLongitude = geoLongitude;
    }

}
