package br.gustavoIgnacio.easypetvet.model;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
*/

import java.util.Date;

public class Vacina implements Notificavel {
    private int id;
    private Animal animal;
    private String nome;
    private Date dataAplicacao;
    private Date proximaAplicacao;

    public Vacina(int id, Animal animal, String nome, Date dataAplicacao, Date proximaAplicacao) {
        this.id = id;
        this.animal = animal;
        this.nome = nome;
        this.dataAplicacao = dataAplicacao;
        this.proximaAplicacao = proximaAplicacao;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(Date dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public Date getProximaAplicacao() {
        return proximaAplicacao;
    }

    public void setProximaAplicacao(Date proximaAplicacao) {
        this.proximaAplicacao = proximaAplicacao;
    }

    @Override
    public String toString() {
        return "Vacina{id=" + id + ", animal=" + animal.getNome() + ", nome='" + nome + "', dataAplicacao=" + dataAplicacao + ", proximaAplicacao=" + proximaAplicacao + "}";
    }

    @Override
    public void notificar(String mensagem) {
        System.out.println("Notificação de Vacina: " + mensagem);
    }
}
