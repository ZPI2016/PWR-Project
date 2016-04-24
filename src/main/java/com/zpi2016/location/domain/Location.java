package com.zpi2016.location.domain;

import com.zpi2016.core.common.domain.GenericEntity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Created by filip on 26.02.2016.
 */

@Entity
public class Location extends GenericEntity {

    @NotNull
    private Float geoLongitude;

    @NotNull
    private Float geoLatitude;

    public Location() {}

    public Location(Float geoLongitude, Float geoLatitude) {
        this.geoLatitude =  geoLatitude;
        this.geoLongitude = geoLongitude;
    }

    public void updateWithPropertiesFrom(Location other) {
        if (other.geoLatitude != null && !this.geoLatitude.equals(other.geoLatitude))  this.geoLatitude =  other.geoLatitude;
        if (other.geoLongitude != null && !this.geoLongitude.equals(other.geoLongitude)) this.geoLongitude = other.geoLongitude;
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
