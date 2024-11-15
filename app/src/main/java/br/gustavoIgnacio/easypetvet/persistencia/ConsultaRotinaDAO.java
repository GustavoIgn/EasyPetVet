package br.gustavoIgnacio.easypetvet.persistencia;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.gustavoIgnacio.easypetvet.model.ConsultaRotina;
import br.gustavoIgnacio.easypetvet.model.Animal;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRotinaDAO implements ICRUDDao<ConsultaRotina> {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private final Context context;

    public ConsultaRotinaDAO(Context context) {
        this.context = context;
    }

    public ConsultaRotinaDAO open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

	@Override
    public void insert(ConsultaRotina consulta) throws SQLException {
        ContentValues contentValues = getContentValues(consulta);
        db.insert(DatabaseHelper.TABELA_CONSULTA_ROTINA, null, contentValues);
    }

	@Override
    public int update(ConsultaRotina consulta) throws SQLException {
        ContentValues contentValues = getContentValues(consulta);
        int ret = db.update(DatabaseHelper.TABELA_CONSULTA_ROTINA, contentValues, "id = ?", new String[]{String.valueOf(consulta.getId())});
		return ret;
    }
	
	@Override
    public void delete(ConsultaRotina consulta) throws SQLException {
		ContentValues contentValues = getContentValues(consulta);
		db.delete(DatabaseHelper.TABELA_CONSULTA_ROTINA, "id = ?", new String[]{String.valueOf(consulta.getId())});
    }

	@Override
    public ConsultaRotina findById(String id) throws SQLException {
        ConsultaRotina consulta = null;
		Cursor cursor = db.query(DatabaseHelper.TABELA_CONSULTA_ROTINA, null, "id = ?", new String[]{id}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            consulta = cursorToConsulta(cursor);
            cursor.close();
        }

        return consulta;
    }

	@Override
    public List<ConsultaRotina> findALL() throws SQLException {
        List<ConsultaRotina> consultas = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABELA_CONSULTA_ROTINA, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                ConsultaRotina consulta = cursorToConsulta(cursor);
                consultas.add(consulta);
            }
            cursor.close();
        }

        return consultas;
    }

    private ConsultaRotina cursorToConsulta(Cursor cursor) {
        ConsultaRotina consulta = new ConsultaRotina();
        consulta.setId(cursor.getInt(cursor.getColumnIndex("id")));
        consulta.setAnimalId(cursor.getInt(cursor.getColumnIndex("animal_id")));
        consulta.setData(cursor.getString(cursor.getColumnIndex("data")));
        consulta.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
        consulta.setRecorrencia(cursor.getString(cursor.getColumnIndex("recorrencia")));
        consulta.setProximaConsulta(cursor.getString(cursor.getColumnIndex("proxima_consulta")));
        return consulta;
    }

    private ContentValues getContentValues(ConsultaRotina consulta) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("animal_id", consulta.getIdAnimal());
        contentValues.put("data", consulta.getData());
        contentValues.put("descricao", consulta.getDescricao());
		contentValues.put("tipo_consulta", 2); // Tipo "2" para consulta de rotina
        contentValues.put("recorrencia", consulta.getRecorrencia());
        contentValues.put("proxima_consulta", consulta.getProximaConsulta());
        
        return contentValues;
    }
}