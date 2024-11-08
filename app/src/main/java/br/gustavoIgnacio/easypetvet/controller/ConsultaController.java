package br.gustavoIgnacio.easypetvet.controller;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
*/

import br.gustavoIgnacio.easypetvet.model.Consulta;
import br.gustavoIgnacio.easypetvet.model.ConsultaEmergencia;
import br.gustavoIgnacio.easypetvet.model.ConsultaRotina;
import br.gustavoIgnacio.easypetvet.persistencia.ConsultaDAO;
import java.util.List;

public class ConsultaController {

    private ConsultaDAO consultaDAO;

    public ConsultaController() {
        consultaDAO = new ConsultaDAO();
    }

    // Criar nova consulta
    public void criarConsulta(Consulta consulta) {
        consultaDAO.inserirConsulta(consulta);
    }

    // Obter lista de todas as consultas
    public List<Consulta> listarConsultas() {
        return consultaDAO.obterTodasConsultas();
    }

    // Notificar sobre consulta
    public void notificarConsulta(Consulta consulta) {
        consulta.notificar("Lembre-se, a consulta de " + consulta.getAnimal().getNome() + " está agendada para " + consulta.getData());
    }
}
