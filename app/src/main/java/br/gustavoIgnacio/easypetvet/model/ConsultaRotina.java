package br.gustavoIgnacio.easypetvet.model;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import java.util.Date;
import java.util.List;

public class ConsultaRotina extends Consulta {
    private String recorrencia;
    private Date proximaConsulta;

    public ConsultaRotina(int id, Animal animal, Date data, String descricao, String recorrencia, Date proximaConsulta) {
        super(id, animal, data, descricao);
        this.recorrencia = recorrencia;
        this.proximaConsulta = proximaConsulta;
    }

    @Override
    public void notificar(String mensagem) {
        System.out.println("Notificação de Consulta de Rotina: " + mensagem);
    }

    @Override
    public String toString() {
        return "ConsultaRotina{" +
                "recorrencia='" + recorrencia + '\'' +
                ", proximaConsulta=" + proximaConsulta +
                ", " + super.toString() +
                '}';
    }

    // Getters e Setters para os novos atributos
    public String getRecorrencia() {
        return recorrencia;
    }

    public void setRecorrencia(String recorrencia) {
        this.recorrencia = recorrencia;
    }

    public Date getProximaConsulta() {
        return proximaConsulta;
    }

    public void setProximaConsulta(Date proximaConsulta) {
        this.proximaConsulta = proximaConsulta;
    }
	
	public String getTipo() {
		return "Rotina";
	}
}
