package br.gustavoIgnacio.easypetvet.persistencia;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
*/

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "controle_veterinario.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABELA_ANIMAL = "animais";
    public static final String TABELA_CONSULTA = "consulta";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criar tabela Animal
        String createAnimalTable = "CREATE TABLE " + TABELA_ANIMAL + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "nome TEXT NOT NULL, " +
                "especie TEXT NOT NULL, " +
                "raca TEXT NOT NULL, " +
                "idade INTEGER NOT NULL, " +
				"cpf_dono TEXT NOT NULL)";
        db.execSQL(createAnimalTable);

        // Criar tabela Consulta
        String createConsultaTable = "CREATE TABLE " + TABELA_CONSULTA + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "animal_id INTEGER, " +
                "data TEXT, " +
                "descricao TEXT, " +
                "FOREIGN KEY(animal_id) REFERENCES " + TABELA_ANIMAL + "(id))";
        db.execSQL(createConsultaTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_ANIMAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CONSULTA);
        onCreate(db);
    }
}
