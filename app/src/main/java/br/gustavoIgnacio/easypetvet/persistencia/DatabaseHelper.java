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

    public static final String TABELA_ANIMAL = "animal";
    public static final String TABELA_CONSULTA = "consulta";
    public static final String TABELA_VACINA = "vacina";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criar tabela Animal
        String createAnimalTable = "CREATE TABLE " + TABELA_ANIMAL + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT, " +
                "especie TEXT, " +
                "raca TEXT, " +
                "idade INTEGER, " +
				"cpfDono TEXT)";
        db.execSQL(createAnimalTable);

        // Criar tabela Consulta
        String createConsultaTable = "CREATE TABLE " + TABELA_CONSULTA + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "animal_id INTEGER, " +
                "data TEXT, " +
                "descricao TEXT, " +
                "FOREIGN KEY(animal_id) REFERENCES " + TABELA_ANIMAL + "(id))";
        db.execSQL(createConsultaTable);

        // Criar tabela Vacina
        String createVacinaTable = "CREATE TABLE " + TABELA_VACINA + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "animal_id INTEGER, " +
                "nome TEXT, " +
                "data_aplicacao TEXT, " +
                "proxima_aplicacao TEXT, " +
                "FOREIGN KEY(animal_id) REFERENCES " + TABELA_ANIMAL + "(id))";
        db.execSQL(createVacinaTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_ANIMAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CONSULTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_VACINA);
        onCreate(db);
    }
}