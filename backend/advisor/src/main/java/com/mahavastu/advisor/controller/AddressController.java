package com.mahavastu.advisor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahavastu.advisor.model.Country;
import com.mahavastu.advisor.model.State;
import com.mahavastu.advisor.service.AddressService;

@RestController
@CrossOrigin(origins = {
        "http://localhost:4200", "http://horo3.mahavastu.com:8080"
})
@RequestMapping("address")
public class AddressController
{
    @Autowired
    private AddressService addressService;
    
    @GetMapping("/all-countries")
    public List<Country> getAllCountries()
    {
        return addressService.getAllCountries();
    }
    
    @GetMapping("/all-states/{country-code}")
    public List<State> getAllCountries(@PathVariable("country-code") String countryCode)
    {
        return addressService.getAllStatesByCountryCode(countryCode);
    }
}
