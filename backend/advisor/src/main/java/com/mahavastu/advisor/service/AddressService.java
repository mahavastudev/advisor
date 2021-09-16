package com.mahavastu.advisor.service;

import java.util.List;

import com.mahavastu.advisor.model.Country;
import com.mahavastu.advisor.model.State;

public interface AddressService
{
    List<Country> getAllCountries();
    List<State> getAllStatesByCountryCode(String countryCode);
}
