package br.gustavoIgnacio.easypetvet.model;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
*/

import java.util.Date;

public class ConsultaEmergencia extends Consulta {

    public ConsultaEmergencia(int id, Animal animal, Date data, String descricao) {
        super(id, animal, data, descricao);
    }

    @Override
    public void notificar(String mensagem) {
        System.out.println("Notificação de Consulta de Emergência: " + mensagem);
    }

    @Override
    public String toString() {
        return "ConsultaEmergencia{" + super.toString() + "}";
    }
}
