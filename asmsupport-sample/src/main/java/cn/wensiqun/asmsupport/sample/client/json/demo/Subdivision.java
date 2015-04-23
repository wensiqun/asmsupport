package cn.wensiqun.asmsupport.sample.client.json.demo;


/**
 * Contains data for the subdivisions associated with an IP address.
 *
 * This record is returned by all the end points except the Country end point.
 */
final public class Subdivision {
    
    private Integer confidence;

    private String isoCode;

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