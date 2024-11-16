package br.gustavoIgnacio.easypetvet.controller;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
*/

import android.database.SQLException;
import java.util.List;

public interface IConsultaController<T> {

	public void insert(T t) throws SQLException;
	public int update(T t) throws SQLException;
	public void delete(T t) throws SQLException;
	public T findById(String string) throws SQLException;
	public List<T> findALL() throws SQLException;
	public List<T> findByAnimalId(int valor) throws SQLException;
}