package com.api.travel.service;

import com.api.travel.api.model.LocationModel;

import java.util.List;

public interface LocationService {

    /**
     * @param language the language param
     * @param type     the type of the location
     * @param code     the code of the location
     * @return List of LocationModel for API response
     */
    List<LocationModel> findAllLocations(String language, String type, String code);

}
