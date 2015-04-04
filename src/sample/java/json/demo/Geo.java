package json.demo;

import java.util.ArrayList;


public class Geo {
	

    private Country country = new Country();

    private Country registeredCountry = new Country();

    private City city = new City();
    
    private ArrayList<Subdivision> subdivisions = new ArrayList<Subdivision>();
    
    private Subdivision[] otherSubdivisions;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Country getRegisteredCountry() {
        return registeredCountry;
    }

    public void setRegisteredCountry(Country registeredCountry) {
        this.registeredCountry = registeredCountry;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public ArrayList<Subdivision> getSubdivisions() {
        return subdivisions;
    }

    public void setSubdivisions(ArrayList<Subdivision> subdivisions) {
        this.subdivisions = subdivisions;
    }

    public Subdivision[] getOtherSubdivisions() {
        return otherSubdivisions;
    }

    public void setOtherSubdivisions(Subdivision[] otherSubdivisions) {
        this.otherSubdivisions = otherSubdivisions;
    }
    
}
