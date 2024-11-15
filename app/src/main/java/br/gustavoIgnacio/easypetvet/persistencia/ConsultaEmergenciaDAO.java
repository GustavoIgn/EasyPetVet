package br.gustavoIgnacio.easypetvet.persistencia;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
*/


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.gustavoIgnacio.easypetvet.model.ConsultaEmergencia;
import br.gustavoIgnacio.easypetvet.model.Animal;
import java.util.ArrayList;
import java.util.List;

public class ConsultaEmergenciaDAO implements ICRUDDao<ConsultaEmergencia> {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private final Context context;

    public ConsultaEmergenciaDAO(Context context) {
        this.context = context;
    }

    public ConsultaEmergenciaDAO open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

	@Override
    public void insert(ConsultaEmergencia consulta) throws SQLException {
        ContentValues contentValues = getContentValues(consulta);
        db.insert(DatabaseHelper.TABELA_CONSULTA_EMERGENCIA, null, contentValues);
    }
	
	@Override
    public int update(ConsultaEmergencia consulta) throws SQLException {
        ContentValues contentValues = getContentValues(consulta);
        return db.update(DatabaseHelper.TABELA_CONSULTA_EMERGENCIA, contentValues, "id = ?", new String[]{
            String.valueOf(consulta.getId())
        });
    }

	@Override
    public void delete(ConsultaEmergencia consulta) throws SQLException {
        db.delete(DatabaseHelper.TABELA_CONSULTA_EMERGENCIA, "id = ?", new String[]{
            String.valueOf(consulta.getId())
        });
    }

	@Override
    public ConsultaEmergencia findById(String id) throws SQLException {
        ConsultaEmergencia consulta = null;
        Cursor cursor = db.query(DatabaseHelper.TABELA_CONSULTA_EMERGENCIA, null, "id = ?", new String[]{id}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            consulta = cursorToConsulta(cursor);
            cursor.close();
        }

        return consulta;
    }

	@Override
    public List<ConsultaEmergencia> findALL() throws SQLException {
        List<ConsultaEmergencia> consultas = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABELA_CONSULTA_EMERGENCIA, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                ConsultaEmergencia consulta = cursorToConsulta(cursor);
                consultas.add(consulta);
            }
            cursor.close();
        }

        return consultas;
    }

    private ConsultaEmergencia cursorToConsulta(Cursor cursor) {
        ConsultaEmergencia consulta = new ConsultaEmergencia();
        consulta.setId(cursor.getInt(cursor.getColumnIndex("id")));
        consulta.setAnimalId(cursor.getInt(cursor.getColumnIndex("animal_id")));
        consulta.setData(cursor.getString(cursor.getColumnIndex("data")));
        consulta.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
        consulta.setPrioridade(cursor.getString(cursor.getColumnIndex("prioridade")));
        consulta.setTempoEstimadoAtendimento(cursor.getInt(cursor.getColumnIndex("tempo_estimado_atendimento")));
        return consulta;
    }

    private ContentValues getContentValues(ConsultaEmergencia consulta) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("animal_id", consulta.getIdAnimal());
        contentValues.put("data", consulta.getData());
        contentValues.put("descricao", consulta.getDescricao());
		contentValues.put("tipo_consulta", 1); // Tipo "1" para consulta de emergência
        contentValues.put("prioridade", consulta.getPrioridade());
        contentValues.put("tempo_estimado_atendimento", consulta.getTempoEstimadoAtendimento());
        
        return contentValues;
    }
}