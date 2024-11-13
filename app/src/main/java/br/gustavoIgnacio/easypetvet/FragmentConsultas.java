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
import br.gustavoIgnacio.easypetvet.persistencia. * ;
import br.gustavoIgnacio.easypetvet.controller.AnimalController;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import android.database.SQLException;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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
    buttonListar,
    buttonApagar,
    buttonEditar,
    buttonBuscar;
    private AnimalDAO animalDAO;
    private AnimalController animalCont;
    private List < Animal > animais;

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
        buttonApagar = view.findViewById(R.id.buttonApagar);
        buttonEditar = view.findViewById(R.id.buttonEditar);
        buttonBuscar = view.findViewById(R.id.buttonBuscar);

        animalDAO = new AnimalDAO(view.getContext());
        animalCont = new AnimalController(animalDAO);
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
        buttonSalvar.setOnClickListener(v->salvarAnimal());
        buttonListar.setOnClickListener(v->listarAnimais());
        buttonApagar.setOnClickListener(v->apagarAnimal());
        buttonEditar.setOnClickListener(v->editarAnimal());
        buttonBuscar.setOnClickListener(v->buscarAnimais());

        return view;
    }

    ------------------------------------------------------------ -

    // Método para salvar Consulta
    private void salvarConsulta() {
        if (!camposValidos())
            return;

        if (radioEmergencia.isChecked()) {
            // Criação de uma consulta de emergência
            ConsultaEmergencia consultaEmergencia = montaConsultaEmergencia();
            try {
                ConsultaEmergenciaController.insert(consultaEmergencia);
                Toast.makeText(view.getContext(), "Consulta de Emergência Inserida com Sucesso", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else if (radioRotina.isChecked()) {
            // Criação de uma consulta de rotina
            ConsultaRotina consultaRotina = montaConsultaRotina();
            try {
                ConsultaRotinaController.insert(consultaRotina);
                Toast.makeText(view.getContext(), "Consulta de Rotina Inserida com Sucesso", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        limparCampos();
    }

    // Métodos auxiliares para montar consulta de emergência e de rotina
    private ConsultaEmergencia montaConsultaEmergencia() {
        Animal animalSelecionado = (Animal)spAnimais.getSelectedItem();
        String data = editTextData.getText().toString().trim();
        String descricao = editTextDescricao.getText().toString().trim();
        String prioridade = editTextPrioridade.getText().toString().trim();
        String tempoEstimadoAtendimento = editTextTempoEstimadoAtendimento.getText().toString().trim();

        return new ConsultaEmergencia(animalSelecionado, data, descricao, prioridade, tempoEstimadoAtendimento);
    }

    private ConsultaRotina montaConsultaRotina() {
        Animal animalSelecionado = (Animal)spAnimais.getSelectedItem();
        String data = editTextData.getText().toString().trim();
        String descricao = editTextDescricao.getText().toString().trim();
        String recorrencia = editTextRecorrencia.getText().toString().trim();
        String proximaConsulta = editTextProximaConsulta.getText().toString().trim();

        return new ConsultaRotina(animalSelecionado, data, descricao, recorrencia, proximaConsulta);
    }

    // Método para buscar por Id
    private void buscarAnimais() {
        String id = editTextId.getText().toString().trim();

        if (id.isEmpty()) {
            Toast.makeText(view.getContext(), "Digite o ID conforme Tabela", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            Animal animal = animalController.findById(id);
            if (animal != null) {
                preencheCampos(animal);
            } else {
                Toast.makeText(view.getContext(), "Animal não Encontrado", Toast.LENGTH_LONG).show();
                limparCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Método para Listar animais
    private void listarAnimais() {
        try {
            List < Animal > animais = animalController.findALL();
            StringBuffer buffer = new StringBuffer();
            for (Animal a: animais) {
                buffer.append(a.toString() + "\n");
                buffer.append("____________________________________\n");
            }

            tvResultado.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Método para apagar um animal
    private void apagarAnimal() {
        if (!camposValidos())
            return;
        Animal animal = montaAnimal();
        try {
            animalController.delete(animal);
            Toast.makeText(view.getContext(), "Animal Apagado com Sucesso", Toast.LENGTH_LONG).show();
            listarAnimais();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limparCampos();
    }

    // Método para editar um animal
    private void editarAnimal() {
        if (!camposValidos())
            return;
        Animal animal = montaAnimal();

        try {
            animalController.update(animal);
            Toast.makeText(view.getContext(), "Animal Atualizado com Sucesso", Toast.LENGTH_LONG).show();
            listarAnimais();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limparCampos();
    }

    private void preencheCampos(Animal animal) {
        editTextNome.setText(animal.getNome());
        editTextEspecie.setText(animal.getEspecie());
        editTextRaca.setText(animal.getRaca());
        editTextIdade.setText(String.valueOf(animal.getIdade()));
        editTextCpfDono.setText(animal.getCPFDono());
    }

    // Método para limpar os campos após salvar ou editar
    private void limparCampos() {
		editTextData.setText("");
        editTextDescricao.setText("");
        editTextPrioridade.setText("");
        editTextTempoEstimadoAtendimento.setText("");
        editTextRecorrencia.setText("");
        editTextProximaConsulta.setText("");
    }

    // Método para verificar se todos os campos estão preenchidos
    private boolean camposValidos() {
		String data = editTextData.getText().toString().trim();
        String descricao = editTextDescricao.getText().toString().trim();
        String prioridade = editTextPrioridade.getText().toString().trim();
        String tempoEstimadoAtendimento = editTextTempoEstimadoAtendimento.getText().toString().trim();
        String recorrencia = editTextRecorrencia.getText().toString().trim();
        String proximaConsulta = editTextProximaConsulta.getText().toString().trim();

        // Verifica se algum campo está vazio ou idade igual a 0
        if (nome.isEmpty() || especie.isEmpty() || raca.isEmpty() || cpfDono.isEmpty() || idadeStr.isEmpty() || id.isEmpty()) {
            Toast.makeText(view.getContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
            return false;
        }

        int idade = Integer.parseInt(idadeStr);
        if (idade == 0) {
            Toast.makeText(view.getContext(), "Idade não pode ser zero", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    -------------------------------------------------------- -

    // Função para exibir campos de emergência
    private void showEmergenciaFields() {
        editTextPrioridade.setVisibility(View.VISIBLE);
        editTextTempoEstimadoAtendimento.setVisibility(View.VISIBLE);
        editTextRecorrencia.setVisibility(View.GONE);
        editTextProximaConsulta.setVisibility(View.GONE);
    }

    // Função para exibir campos de rotina
    private void showRotinaFields() {
        editTextPrioridade.setVisibility(View.GONE);
        editTextTempoEstimadoAtendimento.setVisibility(View.GONE);
        editTextRecorrencia.setVisibility(View.VISIBLE);
        editTextProximaConsulta.setVisibility(View.VISIBLE);
    }

    private void preencheSpinner() {
        // placeholder
        String placeholder = "Escolha um Animal";

        try {
            // animais do banco de dados
            animais = animalCont.findALL();

            // Se a lista estiver vazia, cria uma nova lista
            if (animais == null) {
                animais = new ArrayList <  > ();
            }

            // Cria uma nova lista de strings para exibir no Spinner
            List < String > animalDisplayList = new ArrayList <  > ();
            animalDisplayList.add(placeholder);

            // Adiciona uma string formatada para cada animal na lista
            for (Animal animal: animais) {
                String displayText = "ID= " animal.getId() + "| NOME= " + animal.getNome() + "| ESPÉCIE=" + animal.getEspecie() + "|";
                animalDisplayList.add(displayText);
            }

            // Configura o adaptador do Spinner com a lista de strings formatadas
            ArrayAdapter < String > adapter = new ArrayAdapter <  > (getContext(),
                    android.R.layout.simple_spinner_item,
                    animalDisplayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spAnimais.setAdapter(adapter);

            // Configura o Spinner para evitar seleção do placeholder
            spAnimais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @ Override
                public void onItemSelected(AdapterView <  ?  > parent, View view, int position, long id) {
                    if (position == 0) {
                        spAnimais.setSelection(0);
                    } else {
                        Animal selectedAnimal = animais.get(position - 1);
                    }
                }

                 @ Override
                public void onNothingSelected(AdapterView <  ?  > parent) {
                    // Aqui você pode tratar o caso em que nada é selecionado, se necessário
                }
            });

        } catch (SQLException e) {
            Toast.makeText(getContext(), "Erro ao carregar animais: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(getContext(), "Erro inesperado: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
