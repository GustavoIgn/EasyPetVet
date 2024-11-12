package br.gustavoIgnacio.easypetvet;

/*
@author: <Gustavo da Silva Ignacio 1110482313006>
*/

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import br.gustavoIgnacio.easypetvet.controller.AnimalController;
import br.gustavoIgnacio.easypetvet.model.Animal;
import br.gustavoIgnacio.easypetvet.persistencia.AnimalDAO;

import android.database.SQLException;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;

public class FragmentAnimais extends Fragment {

    private EditText editTextCpfDono;
    private EditText editTextNome;
    private EditText editTextEspecie;
    private EditText editTextRaca;
    private EditText editTextIdade;
    private Button buttonSalvar, buttonApagar, buttonEditar, buttonListar, buttonBuscar;
    private TextView tvResultado;
    private View view;
    private AnimalController animalController;

    public FragmentAnimais() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_animais, container, false);

        // Inicialização dos campos e botões
        animalController = new AnimalController(new AnimalDAO(view.getContext()));
        editTextCpfDono = view.findViewById(R.id.editTextCpfDono);
        editTextNome = view.findViewById(R.id.editTextNome);
        editTextEspecie = view.findViewById(R.id.editTextEspecie);
        editTextRaca = view.findViewById(R.id.editTextRaca);
        editTextIdade = view.findViewById(R.id.editTextIdade);
        buttonSalvar = view.findViewById(R.id.buttonSalvar);
        buttonListar = view.findViewById(R.id.buttonListar);
        buttonApagar = view.findViewById(R.id.buttonApagar);
        buttonEditar = view.findViewById(R.id.buttonEditar);
        buttonBuscar = view.findViewById(R.id.buttonBuscar);
        tvResultado = view.findViewById(R.id.tvListaAnimais);
        tvResultado.setMovementMethod(new ScrollingMovementMethod());

        // Configuração dos botões
        buttonSalvar.setOnClickListener(v -> salvarAnimal());
        buttonListar.setOnClickListener(v -> listarAnimais());
        buttonApagar.setOnClickListener(v -> apagarAnimal());
        buttonEditar.setOnClickListener(v -> editarAnimal());
        buttonBuscar.setOnClickListener(v -> buscarAnimais());

        return view;
    }

    // Método para salvar um animal
    private void salvarAnimal() {
        Animal animal = montaAnimal();

        try {
            animalController.insert(animal);
            Toast.makeText(view.getContext(), "Animal Inserido com Sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limparCampos();
    }

    // Método para buscar por CPF
    private void buscarAnimais() {
        Animal animal = montaAnimal();

        try {
            Animal animal1 = animalController.findByCPF(animal);
            if (animal.getNome() != null && animal1.getNome() != null) {
                preencheCampos(animal);
            } else {
                Toast.makeText(view.getContext(), "Animal não Encontrado", Toast.LENGTH_LONG).show();
                limparCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limparCampos();
    }

    // Método para Listar animais
    private void listarAnimais() {
        try {
            List<Animal> animais = animalController.findALL();
            StringBuffer buffer = new StringBuffer();
            for (Animal a : animais) {
                buffer.append(a.toString() + "\n");
            }

            tvResultado.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Método para apagar um animal
    private void apagarAnimal() {
        Animal animal = montaAnimal();
        try {
            animalController.delete(animal);
            Toast.makeText(view.getContext(), "Animal Apagado com Sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limparCampos();
    }

    // Método para editar um animal
    private void editarAnimal() {
        Animal animal = montaAnimal();

        try {
            animalController.update(animal);
            Toast.makeText(view.getContext(), "Animal Atualizado com Sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limparCampos();
    }

    private Animal montaAnimal() {
        String nome = editTextNome.getText().toString().trim();
        String especie = editTextEspecie.getText().toString().trim();
        String raca = editTextRaca.getText().toString().trim();
        String idadeStr = editTextIdade.getText().toString().trim();
        String cpfDono = editTextCpfDono.getText().toString().trim();

        // Converte a idade para inteiro, com valor padrão 0 caso o campo esteja vazio.
        int idade = idadeStr.isEmpty() ? 0 : Integer.parseInt(idadeStr);

        // Cria um novo objeto Animal com os valores extraídos da tela
        Animal animal = new Animal();
        animal.setNome(nome);
        animal.setEspecie(especie);
        animal.setRaca(raca);
        animal.setIdade(idade);
        animal.setCPFDono(cpfDono);

        return animal;
    }

    private void preencheCampos(Animal animal) {
        editTextNome.setText(animal.getNome());
        editTextEspecie.setText(animal.getEspecie());
        editTextRaca.setText(animal.getRaca());
        editTextIdade.setText(String.valueOf(animal.getIdade()));
        editTextCpfDono.setText(animal.getCPFDono());
    }

    // Método para limpar os campos
    private void limparCampos() {
        editTextNome.setText("");
        editTextEspecie.setText("");
        editTextRaca.setText("");
        editTextIdade.setText("");
        editTextCpfDono.setText("");
    }
}