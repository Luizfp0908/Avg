package service;

import dao.TreinoDAO;
import model.Treino;
import java.util.List;

public class TreinoService {
    private final TreinoDAO dao = new TreinoDAO();

    public void salvar(Treino treino) {
        if (treino.getId() == 0) {
            dao.inserir(treino);
        } else {
            dao.atualizar(treino);
        }
    }

    public void excluir(Treino treino) {
        if (treino != null && treino.getId() > 0) {
            dao.excluir(treino.getId());
        }
    }

    public List<Treino> listarTodos() {
        return dao.listarTodos();
    }
}