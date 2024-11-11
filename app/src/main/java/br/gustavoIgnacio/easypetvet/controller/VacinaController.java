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
}
