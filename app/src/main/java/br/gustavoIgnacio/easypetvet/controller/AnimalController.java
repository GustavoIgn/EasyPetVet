package br.gustavoIgnacio.easypetvet.controller;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import android.content.Context;
import br.gustavoIgnacio.easypetvet.model.Animal;
import br.gustavoIgnacio.easypetvet.persistencia.AnimalDAO;
import android.database.SQLException;
import java.util.List;
import java.util.ArrayList;

public class AnimalController {

    private final AnimalDAO animalDAO;

    public AnimalController(AnimalDAO animalDAO) {
        this.animalDAO = animalDAO;
    }

    public void insert(Animal animal)throws SQLException {
        animalDAO.open();
        animalDAO.insert(animal);
        animalDAO.close();
    }

    public int update(Animal animal)throws SQLException {
        if (animalDAO.open() == null) {
            animalDAO.open();
        }
        animalDAO.update(animal);
        animalDAO.close();
        return 1;
    }

    public void delete (Animal animal)throws SQLException {
        if (animalDAO.open() == null) {
            animalDAO.open();
        }
        animalDAO.delete(animal);
        animalDAO.close();
    }
    public Animal findById(String id)throws SQLException {
        if (animalDAO.open() == null) {
            animalDAO.open();
        }
        return animalDAO.findById(id);
    }

    public List < Animal > findALL()throws SQLException {
        if (animalDAO.open() == null) {
            animalDAO.open();
        }

        return animalDAO.findALL();
    }
}
