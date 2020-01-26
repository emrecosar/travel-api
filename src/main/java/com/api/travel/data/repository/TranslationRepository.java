package com.api.travel.data.repository;

import com.api.travel.data.entity.Translation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to interact with Translation table
 */
@Repository
public interface TranslationRepository extends CrudRepository<Translation, Long> {

    /**
     * Fetch all distinct languages
     *
     * @return List of Translation entity
     */
    @Query(value =
            " SELECT distinct t.language FROM TRANSLATION t ",
            nativeQuery = true)
    List<String> findAllDistinctLanguages();

    /**
     * Fetch all specific Language entities
     *
     * @return List of Translation entity
     */
    List<Translation> findAllByLanguage(String language);

}
