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
    private String name;

    public Flat() {
    }

    public Flat(String flatID, String address, String host, String name) {
        this.flatID = flatID;
        this.address = address;
        this.host = host;
        this.name = name;
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

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flat flat = (Flat) o;
        return Objects.equals(flatID, flat.flatID) && Objects.equals(address, flat.address) && Objects.equals(host, flat.host) && Objects.equals(name,flat.name);
    }
}