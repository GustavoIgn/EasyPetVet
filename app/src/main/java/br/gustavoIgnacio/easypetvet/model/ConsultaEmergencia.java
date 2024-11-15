package br.gustavoIgnacio.easypetvet.model;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import java.util.Date;

public class ConsultaEmergencia extends Consulta {
    private String prioridade;
    private int tempoEstimadoAtendimento;
    private int animalId;

    public ConsultaEmergencia() {
        super();
    }

    public ConsultaEmergencia(Animal animal, String data, String descricao, String prioridade, int tempoEstimadoAtendimento) {
        super(animal.getId(), animal, data, descricao);
        this.prioridade = prioridade;
        this.tempoEstimadoAtendimento = tempoEstimadoAtendimento;
        this.animalId = animal.getId();
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public int getTempoEstimadoAtendimento() {
        return tempoEstimadoAtendimento;
    }

    public void setTempoEstimadoAtendimento(int tempoEstimadoAtendimento) {
        this.tempoEstimadoAtendimento = tempoEstimadoAtendimento;
    }

    public String getTipo() {
        return "Emergencia";
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
        "| ANIMAL_ID=" + getIdAnimal() +
        "| DATA=" + getData() +
        "| DESCRIÇÃO=" + getDescricao() +
        "| PRIORIDADE=" + prioridade +
        "| TEMPO_ESTIMADO=" + tempoEstimadoAtendimento + "h";
    }
}
