package dao;
import domain.Flat;

interface FlatDAO {

    void addFlat(Flat flat);
    Flat getFlat(String FlatID);
    void removeFlat(Flat flat);
}
