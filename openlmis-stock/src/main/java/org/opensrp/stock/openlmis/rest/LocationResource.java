package org.opensrp.stock.openlmis.rest;

import org.json.JSONException;
import org.opensrp.stock.openlmis.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/rest/locations")
public class LocationResource {

    @Autowired
    private LocationService locationService;

    public static final String LOCATION_ID = "id";

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    protected String getLocationHeirarchy(@PathVariable String id) throws JSONException {
        return locationService.getLocationHeirarchy(id);
    }
}
