package com.mahavastu.advisor.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahavastu.advisor.entity.CountryEntity;
import com.mahavastu.advisor.entity.StateEntity;
import com.mahavastu.advisor.entity.converter.Converter;
import com.mahavastu.advisor.model.Country;
import com.mahavastu.advisor.model.State;
import com.mahavastu.advisor.repository.CountryRepository;
import com.mahavastu.advisor.repository.StateRepository;

@Service
public class AddressServiceImpl implements AddressService
{

    @Autowired
    private CountryRepository countryRepository;
    
    @Autowired
    private StateRepository stateRepository;

    @Override
    public List<Country> getAllCountries()
    {
        List<CountryEntity> countryEntities = countryRepository.findAll();
        List<Country> countries = Converter.getCountriesFromCoutryEntities(countryEntities);
        Collections.sort(countries, (c1, c2) -> c1.getName().compareTo(c2.getName()));
        return countries;
    }

    @Override
    public List<State> getAllStatesByCountryCode(String countryCode)
    {
        List<StateEntity> stateEntities = stateRepository.findByCounCode(countryCode);
        List<State> states = Converter.getStateFromStateEntities(stateEntities);
        Collections.sort(states, (c1, c2) -> c1.getName().compareTo(c2.getName()));
        return states;
    }

}
