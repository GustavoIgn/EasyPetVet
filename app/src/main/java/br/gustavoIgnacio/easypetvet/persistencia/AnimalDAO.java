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

public class AnimalDAO implements ICRUDDao<Animal>{

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
	private final Context context;

    public AnimalDAO(Context context) {
		this.context = context;
    }
	
	public AnimalDAO open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	@Override
	public void insert(Animal animal) throws SQLException {
		ContentValues contentValues = getContentValues(animal);
		db.insert(DatabaseHelper.TABELA_ANIMAL, null, contentValues);
    }
	
	
	@Override
	public int update(Animal animal) throws SQLException {
		ContentValues contentValues = getContentValues(animal);
		int ret = db.update(DatabaseHelper.TABELA_ANIMAL, contentValues, "id = ?", new String[]{String.valueOf(animal.getId())});
		return ret;
	}
	
	@Override
	public void delete(Animal animal) throws SQLException {
		ContentValues contentValues = getContentValues(animal);
		db.delete(DatabaseHelper.TABELA_ANIMAL, "id = ?", new String[]{String.valueOf(animal.getId())});
	}
	
	@Override
	public Animal findById(String id) throws SQLException {
		Animal resultadoAnimal = null;
		Cursor cursor = db.query(DatabaseHelper.TABELA_ANIMAL, null, "id = ?", new String[]{id}, null, null, null);

		if (cursor != null && cursor.moveToFirst()) {
			resultadoAnimal = cursorToAnimal(cursor);
			cursor.close();
		}

		return resultadoAnimal;
	}
	
    @Override
    public List<Animal> findALL() throws SQLException {
        List<Animal> animais = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABELA_ANIMAL, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Animal animal = cursorToAnimal(cursor);
                animais.add(animal);
            }
            cursor.close();
        }

        return animais;
    }

    private Animal cursorToAnimal(Cursor cursor) {
        Animal animal = new Animal();
        animal.setId(cursor.getInt(cursor.getColumnIndex("id")));
        animal.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        animal.setEspecie(cursor.getString(cursor.getColumnIndex("especie")));
        animal.setRaca(cursor.getString(cursor.getColumnIndex("raca")));
        animal.setIdade(cursor.getInt(cursor.getColumnIndex("idade")));
        animal.setCPFDono(cursor.getString(cursor.getColumnIndex("cpf_dono")));
        return animal;
    }

    public ContentValues getContentValues(Animal animal) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", animal.getNome());
        contentValues.put("especie", animal.getEspecie());
        contentValues.put("raca", animal.getRaca());
        contentValues.put("idade", animal.getIdade());
        contentValues.put("cpf_dono", animal.getCPFDono());

        return contentValues;
    }
}