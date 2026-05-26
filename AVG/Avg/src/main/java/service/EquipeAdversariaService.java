package service;

import dao.EquipeAdversariaDAO;
import model.EquipeAdversaria;
import java.util.List;

public class EquipeAdversariaService {
    private final EquipeAdversariaDAO dao = new EquipeAdversariaDAO();

    public void salvar(EquipeAdversaria equipe) {
        if (equipe.getId() == 0) {
            dao.inserir(equipe);
        } else {
            dao.atualizar(equipe);
        }
    }

    public void excluir(EquipeAdversaria equipe) {
        if (equipe != null && equipe.getId() > 0) {
            dao.excluir(equipe.getId());
        }
    }

    public List<EquipeAdversaria> listarTodos() {
        return dao.listarTodos();
    }
}