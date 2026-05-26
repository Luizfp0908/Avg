package service;

import dao.AtletaDAO;
import model.Atleta;
import java.util.ArrayList;
import java.util.List;

public class AtletaService {
    private final AtletaDAO dao = new AtletaDAO();

    public void salvar(Atleta a) {
        // Se o ID for 0, é um atleta novo que veio do formulário em branco
        if (a.getId() == 0) {
            dao.inserir(a);
        } else {
            // Se já possui um ID (maior que 0), significa que veio da seleção da TableView para edição
            dao.atualizar(a);
        }
    }

    public void excluir(Atleta a) {
        // Validação de segurança: só envia para o DAO se o atleta existir e tiver um ID válido do banco
        if (a != null && a.getId() > 0) {
            dao.excluir(a.getId());
        }
    }

    public List<Atleta> listarTodos() {
        return dao.listarTodos();
    }

    // Mantido o método de simulação da equipe técnica para testes de partidas
    public List<Atleta> gerarAtletasAdversarios() {
        List<Atleta> adversarios = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            Atleta a = new Atleta();
            a.setId(-i); // IDs negativos diferenciam os mocks dos atletas reais do banco
            a.setNomeCompleto("Oponente #" + i);
            a.setNumeroCamisa(i);
            adversarios.add(a);
        }
        return adversarios;
    }
}