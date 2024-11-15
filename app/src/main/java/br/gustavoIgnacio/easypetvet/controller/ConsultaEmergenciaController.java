package br.gustavoIgnacio.easypetvet.controller;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import android.content.Context;
import br.gustavoIgnacio.easypetvet.model.ConsultaEmergencia;
import br.gustavoIgnacio.easypetvet.model. * ;
import br.gustavoIgnacio.easypetvet.persistencia.ConsultaEmergenciaDAO;
import android.database.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ConsultaEmergenciaController implements IConsultaController<ConsultaEmergencia> {


    private final ConsultaEmergenciaDAO consultaEmergenciaDAO;

	public ConsultaEmergenciaController(ConsultaEmergenciaDAO consultaEDAO) {
		this.consultaEmergenciaDAO = consultaEDAO;
	}

     @ Override
    public void insert(ConsultaEmergencia consulta)throws SQLException {
        consultaEmergenciaDAO.open();
        consultaEmergenciaDAO.insert(consulta);
        consultaEmergenciaDAO.close();
    }

     @ Override
    public int update(ConsultaEmergencia consulta)throws SQLException {
        if (consultaEmergenciaDAO.open() == null) {
            consultaEmergenciaDAO.open();
        }
        consultaEmergenciaDAO.update(consulta);
        consultaEmergenciaDAO.close();
        return 1;
    }

     @ Override
    public void delete (ConsultaEmergencia consulta)throws SQLException {
        if (consultaEmergenciaDAO.open() == null) {
            consultaEmergenciaDAO.open();
        }
        consultaEmergenciaDAO.delete(consulta);
        consultaEmergenciaDAO.close();
    }

     @ Override
    public ConsultaEmergencia findById(String id)throws SQLException {
        if (consultaEmergenciaDAO.open() == null) {
            consultaEmergenciaDAO.open();
        }
        return consultaEmergenciaDAO.findById(id);
    }

     @ Override
    public List < ConsultaEmergencia > findALL()throws SQLException {
        if (consultaEmergenciaDAO.open() == null) {
            consultaEmergenciaDAO.open();
        }

        return consultaEmergenciaDAO.findALL();
    }
}
