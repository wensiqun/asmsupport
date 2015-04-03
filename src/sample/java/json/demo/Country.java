package json.demo;

import java.util.Map;


/**
 * Contains data for the country record associated with an IP address.
 *
 * This record is returned by all the end points.
 */
public class Country {

    private Integer confidence;

    private String isoCode;
    
    private Map<String, Object> infos;
    
    public Map<String, Object> getInfos() {
        return infos;
    }

    public void setInfos(Map<String, Object> infos) {
        this.infos = infos;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

}
