package br.gustavoIgnacio.easypetvet.controller;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import android.content.Context;
import br.gustavoIgnacio.easypetvet.model.Consulta;
import br.gustavoIgnacio.easypetvet.model. * ;
import br.gustavoIgnacio.easypetvet.persistencia.ConsultaRotinaDAO;
import android.database.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ConsultaRotinaController implements IConsultaController<ConsultaRotina> {

	
    private final ConsultaRotinaDAO consultaRotinaDAO;

	public ConsultaRotinaController(ConsultaRotinaDAO consultaRotinaDAO) {
		this.consultaRotinaDAO = consultaRotinaDAO;
	}

     @ Override
    public void insert(ConsultaRotina consulta)throws SQLException {
        consultaRotinaDAO.open();
        consultaRotinaDAO.insert(consulta);
        consultaRotinaDAO.close();
    }

     @ Override
    public int update(ConsultaRotina consulta)throws SQLException {
        if (consultaRotinaDAO.open() == null) {
            consultaRotinaDAO.open();
        }
        consultaRotinaDAO.update(consulta);
        consultaRotinaDAO.close();
        return 1;
    }

     @ Override
    public void delete (ConsultaRotina consulta)throws SQLException {
        if (consultaRotinaDAO.open() == null) {
            consultaRotinaDAO.open();
        }
        consultaRotinaDAO.delete(consulta);
        consultaRotinaDAO.close();
    }

     @ Override
    public ConsultaRotina findById(String id)throws SQLException {
        if (consultaRotinaDAO.open() == null) {
            consultaRotinaDAO.open();
        }
        return consultaRotinaDAO.findById(id);
    }

     @ Override
    public List < ConsultaRotina > findALL()throws SQLException {
        if (consultaRotinaDAO.open() == null) {
            consultaRotinaDAO.open();
        }

        return consultaRotinaDAO.findALL();
    }
	
	 @ Override
	public List<ConsultaRotina> findByAnimalId(int id) throws SQLException {
		if (consultaRotinaDAO.open() == null) {
			consultaRotinaDAO.open();
		}
		
		return consultaRotinaDAO.findByAnimalId(id);
	}
}
