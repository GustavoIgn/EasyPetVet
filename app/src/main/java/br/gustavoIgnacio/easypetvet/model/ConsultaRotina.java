package br.gustavoIgnacio.easypetvet.model;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import java.util.Date;
import java.util.List;

public class ConsultaRotina extends Consulta {
    private String recorrencia;
    private String proximaConsulta;
    private int animalId;

    public ConsultaRotina() {
        super();
    }

    public ConsultaRotina(Animal animal, String data, String descricao, String recorrencia, String proximaConsulta) {
        super(animal.getId(), animal, data, descricao); // Passando o animal corretamente
        this.recorrencia = recorrencia;
        this.proximaConsulta = proximaConsulta;
        this.animalId = animal.getId();
    }

    public String getRecorrencia() {
        return recorrencia;
    }

    public void setRecorrencia(String recorrencia) {
        this.recorrencia = recorrencia;
    }

    public String getProximaConsulta() {
        return proximaConsulta;
    }

    public void setProximaConsulta(String proximaConsulta) {
        this.proximaConsulta = proximaConsulta;
    }

    public String getTipo() {
        return "Rotina";
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public int getIdAnimal() {
        return animalId;
    }

     @ Override
    public String toString() {
        return getTipo() + 
		"| ID=" + getId() +
        "| ANIMAL-ID=" + getIdAnimal() +
        "| DATA=" + getData() +
        "| DESCRIÇÃO=" + getDescricao() +
        "| RECORRÊNCIA=" + recorrencia +
        "| PROXIMA-CONSULTA=" + proximaConsulta;
    }
}
