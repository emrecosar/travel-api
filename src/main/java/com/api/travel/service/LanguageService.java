package com.api.travel.service;

import com.api.travel.api.model.LocationModel;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface LanguageService {

    /**
     * Parse acceptedLanguage header and return available distinct acceptedLanguage
     *
     * @param acceptedLanguage the acceptedLanguage input param
     * @return Locale value, matching with database
     */
    Locale extractAndFindLocale(Optional<String> acceptedLanguage);

    /**
     * Fetch all matching language entities and collect Location entities from it
     *
     * @param language the language input param
     * @return List of LocationModel value, matching with database
     */
    List<LocationModel> findAllLocationsByLanguage(String language);
}
