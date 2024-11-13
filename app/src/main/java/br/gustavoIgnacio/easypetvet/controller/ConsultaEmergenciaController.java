package br.gustavoIgnacio.easypetvet.controller;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import android.content.Context;
import br.gustavoIgnacio.easypetvet.model.Consulta;
import br.gustavoIgnacio.easypetvet.model. * ;
import br.gustavoIgnacio.easypetvet.persistencia.ConsultaDAO;
import android.database.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ConsultaEmergenciaController {

	/*

    private final ConsultaDAO consultaDAO;

    public ConsultaEmergenciaController(ConsultaDAO consultaDAO) {
        this.consultaDAO = consultaDAO;
    }

     @ Override
    public void insert(Consulta consulta)throws SQLException {
        consultaDAO.open();
        consultaDAO.insert(consulta);
        consultaDAO.close();
    }

     @ Override
    public int update(Consulta consulta)throws SQLException {
        if (consultaDAO.open() == null) {
            consultaDAO.open();
        }
        consultaDAO.update(consulta);
        consultaDAO.close();
        return 1;
    }

     @ Override
    public void delete (Consulta consulta)throws SQLException {
        if (consultaDAO.open() == null) {
            consultaDAO.open();
        }
        consultaDAO.delete(consulta);
        consultaDAO.close();
    }

     @ Override
    public Consulta findById(String id)throws SQLException {
        if (consultaDAO.open() == null) {
            consultaDAO.open();
        }
        return consultaDAO.findById(id);
    }

     @ Override
    public List < Consulta > findALL()throws SQLException {
        if (consultaDAO.open() == null) {
            consultaDAO.open();
        }

        return consultaDAO.findALL();
    } */
}
