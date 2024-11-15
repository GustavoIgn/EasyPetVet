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
import androidx.fragment.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;

import java.util.List;
import android.database.SQLException;

import br.gustavoIgnacio.easypetvet.controller.ConsultaEmergenciaController;
import br.gustavoIgnacio.easypetvet.controller.ConsultaRotinaController;
import br.gustavoIgnacio.easypetvet.model.*;
import br.gustavoIgnacio.easypetvet.persistencia.*;

public class FragmentListarConsultas extends Fragment {

    private View view;
    private TextView tvResultado;
    private EditText editTextBuscarId;
    private Button buttonBuscar;
    private ConsultaEmergenciaController consultaEmergenciaController;
    private ConsultaRotinaController consultaRotinaController;
    private RadioGroup radioGroup;
    private RadioButton radioEmergencia, radioRotina;

    public FragmentListarConsultas() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_listar_consultas, container, false);

        // Inicializa os elementos
        editTextBuscarId = view.findViewById(R.id.editTextBuscarId);
        buttonBuscar = view.findViewById(R.id.buttonBuscar);
        tvResultado = view.findViewById(R.id.tvListaConsultas);
        tvResultado.setMovementMethod(new ScrollingMovementMethod());

        // Inicializa o RadioGroup e RadioButtons
        radioGroup = view.findViewById(R.id.radioGroup);
        radioEmergencia = view.findViewById(R.id.radioEmergencia);
        radioRotina = view.findViewById(R.id.radioRotina);

        consultaEmergenciaController = new ConsultaEmergenciaController(new ConsultaEmergenciaDAO(view.getContext()));
        consultaRotinaController = new ConsultaRotinaController(new ConsultaRotinaDAO(view.getContext()));

        exibeLista();

        // Configura o botão de busca
        buttonBuscar.setOnClickListener(v -> buscarConsultaPorId());

        return view;
    }

    private void exibeLista() {
        try {
            List<ConsultaEmergencia> consultasEmergencia = consultaEmergenciaController.findALL();
            List<ConsultaRotina> consultasRotina = consultaRotinaController.findALL();
            StringBuilder buffer = new StringBuilder();

            for (ConsultaEmergencia cE : consultasEmergencia) {
                buffer.append(cE.toString()).append("\n");
                buffer.append("____________________________________\n");
            }
            for (ConsultaRotina cR : consultasRotina) {
                buffer.append(cR.toString()).append("\n");
                buffer.append("____________________________________\n");
            }

            tvResultado.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), "Erro ao listar consultas: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void buscarConsultaPorId() {
        String idBusca = editTextBuscarId.getText().toString().trim();

        if (idBusca.isEmpty()) {
            Toast.makeText(getContext(), "Insira um ID para buscar", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tenta buscar nos dois tipos de consulta
        Consulta consulta = null;
        String tipoConsulta = null;

        if (radioEmergencia.isChecked()) {
            consulta = consultaEmergenciaController.findById(idBusca);
            tipoConsulta = "emergencia";
        } else if (radioRotina.isChecked()) {
            consulta = consultaRotinaController.findById(idBusca);
            tipoConsulta = "rotina";
        }

        if (consulta != null) {
            abrirDetalhesConsulta(idBusca, tipoConsulta);
        } else {
            Toast.makeText(getContext(), "Consulta não encontrada", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirDetalhesConsulta(String consultaId, String tipoConsulta) {
        FragmentDetalhesConsulta fragmentDetalhesConsulta = new FragmentDetalhesConsulta();
        Bundle bundle = new Bundle();
        bundle.putString("consultaId", consultaId);
        bundle.putString("tipoConsulta", tipoConsulta);
        fragmentDetalhesConsulta.setArguments(bundle);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragmentDetalhesConsulta);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}