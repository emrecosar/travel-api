package com.api.travel.data.repository;

import com.api.travel.data.entity.Translation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TranslationRepositoryTest {

    @Autowired
    TranslationRepository translationRepository;

    @Test
    public void findDistinctLanguages_returnResults() {
        List<String> translations = translationRepository.findAllDistinctLanguages();
        assertFalse(translations.isEmpty());
    }

    @Test
    public void findAllByLanguage_returnResults() {
        List<Translation> translations = translationRepository.findAllByLanguage("NL");
        assertFalse(translations.isEmpty());
    }

    @Test
    public void findAllByLanguage_returnEmpty() {
        List<Translation> translations = translationRepository.findAllByLanguage("AA");
        assertTrue(translations.isEmpty());
    }
}