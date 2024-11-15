package br.gustavoIgnacio.easypetvet.persistencia;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "controle_veterinario.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABELA_ANIMAL = "animais";
    public static final String TABELA_CONSULTA_EMERGENCIA = "consulta_emergencia";
    public static final String TABELA_CONSULTA_ROTINA = "consulta_rotina";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

     @ Override
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

        // Criar tabela Consulta de Emergência
        String createConsultaEmergenciaTable = "CREATE TABLE " + TABELA_CONSULTA_EMERGENCIA + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "animal_id INTEGER, " +
            "data TEXT, " +
            "descricao TEXT, " +
            "tipo_consulta INTEGER, " +
            "prioridade TEXT, " +
            "tempo_estimado_atendimento INTEGER, " +
            "FOREIGN KEY(animal_id) REFERENCES " + TABELA_ANIMAL + "(id))";
        db.execSQL(createConsultaEmergenciaTable);

        // Criar tabela Consulta de Rotina
        String createConsultaRotinaTable = "CREATE TABLE " + TABELA_CONSULTA_ROTINA + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "animal_id INTEGER, " +
            "data TEXT, " +
            "descricao TEXT, " +
            "tipo_consulta INTEGER, " +
            "recorrencia TEXT, " +
            "proxima_consulta TEXT, " +
            "FOREIGN KEY(animal_id) REFERENCES " + TABELA_ANIMAL + "(id))";
        db.execSQL(createConsultaRotinaTable);
    }

     @ Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_ANIMAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CONSULTA_EMERGENCIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CONSULTA_ROTINA);
        onCreate(db);

    }
}
