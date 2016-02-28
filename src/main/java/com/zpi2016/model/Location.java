package com.zpi2016.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by filip on 26.02.2016.
 */

@Entity
@Table(name = "LOCATIONS")
public class Location extends GenericEntity<Location> {

    @Column(name = "GEOLONGITUDE", nullable = false)
    private Float geoLongitude;

    @Column(name = "GEOLATITUDE", nullable = false)
    private Float geoLatitude;

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
