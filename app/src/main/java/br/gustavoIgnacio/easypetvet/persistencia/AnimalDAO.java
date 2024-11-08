package br.gustavoIgnacio.easypetvet.persistencia;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.gustavoIgnacio.easypetvet.model.Animal;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public AnimalDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Inserir animal
    public void inserirAnimal(Animal animal) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", animal.getNome());
        values.put("especie", animal.getEspecie());
        values.put("raca", animal.getRaca());
        values.put("idade", animal.getIdade());
        db.insert(DatabaseHelper.TABELA_ANIMAL, null, values);
    }

    // Obter todos os animais
    public List<Animal> obterTodosAnimais() {
        List<Animal> animais = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABELA_ANIMAL, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Animal animal = new Animal(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("nome")),
                        cursor.getString(cursor.getColumnIndex("especie")),
                        cursor.getString(cursor.getColumnIndex("raca")),
                        cursor.getInt(cursor.getColumnIndex("idade"))
                );
                animais.add(animal);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return animais;
    }

    // Obter animal por ID
    public Animal obterAnimalPorId(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABELA_ANIMAL, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Animal animal = new Animal(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("nome")),
                    cursor.getString(cursor.getColumnIndex("especie")),
                    cursor.getString(cursor.getColumnIndex("raca")),
                    cursor.getInt(cursor.getColumnIndex("idade"))
            );
            cursor.close();
            return animal;
        }
        return null;
    }

    // Atualizar animal
    public void atualizarAnimal(Animal animal) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", animal.getNome());
        values.put("especie", animal.getEspecie());
        values.put("raca", animal.getRaca());
        values.put("idade", animal.getIdade());
        db.update(DatabaseHelper.TABELA_ANIMAL, values, "id=?", new String[]{String.valueOf(animal.getId())});
    }

    // Deletar animal
    public void deletarAnimal(int id) {
        db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABELA_ANIMAL, "id=?", new String[]{String.valueOf(id)});
    }
}
