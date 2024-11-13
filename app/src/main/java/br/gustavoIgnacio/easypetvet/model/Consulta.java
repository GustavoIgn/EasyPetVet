package br.gustavoIgnacio.easypetvet.model;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import java.util.Date;

public abstract class Consulta implements INotificavel {
    private int id;
    private Animal animal;
    private Date data;
    private String descricao;

    public Consulta(int id, Animal animal, Date data, String descricao) {
        this.id = id;
        this.animal = animal;
        this.data = data;
        this.descricao = descricao;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

     @ Override
    public String toString() {
        return "Consulta{id=" + id + ", animal=" + animal.getNome() + ", data=" + data + ", descricao='" + descricao + "'}";
    }

     @ Override
    public abstract void notificar(String mensagem);
}
