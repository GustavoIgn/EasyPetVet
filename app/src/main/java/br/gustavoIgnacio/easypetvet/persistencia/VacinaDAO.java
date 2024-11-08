package br.gustavoIgnacio.easypetvet.persistencia;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.gustavoIgnacio.easypetvet.model.Vacina;
import br.gustavoIgnacio.easypetvet.model.Animal;
import java.util.ArrayList;
import java.util.List;

public class VacinaDAO {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public VacinaDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Inserir nova vacina
    public void inserirVacina(Vacina vacina) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("animal_id", vacina.getAnimal().getId());
        values.put("nome", vacina.getNome());
        values.put("data_aplicacao", vacina.getDataAplicacao().toString());
        values.put("proxima_aplicacao", vacina.getProximaAplicacao().toString());

        db.insert(DatabaseHelper.TABELA_VACINA, null, values);
    }

    // Obter todas as vacinas
    public List<Vacina> obterTodasVacinas() {
        List<Vacina> vacinas = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABELA_VACINA, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int animalId = cursor.getInt(cursor.getColumnIndex("animal_id"));
                AnimalDAO animalDAO = new AnimalDAO(dbHelper.getContext());
                Animal animal = animalDAO.obterAnimalPorId(animalId);

                String nome = cursor.getString(cursor.getColumnIndex("nome"));
                String dataAplicacaoStr = cursor.getString(cursor.getColumnIndex("data_aplicacao"));
                Date dataAplicacao = new Date(dataAplicacaoStr);
                String proximaAplicacaoStr = cursor.getString(cursor.getColumnIndex("proxima_aplicacao"));
                Date proximaAplicacao = new Date(proximaAplicacaoStr);

                Vacina vacina = new Vacina(cursor.getInt(cursor.getColumnIndex("id")), animal, nome, dataAplicacao, proximaAplicacao);
                vacinas.add(vacina);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return vacinas;
    }

    // Obter vacina por ID
    public Vacina obterVacinaPorId(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABELA_VACINA, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int animalId = cursor.getInt(cursor.getColumnIndex("animal_id"));
            AnimalDAO animalDAO = new AnimalDAO(dbHelper.getContext());
            Animal animal = animalDAO.obterAnimalPorId(animalId);

            String nome = cursor.getString(cursor.getColumnIndex("nome"));
            String dataAplicacaoStr = cursor.getString(cursor.getColumnIndex("data_aplicacao"));
            Date dataAplicacao = new Date(dataAplicacaoStr);
            String proximaAplicacaoStr = cursor.getString(cursor.getColumnIndex("proxima_aplicacao"));
            Date proximaAplicacao = new Date(proximaAplicacaoStr);

            Vacina vacina = new Vacina(cursor.getInt(cursor.getColumnIndex("id")), animal, nome, dataAplicacao, proximaAplicacao);
            cursor.close();
            return vacina;
        }
        return null;
    }

    // Atualizar vacina
    public void atualizarVacina(Vacina vacina) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("animal_id", vacina.getAnimal().getId());
        values.put("nome", vacina.getNome());
        values.put("data_aplicacao", vacina.getDataAplicacao().toString());
        values.put("proxima_aplicacao", vacina.getProximaAplicacao().toString());

        db.update(DatabaseHelper.TABELA_VACINA, values, "id=?", new String[]{String.valueOf(vacina.getId())});
    }

    // Deletar vacina
    public void deletarVacina(int id) {
        db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABELA_VACINA, "id=?", new String[]{String.valueOf(id)});
    }
}
