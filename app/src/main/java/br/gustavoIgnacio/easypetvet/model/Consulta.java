package br.gustavoIgnacio.easypetvet.model;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import java.util.Date;

public abstract class Consulta {
    private int id;
    private Animal animal;
    private String data;
    private String descricao;
	private int animalId;
	
	public Consulta(){}

    public Consulta(int id, Animal animal, String data, String descricao) {
        this.id = id;
        this.animal = animal;
        this.data = data;
        this.descricao = descricao;
		this.animalId = animal.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
	
	public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public int getIdAnimal() {
        return animalId;
    }

    @Override
    public String toString() {
        return "Consulta{id=" + id + ", animal=" + animal.getNome() + ", data='" + data + "', descricao='" + descricao + "'}";
    }

}
