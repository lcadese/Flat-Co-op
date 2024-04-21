package domain;
/**
 *
 * @author haydenaish
 */
public class Flat {
    private String flatID;
    private String address;
    private String name;
    private String host;

    public Flat() {
    }

    public Flat(String flatID, String address, String name, String host) {
        this.flatID = flatID;
        this.address = address;
        this.name = name;
        this.host = host;
    }

    public String getflatID() {
        return flatID;
    }

    public void setflatID(String flatID) {
        this.flatID = flatID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}