package com.api.travel.service;

import com.api.travel.api.model.LocationModel;
import com.api.travel.data.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Language service to access data layer for Translation entity and transform responses
 */
@Service
public class LanguageServiceImpl implements LanguageService {

    private TranslationRepository translationRepository;

    private LocationMapper locationMapper;

    @Value("${http.header.accept-language.default}")
    private String defaultLanguage;

    /*
    Hold all possible language options
     */
    private List<Locale> locales;

    @Autowired
    public LanguageServiceImpl(TranslationRepository translationRepository, LocationMapper locationMapper) {
        this.translationRepository = translationRepository;
        this.locationMapper = locationMapper;
    }

    @Override
    public Locale extractAndFindLocale(Optional<String> acceptedLanguage) {

        initLocates();

        // default language
        Locale result = new Locale(defaultLanguage.toUpperCase());

        if (!acceptedLanguage.isPresent())
            return result;

        List<Locale.LanguageRange> ranges;
        try {
            ranges = Locale.LanguageRange.parse(acceptedLanguage.get());
        } catch (IllegalArgumentException iae) { // cannot parse language input, ill-formed
            return result;
        }

        Locale best = Locale.lookup(ranges, locales);
        if (best != null) {
            result = best;
        }
        return result;
    }

    @Transactional // used for fetching the lazy loading bindings in the entity
    @Override
    public List<LocationModel> findAllLocationsByLanguage(String language) {
        return translationRepository.findAllByLanguage(language)
                .stream()
                .map(t -> locationMapper.mapToLocationModel(t.getLocation()))
                .collect(Collectors.toList());
    }

    /**
     * fetch all possible distinct languages from Translation table
     *
     * @return list of String contains distinct languages
     */
    private List<String> fetchAllDistinctLanguages() {
        return translationRepository.findAllDistinctLanguages();
    }

    /**
     * init locale values from database with first request and hold it in memory
     * Check if the value is null, load the languages.
     * After the load, if no local found, `locales` is an empty array or list of Locale items
     * <p>
     * This step can be improved with using cache and/or invalidate interval to refresh languages
     */
    private void initLocates() {
        if (locales == null) {
            // Load all distinct languages
            locales = fetchAllDistinctLanguages()
                    .stream()
                    .map(l -> new Locale(l))
                    .collect(Collectors.toList());
        }
    }

}
