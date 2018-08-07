package org.opensrp.stock.openlmis.rest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class LocationResourceTest extends BaseResourceTest {

    @Autowired
    LocationResource locationResource;

    private final static String BASE_URL = "/rest/locations/";

    @Test
    public void testGetLocationHierarchyByIdShouldGetLocationHierarchy() throws Exception {
        assertNotNull(getResponseAsString(BASE_URL + "25089a50-0cf0-47e8-8bfe-fecabed92530", null, status().isOk()));
    }

    @Test
    public void testGetLocationHierarchyByNameShouldGetLocationHierarchy() throws Exception {
        assertNotNull(getResponseAsString(BASE_URL + "Fort Jameson", null, status().isOk()));
    }
}
