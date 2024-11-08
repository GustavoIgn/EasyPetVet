package br.gustavoIgnacio.easypetvet.controller;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
*/

import br.gustavoIgnacio.easypetvet.model.Vacina;
import br.gustavoIgnacio.easypetvet.persistencia.VacinaDAO;
import java.util.List;

public class VacinaController {

    private VacinaDAO vacinaDAO;

    public VacinaController() {
        vacinaDAO = new VacinaDAO();
    }

    // Criar nova vacina
    public void criarVacina(Vacina vacina) {
        vacinaDAO.inserirVacina(vacina);
    }

    // Obter lista de todas as vacinas
    public List<Vacina> listarVacinas() {
        return vacinaDAO.obterTodasVacinas();
    }

    // Notificar sobre vacina
    public void notificarVacina(Vacina vacina) {
        vacina.notificar("A vacina de " + vacina.getAnimal().getNome() + " está agendada para " + vacina.getDataAplicacao());
    }
}
