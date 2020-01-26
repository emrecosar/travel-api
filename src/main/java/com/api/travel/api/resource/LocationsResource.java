package com.api.travel.api.resource;

import com.api.travel.api.model.LocationModel;
import com.api.travel.service.LanguageServiceImpl;
import com.api.travel.service.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("${app.locations.path}")
public class LocationsResource {

    private LocationServiceImpl locationService;

    private LanguageServiceImpl languageService;

    @Autowired
    public LocationsResource(LocationServiceImpl locationService, LanguageServiceImpl languageService) {
        this.locationService = locationService;
        this.languageService = languageService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getLocationsForLanguage(
            @RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, required = false) Optional<String> acceptLanguage) {
        Locale locale = languageService.extractAndFindLocale(acceptLanguage);
        List<LocationModel> allLocationsByLanguage = languageService.findAllLocationsByLanguage(locale.getLanguage().toUpperCase());
        if (!allLocationsByLanguage.isEmpty())
            return ResponseEntity.ok(allLocationsByLanguage);
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/{type}/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    // This path variables are fixed. I choose to not make these 2 as a property variable
    public ResponseEntity getLocationByTypeAndCode(
            @RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, required = false) Optional<String> acceptLanguage,
            @PathVariable(name = "type") String type,
            @PathVariable(name = "code") String code) {
        Locale locale = languageService.extractAndFindLocale(acceptLanguage);
        List<LocationModel> locations = locationService.findAllLocations(locale.getLanguage().toUpperCase(), type, code);
        if (!locations.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(locations);
        return ResponseEntity.notFound().build();
    }

}
