/**
 * Created by hgoscenski on 5/6/17.
 */
public class JsonData {
    private String accessOnShort;
    private String browser;
    private String country;
    private String hostname;
    private String ipAddress;
    private String os;
    private String referrer;
    private String targetName;
    private String userAgent;

    public JsonData(String accessOnShort, String browser, String country, String hostname, String ipAddress, String os, String referrer, String targetName, String userAgent) {
        this.accessOnShort = accessOnShort;
        this.browser = browser;
        this.country = country;
        this.hostname = hostname;
        this.ipAddress = ipAddress;
        this.os = os;
        this.referrer = referrer;
        this.targetName = targetName;
        this.userAgent = userAgent;
    }

    public String getAccessOnShort() {
        return accessOnShort;
    }

    public void setAccessOnShort(String accessOnShort) {
        this.accessOnShort = accessOnShort;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
