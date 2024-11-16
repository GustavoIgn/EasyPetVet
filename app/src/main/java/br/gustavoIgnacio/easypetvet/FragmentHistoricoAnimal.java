package br.gustavoIgnacio.easypetvet;

/*
@author: <Gustavo da Silva Ignacio 1110482313006>
 */

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import androidx.fragment.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import java.util.List;
import java.util.ArrayList;

import android.database.SQLException;

import br.gustavoIgnacio.easypetvet.controller.ConsultaEmergenciaController;
import br.gustavoIgnacio.easypetvet.controller.ConsultaRotinaController;
import br.gustavoIgnacio.easypetvet.controller.*;
import br.gustavoIgnacio.easypetvet.model.*;
import br.gustavoIgnacio.easypetvet.persistencia.*;

public class FragmentHistoricoAnimal extends Fragment {

    private View view;
    private Button buttonBuscar;
    private TextView tvListaConsultasEmergencia,
    tvListaConsultasRotina;
	private Spinner spAnimais;
    private ConsultaEmergenciaController consultaEmergenciaController;
    private ConsultaRotinaController consultaRotinaController;
	private List < Animal > animais;
    private Animal animalSelecionado;
    private AnimalController animalCont;

    public FragmentHistoricoAnimal() {
        super();
    }

     @ Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_historico_animal, container, false);
		
		// Inicializa os elementos
		buttonBuscar = view.findViewById(R.id.buttonBuscar);
		spAnimais = view.findViewById(R.id.spAnimais);
        tvListaConsultasEmergencia = view.findViewById(R.id.tvListaConsultasEmergencia);
        tvListaConsultasRotina = view.findViewById(R.id.tvListaConsultasRotina);
		
		
		animalCont = new AnimalController(new AnimalDAO(view.getContext()));
        consultaEmergenciaController = new ConsultaEmergenciaController(new ConsultaEmergenciaDAO(view.getContext()));
        consultaRotinaController = new ConsultaRotinaController(new ConsultaRotinaDAO(view.getContext()));
		preencheSpinner();


        // Configura o botão de busca
        buttonBuscar.setOnClickListener(v->buscarConsultaPorId());

        return view;
    }
	
	
	private void preencheSpinner() {
        String placeholder = "Escolha um Animal";

        animais = animalCont.findALL();
        if (animais == null) {
            animais = new ArrayList <  > ();
        }

        List < String > animalDisplayList = new ArrayList <  > ();
        animalDisplayList.add(placeholder);

        for (Animal animal: animais) {
            String displayText = "ID= " + animal.getId() + "| NOME= " + animal.getNome() + "| ESPÉCIE=" + animal.getEspecie() + "|";
            animalDisplayList.add(displayText);
        }

        ArrayAdapter < String > adapter = new ArrayAdapter <  > (getContext(), android.R.layout.simple_spinner_item, animalDisplayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAnimais.setAdapter(adapter);

        spAnimais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @ Override
            public void onItemSelected(AdapterView <  ?  > parent, View view, int position, long id) {
                if (position == 0) { // Placeholder
                    return;
                }
                // Código para tratar a seleção do animal
                String selectedAnimalText = (String)parent.getItemAtPosition(position);
                int animalId = Integer.parseInt(selectedAnimalText.split("=")[1].trim().split("\\|")[0].trim());

                animalSelecionado = animalCont.findById(String.valueOf(animalId));

                if (animalSelecionado != null) {
                    Toast.makeText(getContext(), "Animal Selecionado: " + animalSelecionado.getNome(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Animal não encontrado", Toast.LENGTH_SHORT).show();
                }
            }

             @ Override
            public void onNothingSelected(AdapterView <  ?  > parent) {}
        });
    }

    private void buscarConsultaPorId() {
        if (animalSelecionado == null) {
            Toast.makeText(view.getContext(), "Selecione um Animal para buscar", Toast.LENGTH_SHORT).show();
            return;
        }

        int animalId = animalSelecionado.getId();

        // Tenta buscar nos dois tipos de consulta
        List < ConsultaEmergencia > consultasEmergencia = consultaEmergenciaController.findByAnimalId(animalId);
        List < ConsultaRotina > consultasRotina = consultaRotinaController.findByAnimalId(animalId);

        if (!consultasEmergencia.isEmpty() || !consultasRotina.isEmpty()) {
            // Exibir as consultas encontradas
            exibeConsultas(consultasEmergencia, consultasRotina);
        } else {
            Toast.makeText(getContext(), "Nenhuma consulta encontrada para este animal", Toast.LENGTH_SHORT).show();
			tvListaConsultasEmergencia.setText("Sem consultas de emergencia");
			tvListaConsultasRotina.setText("Sem consultas de rotina");
        }
    }

    private void exibeConsultas(List < ConsultaEmergencia > consultasEmergencia, List < ConsultaRotina > consultasRotina) {
        StringBuilder emergenciaBuffer = new StringBuilder();
        StringBuilder rotinaBuffer = new StringBuilder();

        for (ConsultaEmergencia cE: consultasEmergencia) {
            emergenciaBuffer.append(cE.toString()).append("\n");
            emergenciaBuffer.append("____________________________________\n");
        }

        for (ConsultaRotina cR: consultasRotina) {
            rotinaBuffer.append(cR.toString()).append("\n");
            rotinaBuffer.append("____________________________________\n");
        }

        // Atualiza as TextViews com as consultas
        tvListaConsultasEmergencia.setText(emergenciaBuffer.toString());
        tvListaConsultasRotina.setText(rotinaBuffer.toString());
    }
}
