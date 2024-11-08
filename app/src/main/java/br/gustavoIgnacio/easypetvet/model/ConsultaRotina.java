package br.gustavoIgnacio.easypetvet.model;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
*/

public class ConsultaRotina extends Consulta {

    public ConsultaRotina(int id, Animal animal, Date data, String descricao) {
        super(id, animal, data, descricao);
    }

    @Override
    public void notificar(String mensagem) {
        System.out.println("Notificação de Consulta de Rotina: " + mensagem);
    }

    @Override
    public String toString() {
        return "ConsultaRotina{" + super.toString() + "}";
    }
}
