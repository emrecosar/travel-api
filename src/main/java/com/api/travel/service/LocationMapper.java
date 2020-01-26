package com.api.travel.service;

import com.api.travel.api.model.LocationModel;
import com.api.travel.data.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    /**
     * @param location location Dao object from data layer
     * @return LocationModel object fro api layer
     */
    public LocationModel mapToLocationModel(Location location) {
        LocationModel locationModel = new LocationModel();
        locationModel.setCode(location.getCode());
        locationModel.setType(location.getType());
        locationModel.setLatitude(location.getLatitude());
        locationModel.setLongitude(location.getLongitude());
        /**
         * Location can have multiple translations but request filters, look for the first one
         * Also If translation may not exist, skip these fields.
         */
        if (!location.getTranslation().isEmpty()) {
            locationModel.setName(location.getTranslation().get(0).getName());
            locationModel.setDescription(location.getTranslation().get(0).getDescription());
        }
        /**
         * Location does not have parent Location if location type is `country`
         */
        if (location.getParentLocation() != null) {
            locationModel.setParentCode(location.getParentLocation().getCode());
            locationModel.setParentType(location.getParentLocation().getType());
        }
        return locationModel;
    }
}
