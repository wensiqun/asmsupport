package json.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import json.JSONPool;

public class Runner {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF_8"));
        printPrompt(in);
        
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

        System.out.println("Country infos is          : " + pool.getJson(china.getInfos()));
        System.out.println("Country json is           : " + pool.getJson(china));
        printPrompt(in);
        
        Geo geo = new Geo();
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

        System.out.println("Subdivisions json is      : " + pool.getJson(geo.getSubdivisions()));
        
        geo.setOtherSubdivisions(new Subdivision[]{sub2, sub1});

        System.out.println("OtherSubdivisions json is : " + pool.getJson(geo.getOtherSubdivisions()));
        System.out.println("Success generate json.demo.Subdivision.class Json Generator.");
        printPrompt(in);
        
        Country usa = new Country();
        usa.setConfidence(20);
        usa.setIsoCode("USA");
        geo.setRegisteredCountry(usa);

        System.out.println("Geo json is               : " + pool.getJson(geo));
        System.out.println("Success generate json.demo.Geo.class Json Generator.");
        printPrompt(in);
        
    }
    
    public static void printPrompt(BufferedReader in) throws IOException {
        System.out.println("##################################################");
        System.out.println("# 1. Entry 'gc' to do System.gc and continue...  #");
        System.out.println("# 2. Entry other just continue do next generate  #");
        System.out.println("##################################################");
        String command = in.readLine();
        if("gc".equals(command)) {
        	System.gc();
        }
    }

}
