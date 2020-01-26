package com.api.travel.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Location Entity
 */
@Entity(name = "Location")
@Table(name = "Location")
public class Location {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "type")
    private String type;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "latitude")
    private Double latitude;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Location parentLocation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentLocation")
    private List<Location> childLocation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    private List<Translation> translation;


    public Location() {
    }

    public Location(Long id, String code, String type, Double latitude, Double longitude, Location parentLocation) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.parentLocation = parentLocation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Location getParentLocation() {
        return parentLocation;
    }

    public void setParentLocation(Location parentLocation) {
        this.parentLocation = parentLocation;
    }

    public List<Location> getChildLocation() {
        return childLocation;
    }

    public void setChildLocation(List<Location> childLocation) {
        this.childLocation = childLocation;
    }

    public List<Translation> getTranslation() {
        return translation;
    }

    public void setTranslation(List<Translation> translation) {
        this.translation = translation;
    }
}