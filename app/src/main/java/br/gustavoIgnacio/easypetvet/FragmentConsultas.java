package br.gustavoIgnacio.easypetvet;

/*
@author: <Gustavo da Silva Ignacio 1110482313006>
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;
import br.gustavoIgnacio.easypetvet.model.Animal;
import br.gustavoIgnacio.easypetvet.model.ConsultaEmergencia;
import br.gustavoIgnacio.easypetvet.model.ConsultaRotina;
import br.gustavoIgnacio.easypetvet.persistencia.AnimalDAO;
import br.gustavoIgnacio.easypetvet.persistencia. * ;
import br.gustavoIgnacio.easypetvet.controller.AnimalController;
import br.gustavoIgnacio.easypetvet.controller.ConsultaEmergenciaController;
import br.gustavoIgnacio.easypetvet.controller.ConsultaRotinaController;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;
import android.database.SQLException;
import android.widget.TextView;

public class FragmentConsultas extends Fragment {

    private View view;
    private Spinner spAnimais;
    private EditText editTextData,
    editTextDescricao,
    editTextPrioridade,
    editTextTempoEstimadoAtendimento,
    editTextRecorrencia,
    editTextProximaConsulta;
    private RadioGroup radioGroup;
    private RadioButton radioEmergencia,
    radioRotina;
    private Button buttonSalvar,
    buttonListar;
    private AnimalDAO animalDAO;
    private AnimalController animalCont;
    private List < Animal > animais;
    private ConsultaEmergenciaController consultaEmergenciaController;
    private ConsultaRotinaController consultaRotinaController;
    private Animal animalSelecionado;

    public FragmentConsultas() {
        super();
    }

     @ Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_consultas, container, false);

        // Inicialização dos campos e botões
        spAnimais = view.findViewById(R.id.spAnimais);
        editTextData = view.findViewById(R.id.editTextData);
        editTextDescricao = view.findViewById(R.id.editTextDescricao);
        editTextPrioridade = view.findViewById(R.id.editTextPrioridade);
        editTextTempoEstimadoAtendimento = view.findViewById(R.id.editTextTempoEstimadoAtendimento);
        editTextRecorrencia = view.findViewById(R.id.editTextRecorrencia);
        editTextProximaConsulta = view.findViewById(R.id.editTextProximaConsulta);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioEmergencia = view.findViewById(R.id.radioEmergencia);
        radioRotina = view.findViewById(R.id.radioRotina);
        buttonSalvar = view.findViewById(R.id.buttonSalvar);
        buttonListar = view.findViewById(R.id.buttonListar);

        animalDAO = new AnimalDAO(view.getContext());
        animalCont = new AnimalController(animalDAO);
        consultaEmergenciaController = new ConsultaEmergenciaController(new ConsultaEmergenciaDAO(view.getContext()));
        consultaRotinaController = new ConsultaRotinaController(new ConsultaRotinaDAO(view.getContext()));

        preencheSpinner();

        // Configura a visibilidade inicial dos campos com base na seleção
        radioGroup.setOnCheckedChangeListener((group, checkedId)->{
            if (checkedId == R.id.radioEmergencia) {
                showEmergenciaFields();
            } else if (checkedId == R.id.radioRotina) {
                showRotinaFields();
            }
        });

        // Configuração dos botões
        buttonSalvar.setOnClickListener(v->salvarConsulta());
        buttonListar.setOnClickListener(v->listarConsultas());

        return view;
    }

    // Método para salvar Consulta
    private void salvarConsulta() {
        if (animalSelecionado == null) {
            Toast.makeText(view.getContext(), "Selecione um animal", Toast.LENGTH_LONG).show();
            return;
        }

        if (!camposValidos()) {
            return;
        }

        try {
            if (radioEmergencia.isChecked()) {
                ConsultaEmergencia consultaEmergencia = montaConsultaEmergencia();
                consultaEmergenciaController.insert(consultaEmergencia);
                Toast.makeText(view.getContext(), "Consulta de Emergência Inserida com Sucesso", Toast.LENGTH_LONG).show();
            } else if (radioRotina.isChecked()) {
                ConsultaRotina consultaRotina = montaConsultaRotina();
                consultaRotinaController.insert(consultaRotina);
                Toast.makeText(view.getContext(), "Consulta de Rotina Inserida com Sucesso", Toast.LENGTH_LONG).show();
            }
            limparCampos();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void listarConsultas() {
		FragmentListarConsultas fragmentListarConsultas = new FragmentListarConsultas();	
		
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragmentListarConsultas);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void limparCampos() {
        editTextData.setText("");
        editTextDescricao.setText("");
        editTextPrioridade.setText("");
        editTextTempoEstimadoAtendimento.setText("");
        editTextRecorrencia.setText("");
        editTextProximaConsulta.setText("");
    }

    private boolean camposValidos() {
        String data = editTextData.getText().toString().trim();
        String descricao = editTextDescricao.getText().toString().trim();

        if (data.isEmpty() || descricao.isEmpty()) {
            Toast.makeText(view.getContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void showEmergenciaFields() {
        editTextPrioridade.setVisibility(View.VISIBLE);
        editTextTempoEstimadoAtendimento.setVisibility(View.VISIBLE);
        editTextRecorrencia.setVisibility(View.GONE);
        editTextProximaConsulta.setVisibility(View.GONE);
    }

    private void showRotinaFields() {
        editTextPrioridade.setVisibility(View.GONE);
        editTextTempoEstimadoAtendimento.setVisibility(View.GONE);
        editTextRecorrencia.setVisibility(View.VISIBLE);
        editTextProximaConsulta.setVisibility(View.VISIBLE);
    }

    private void preencheSpinner() {
        String placeholder = "Escolha um Animal";

        try {
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

                    try {
                        animalSelecionado = animalCont.findById(String.valueOf(animalId));

                        if (animalSelecionado != null) {
                            Toast.makeText(getContext(), "Animal Selecionado: " + animalSelecionado.getNome(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Animal não encontrado", Toast.LENGTH_SHORT).show();
                        }
                    } catch (SQLException e) {
                        Toast.makeText(getContext(), "Erro ao buscar animal: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                 @ Override
                public void onNothingSelected(AdapterView <  ?  > parent) {}
            });

        } catch (SQLException e) {
            Toast.makeText(getContext(), "Erro ao carregar animais: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private ConsultaEmergencia montaConsultaEmergencia() {
        String data = editTextData.getText().toString().trim();
        String descricao = editTextDescricao.getText().toString().trim();
        String prioridade = editTextPrioridade.getText().toString().trim();
        String tempoEstimadoAtendimentoStr = editTextTempoEstimadoAtendimento.getText().toString().trim();

        int tempoEstimadoAtendimento = 0;
        if (!tempoEstimadoAtendimentoStr.isEmpty()) {
            tempoEstimadoAtendimento = Integer.parseInt(tempoEstimadoAtendimentoStr);
        }

        return new ConsultaEmergencia(animalSelecionado, data, descricao, prioridade, tempoEstimadoAtendimento);
    }

    private ConsultaRotina montaConsultaRotina() {
        String data = editTextData.getText().toString().trim();
        String descricao = editTextDescricao.getText().toString().trim();
        String recorrencia = editTextRecorrencia.getText().toString().trim();
        String proximaConsulta = editTextProximaConsulta.getText().toString().trim();

        return new ConsultaRotina(animalSelecionado, data, descricao, recorrencia, proximaConsulta);
    }
}
