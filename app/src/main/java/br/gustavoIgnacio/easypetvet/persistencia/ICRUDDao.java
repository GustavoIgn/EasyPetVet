package br.gustavoIgnacio.easypetvet.persistencia;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
*/

import android.database.SQLException;
import java.util.List;

public interface ICRUDDao<T> {

	public void insert(T t) throws SQLException;
	public int update(T t) throws SQLException;
	public void delete(T t) throws SQLException;
	public T findByCPF(T t) throws SQLException;
	public List<T> findALL() throws SQLException;
}