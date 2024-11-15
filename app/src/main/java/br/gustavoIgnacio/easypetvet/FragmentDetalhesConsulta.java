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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import androidx.fragment.app.Fragment;
import java.util.List;
import java.util.ArrayList;

import java.sql.SQLException;

import br.gustavoIgnacio.easypetvet.controller.ConsultaEmergenciaController;
import br.gustavoIgnacio.easypetvet.controller.ConsultaRotinaController;
import br.gustavoIgnacio.easypetvet.controller. * ;
import br.gustavoIgnacio.easypetvet.model. * ;
import br.gustavoIgnacio.easypetvet.persistencia. * ;
import androidx.fragment.app.FragmentTransaction;
import br.gustavoIgnacio.easypetvet.model.Consulta;

public class FragmentDetalhesConsulta extends Fragment {

    private TextView tvTituloConsultas;
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
    private Button buttonEditar,
    buttonApagar,
    buttonListar,
    buttonNovaConsulta;
    private View view;

    private ConsultaEmergenciaController consultaEmergenciaController;
    private ConsultaRotinaController consultaRotinaController;
    private Consulta consultaAtual;
    private List < Animal > animais;
    private Animal animalSelecionado;
    private AnimalController animalCont;

    public FragmentDetalhesConsulta() {}

     @ Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detalhes_consultas, container, false);

        // Inicialize os elementos
        tvTituloConsultas = view.findViewById(R.id.tvTituloConsultas);
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

        buttonEditar = view.findViewById(R.id.buttonEditar);
        buttonApagar = view.findViewById(R.id.buttonApagar);
        buttonListar = view.findViewById(R.id.buttonListar);
        buttonNovaConsulta = view.findViewById(R.id.buttonNovaConsulta);

        // Inicialize os controllers
        consultaEmergenciaController = new ConsultaEmergenciaController(new ConsultaEmergenciaDAO(getContext()));
        consultaRotinaController = new ConsultaRotinaController(new ConsultaRotinaDAO(getContext()));
        animalCont = new AnimalController(new AnimalDAO(getContext()));

        // Recebe o ID da consulta e carrega os dados
        String consultaId = getArguments().getString("consultaId");
        String tipoConsulta = getArguments().getString("tipoConsulta");
        preencheSpinner();
        carregarConsulta(consultaId, tipoConsulta);

        if (tipoConsulta.equals("emergencia")) {
            tvTituloConsultas.setText(R.string.consulta_emergencia);
        } else {
            tvTituloConsultas.setText(R.string.consulta_rotina);
        }

        // Configura os botões
        buttonEditar.setOnClickListener(v->editarConsulta());
        buttonApagar.setOnClickListener(v->apagarConsulta());
        buttonListar.setOnClickListener(v->voltarParaLista());
        buttonNovaConsulta.setOnClickListener(v->voltarParaCadastro());

        return view;
    }

    private void carregarConsulta(String id, String tipoConsulta) {
        if (tipoConsulta.equals("emergencia")) {
            consultaAtual = consultaEmergenciaController.findById(id);
        } else if (tipoConsulta.equals("rotina")) {
            consultaAtual = consultaRotinaController.findById(id);
        } else {
            Toast.makeText(getContext(), "Consulta não encontrada", Toast.LENGTH_SHORT).show();
        }
        preencherCampos(consultaAtual);
    }

    private void preencherCampos(Consulta consulta) {
        editTextData.setText(consulta.getData());
        editTextDescricao.setText(consulta.getDescricao());

        if (consulta instanceof ConsultaEmergencia) {
            editTextPrioridade.setText(((ConsultaEmergencia)consulta).getPrioridade());
            editTextTempoEstimadoAtendimento.setText(String.valueOf(((ConsultaEmergencia)consulta).getTempoEstimadoAtendimento()));
            editTextPrioridade.setVisibility(View.VISIBLE);
            editTextTempoEstimadoAtendimento.setVisibility(View.VISIBLE);
            editTextRecorrencia.setVisibility(View.GONE);
            editTextProximaConsulta.setVisibility(View.GONE);
        } else if (consulta instanceof ConsultaRotina) {
            editTextRecorrencia.setText(((ConsultaRotina)consulta).getRecorrencia());
            editTextProximaConsulta.setText(((ConsultaRotina)consulta).getProximaConsulta());
            editTextPrioridade.setVisibility(View.GONE);
            editTextTempoEstimadoAtendimento.setVisibility(View.GONE);
            editTextRecorrencia.setVisibility(View.VISIBLE);
            editTextProximaConsulta.setVisibility(View.VISIBLE);
        }
    }

    // Método para apagar a consulta
    private void apagarConsulta() {
        if (consultaAtual == null) {
            Toast.makeText(getContext(), "Consulta não encontrada para exclusão", Toast.LENGTH_SHORT).show();
            return;
        }

        if (consultaAtual instanceof ConsultaEmergencia) {
            consultaEmergenciaController.delete((ConsultaEmergencia)consultaAtual);
            Toast.makeText(getContext(), "Consulta de Emergência apagada com sucesso", Toast.LENGTH_LONG).show();
        } else {
            consultaRotinaController.delete((ConsultaRotina)consultaAtual);
            Toast.makeText(getContext(), "Consulta de Rotina apagada com sucesso", Toast.LENGTH_LONG).show();
        }
        voltarParaLista();
    }

    // Método para editar a consulta
    private void editarConsulta() {
        if (animalSelecionado == null) {
            Toast.makeText(view.getContext(), "Selecione um animal", Toast.LENGTH_LONG).show();
            return;
        }

        if (!camposValidos())
            return;

        if (consultaAtual instanceof ConsultaEmergencia) {
            ConsultaEmergencia cE = montaConsultaEmergencia();
            cE.setId(consultaAtual.getId()); // Mantenha o ID da consulta original
            consultaEmergenciaController.update(cE);
            Toast.makeText(view.getContext(), "Consulta de Emergência Atualizada com Sucesso", Toast.LENGTH_LONG).show();
        } else {
            ConsultaRotina cR = montaConsultaRotina();
            cR.setId(consultaAtual.getId()); // Mantenha o ID da consulta original
            consultaRotinaController.update(cR);
            Toast.makeText(view.getContext(), "Consulta de Rotina Atualizada com Sucesso", Toast.LENGTH_LONG).show();
        }
		voltarParaLista();
    }

    private boolean camposValidos() {
        // Verificar se os campos essenciais estão preenchidos
        return !editTextData.getText().toString().trim().isEmpty() && !editTextDescricao.getText().toString().trim().isEmpty();
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

    private ConsultaEmergencia montaConsultaEmergencia() {
        String data = editTextData.getText().toString().trim();
        String descricao = editTextDescricao.getText().toString().trim();
        String prioridade = editTextPrioridade.getText().toString().trim();
        String tempoEstimadoAtendimento = editTextTempoEstimadoAtendimento.getText().toString().trim();
        return new ConsultaEmergencia(animalSelecionado, data, descricao, prioridade, Integer.parseInt(tempoEstimadoAtendimento));

    }

    private ConsultaRotina montaConsultaRotina() {
        String data = editTextData.getText().toString().trim();
        String descricao = editTextDescricao.getText().toString().trim();
        String recorrencia = editTextRecorrencia.getText().toString().trim();
        String proximaConsulta = editTextProximaConsulta.getText().toString().trim();
        return new ConsultaRotina(animalSelecionado, data, descricao, recorrencia, proximaConsulta);
    }

    private void voltarParaLista() {
        // Transição para o FragmentListarConsultas
        FragmentListarConsultas fragmentListarConsultas = new FragmentListarConsultas();

        // Cria uma transação para substituir o fragmento atual
        getFragmentManager().beginTransaction()
        .replace(R.id.fragment, fragmentListarConsultas)
        .addToBackStack(null)
        .commit();
    }

    private void voltarParaCadastro() {
        // Transição para o FragmentConsultas
        FragmentConsultas fragmentConsultas = new FragmentConsultas();

        // Cria uma transação para substituir o fragmento atual
        getFragmentManager().beginTransaction()
        .replace(R.id.fragment, fragmentConsultas)
        .addToBackStack(null)
        .commit();
    }
}
