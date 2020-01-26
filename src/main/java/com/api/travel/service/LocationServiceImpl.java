package com.api.travel.service;

import com.api.travel.api.model.LocationModel;
import com.api.travel.data.entity.Location;
import com.api.travel.data.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Location service to access data layer and transform responses
 */
@Service
public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;

    private LocationMapper locationMapper;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    /**
     * Find single Location with given type and code
     *
     * @param language the language
     * @param type     the type of the location
     * @param code     the code of the location
     * @return List of LocationModel
     */
    @Transactional // used for fetching the lazy loading bindings in the entity
    @Override
    public List<LocationModel> findAllLocations(String language, String type, String code) {
        List<Location> locations = locationRepository.findAllByLanguageAndTypeAndCode(language, type, code);
        return locations
                .stream()
                .map(l -> locationMapper.mapToLocationModel(l))
                .collect(Collectors.toList());
    }
}
