package br.gustavoIgnacio.easypetvet.persistencia;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.gustavoIgnacio.easypetvet.model.Consulta;
import br.gustavoIgnacio.easypetvet.model.ConsultaEmergencia;
import br.gustavoIgnacio.easypetvet.model.ConsultaRotina;
import br.gustavoIgnacio.easypetvet.model.Animal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsultaDAO {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;

    public ConsultaDAO() {}

    public ConsultaDAO(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    // Inserir nova consulta
    public void inserirConsulta(Consulta consulta) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("animal_id", consulta.getAnimal().getId());
        values.put("data", consulta.getData().toString());  // Convertendo a data para String
        values.put("descricao", consulta.getDescricao());

        db.insert(DatabaseHelper.TABELA_CONSULTA, null, values);
    }

    // Obter todas as consultas
    public List<Consulta> obterTodasConsultas() {
        List<Consulta> consultas = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABELA_CONSULTA, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                // Obter os dados do animal associado à consulta
                int animalId = cursor.getInt(cursor.getColumnIndex("animal_id"));
                AnimalDAO animalDAO = new AnimalDAO(context);
                Animal animal = animalDAO.obterAnimalPorId(animalId);

                String descricao = cursor.getString(cursor.getColumnIndex("descricao"));
                String dataStr = cursor.getString(cursor.getColumnIndex("data"));
                // Parse para Date (implementação simples para exemplo)
                Date data = new Date(dataStr);

                // Instanciar a consulta conforme o tipo (rotina ou emergência)
                Consulta consulta;
                if (descricao.contains("emergência")) {
                    consulta = new ConsultaEmergencia(cursor.getInt(cursor.getColumnIndex("id")), animal, data, descricao);
                } else {
                    consulta = new ConsultaRotina(cursor.getInt(cursor.getColumnIndex("id")), animal, data, descricao);
                }

                consultas.add(consulta);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return consultas;
    }

    // Obter consulta por ID
    public Consulta obterConsultaPorId(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABELA_CONSULTA, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int animalId = cursor.getInt(cursor.getColumnIndex("animal_id"));
            AnimalDAO animalDAO = new AnimalDAO(context);
            Animal animal = animalDAO.obterAnimalPorId(animalId);

            String descricao = cursor.getString(cursor.getColumnIndex("descricao"));
            String dataStr = cursor.getString(cursor.getColumnIndex("data"));
            Date data = new Date(dataStr);

            // Retorna a consulta conforme o tipo
            Consulta consulta;
            if (descricao.contains("emergência")) {
                consulta = new ConsultaEmergencia(cursor.getInt(cursor.getColumnIndex("id")), animal, data, descricao);
            } else {
                consulta = new ConsultaRotina(cursor.getInt(cursor.getColumnIndex("id")), animal, data, descricao);
            }

            cursor.close();
            return consulta;
        }
        return null;
    }

    // Atualizar consulta
    public void atualizarConsulta(Consulta consulta) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("animal_id", consulta.getAnimal().getId());
        values.put("data", consulta.getData().toString());
        values.put("descricao", consulta.getDescricao());

        db.update(DatabaseHelper.TABELA_CONSULTA, values, "id=?", new String[]{String.valueOf(consulta.getId())});
    }

    // Deletar consulta
    public void deletarConsulta(int id) {
        db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABELA_CONSULTA, "id=?", new String[]{String.valueOf(id)});
    }
}
