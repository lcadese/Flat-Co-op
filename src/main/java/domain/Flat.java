package domain;

import java.util.Objects;

/**
 *
 * @author haydenaish
 */
public class Flat {
    private String flatID;
    private String address;
    private String host;

    public Flat() {
    }

    public Flat(String flatID, String address, String host) {
        this.flatID = flatID;
        this.address = address;
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flat flat = (Flat) o;
        return Objects.equals(flatID, flat.flatID) && Objects.equals(address, flat.address) && Objects.equals(host, flat.host);
    }
}