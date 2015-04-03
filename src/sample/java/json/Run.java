package json;

import java.util.HashMap;

import json.demo.City;
import json.demo.Country;
import json.demo.IPGeo;
import json.demo.Subdivision;

public class Run {

    public static void main(String[] args) {
        JSONPool pool = new JSONPool();
        
        City city = new City();
        city.setConfidence(100);
        city.setName("Beijing");
        
        Country china = new Country();
        china.setConfidence(10);
        china.setIsoCode("CN");
        china.setInfos(new HashMap<String, Object>());
        china.getInfos().put("capital", city);
        china.getInfos().put("region-count", 32);

        IPGeo geo = new IPGeo();
        geo.setCity(city);
        geo.setCountry(china);
        
        Subdivision sub1 = new Subdivision();
        sub1.setConfidence(1);
        sub1.setIsoCode("JX");
        Subdivision sub2 = new Subdivision();
        sub2.setConfidence(2);
        sub2.setIsoCode("HN");
        geo.getSubdivisions().add(sub1);
        geo.getSubdivisions().add(sub2);
        
        geo.setOtherSubdivisions(new Subdivision[]{sub2, sub1});
        
        Country usa = new Country();
        usa.setConfidence(20);
        usa.setIsoCode("USA");
        geo.setRegisteredCountry(usa);

        System.out.println(pool.getJson(geo));
        
    }

}
