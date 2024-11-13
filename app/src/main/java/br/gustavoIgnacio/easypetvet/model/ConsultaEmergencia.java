package br.gustavoIgnacio.easypetvet.model;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import java.util.Date;

public class ConsultaEmergencia extends Consulta {
    private String prioridade;
    private int tempoEstimadoAtendimento;

    public ConsultaEmergencia(int id, Animal animal, Date data, String descricao, String prioridade, int tempoEstimadoAtendimento) {
        super(id, animal, data, descricao);
        this.prioridade = prioridade;
        this.tempoEstimadoAtendimento = tempoEstimadoAtendimento;
    }

    @Override
    public void notificar(String mensagem) {
        System.out.println("Notificação de Consulta de Emergência: " + mensagem);
    }

    @Override
    public String toString() {
        return "ConsultaEmergencia{" +
                "prioridade='" + prioridade + '\'' +
                ", tempoEstimadoAtendimento=" + tempoEstimadoAtendimento +
                ", " + super.toString() +
                '}';
    }

    // Getters e Setters para os novos atributos
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
}

