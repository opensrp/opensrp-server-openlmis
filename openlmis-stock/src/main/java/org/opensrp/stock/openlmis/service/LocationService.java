package org.opensrp.stock.openlmis.service;

import com.google.gson.Gson;
import org.json.JSONException;
import org.opensrp.stock.openlmis.service.openmrs.OpenmrsLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    private OpenmrsLocationService locationService;

    public String getLocationHeirarchy(String locationIdOrName) throws JSONException {
        return new Gson().toJson(locationService.getLocationTreeOf(locationIdOrName));
    }

    public String getLocationHeirarchy() throws JSONException {
        return new Gson().toJson(locationService.getLocationTree());
    }
}