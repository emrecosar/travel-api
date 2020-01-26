package com.api.travel.data.repository;

import com.api.travel.data.entity.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LocationRepositoryTest {

    @Autowired
    LocationRepository locationRepository;

    @Test
    public void findByTypeAndCode_ReturnsResult() {
        List<Location> locations = locationRepository.findAllByLanguageAndTypeAndCode("NL", "airport", "US");
        assertFalse(locations.isEmpty());
        assertEquals("airport", locations.get(0).getType());
    }

    @Test
    public void findByTypeAndCode_emptyResult() {
        List<Location> locations = locationRepository.findAllByLanguageAndTypeAndCode("TR", "airport", "US");
        assertTrue(locations.isEmpty());
    }

    @Test
    public void findByTypeAndCode_brokenInput() {
        List<Location> locations = locationRepository.findAllByLanguageAndTypeAndCode("NL", "region", "US");
        assertTrue(locations.isEmpty());
    }


}