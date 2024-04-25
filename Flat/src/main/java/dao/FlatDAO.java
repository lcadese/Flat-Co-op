package dao;
import domain.Flat;

public interface FlatDAO {

    void addFlat(Flat flat);
    Flat getFlat(String FlatID);
    void removeFlat(Flat flat);
    void removeFlat(String flatID);
}
