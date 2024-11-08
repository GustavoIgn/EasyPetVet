package br.gustavoIgnacio.easypetvet.controller;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
*/

import br.gustavoIgnacio.easypetvet.model.Animal;
import br.gustavoIgnacio.easypetvet.persistencia.AnimalDAO;
import java.util.List;

public class AnimalController {

    private AnimalDAO animalDAO;

    public AnimalController() {
        animalDAO = new AnimalDAO();
    }

    // Criar um novo animal
    public void criarAnimal(Animal animal) {
        animalDAO.inserirAnimal(animal);
    }

    // Atualizar informações de um animal
    public void atualizarAnimal(Animal animal) {
        animalDAO.atualizarAnimal(animal);
    }

    // Deletar um animal
    public void deletarAnimal(int id) {
        animalDAO.deletarAnimal(id);
    }

    // Obter lista de todos os animais
    public List<Animal> listarAnimais() {
        return animalDAO.obterTodosAnimais();
    }

    // Obter animal por ID
    public Animal obterAnimalPorId(int id) {
        return animalDAO.obterAnimalPorId(id);
    }
}
