package com.api.travel.api.resource;

import com.api.travel.api.model.LocationModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Api Integration Test for Resource Location
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationsResourceIT {

    @Autowired
    TestRestTemplate authorizedRestTemplate;

    @Autowired
    TestRestTemplate unauthorizedRestTemplate;

    @Value("${app.locations.path}")
    String basePath;

    @Before
    public void setup() {
        authorizedRestTemplate = authorizedRestTemplate.withBasicAuth("someuser", "psw");
    }

    @Test
    public void getLocationsWithoutAuth_Return401() {
        ResponseEntity<Object> response =
                unauthorizedRestTemplate.exchange(basePath,
                        HttpMethod.GET,
                        createEntity(createHeader()),
                        Object.class);

        // application will return HTTP 401
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void getLocationsWithoutLanguage_then_Return200() {
        ResponseEntity<LocationModel[]> response =
                authorizedRestTemplate.exchange(basePath,
                        HttpMethod.GET,
                        createEntity(createHeader()),
                        LocationModel[].class);

        // no header for language, so locations return with default language EN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    public void getLocationsForLanguage_NL_then_Return200() {
        ResponseEntity<LocationModel[]> response =
                authorizedRestTemplate.exchange(basePath,
                        HttpMethod.GET,
                        createHeaderWithLanguage("NL"),
                        LocationModel[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    public void getLocationsForLanguage_TR_then_return404() {
        ResponseEntity<LocationModel[]> response =
                authorizedRestTemplate.exchange(basePath,
                        HttpMethod.GET,
                        createHeaderWithLanguage("TR"),
                        LocationModel[].class);

        // TR is not supported by the app, so locations return with default language EN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    public void getLocationByType_city_AndCode_NL_AndWithoutLanguage() {
        ResponseEntity<LocationModel[]> response =
                authorizedRestTemplate.exchange(basePath + "/city/NL",
                        HttpMethod.GET,
                        createEntity(createHeader()),
                        LocationModel[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    public void getLocationByType_airport_AndCode_US_AndLanguage_NL() {
        ResponseEntity<LocationModel[]> response =
                authorizedRestTemplate.exchange(basePath + "/airport/NL",
                        HttpMethod.GET,
                        createHeaderWithLanguage("NL"),
                        LocationModel[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    public void getLocationByType_airport_AndCode_US_AndLanguage_EN() {
        ResponseEntity<LocationModel[]> response =
                authorizedRestTemplate.exchange(basePath + "/airport/TR",
                        HttpMethod.GET,
                        createHeaderWithLanguage("NL"),
                        LocationModel[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    public void getLocationByType_airport_AndCode_US_AndLanguage_TR() {
        ResponseEntity<LocationModel[]> response =
                authorizedRestTemplate.exchange(basePath + "/airport/TR",
                        HttpMethod.GET,
                        createHeaderWithLanguage("TR"),
                        LocationModel[].class);

        // TR is not supported by the app due to current data, so locations return with default language EN
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.hasBody());
    }

    /**
     * For assignment, "Create a client"
     * - Implement a client to consume the API.
     * - Use the client to retrieve and print all airports from the USA (country code is 'US')
     */
    @Test
    public void getAllLocationsByType_airport_AndCode_US_forLanguage_EN() {
        ResponseEntity<LocationModel[]> response =
                authorizedRestTemplate.exchange(basePath + "/airport/US",
                        HttpMethod.GET,
                        createHeaderWithLanguage("EN"),
                        LocationModel[].class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.hasBody());
    }

    @Test
    public void getAllLocationsByType_airport_AndCode_US_forLanguage_NL() {
        ResponseEntity<LocationModel[]> response =
                authorizedRestTemplate.exchange(basePath + "/airport/US",
                        HttpMethod.GET,
                        createHeaderWithLanguage("NL"),
                        LocationModel[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        for (LocationModel location : response.getBody()) {
            System.out.println(location);
        }
    }

    @Test
    public void getAllLocationsByType_airport_AndCode_US_withoutLanguage() {
        ResponseEntity<LocationModel[]> response =
                authorizedRestTemplate.exchange(basePath + "/airport/US",
                        HttpMethod.GET,
                        createEntity(createHeader()),
                        LocationModel[].class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.hasBody());
    }

    public HttpEntity createHeaderWithLanguage(String value) {
        HttpHeaders httpHeaders = createHeader();
        httpHeaders.add(HttpHeaders.ACCEPT_LANGUAGE, value);
        return createEntity(httpHeaders);
    }

    public HttpHeaders createHeader() {
        return new HttpHeaders();
    }

    @SuppressWarnings("unchecked")
    public HttpEntity createEntity(HttpHeaders httpHeaders) {
        return new HttpEntity(httpHeaders);
    }
}