package service;

import dao.ScoutDAO;
import model.Scout;
import java.util.List;

public class ScoutService {
    private final ScoutDAO dao = new ScoutDAO();

    public void registrarPonto(int jogoId, String equipe, String fundamento) {
        Scout s = new Scout();
        s.setJogoId(jogoId);
        s.setEquipe(equipe);
        s.setFundamento(fundamento);
        dao.inserir(s);
    }

    public List<Scout> listarPorJogo(int jogoId) {
        return dao.listarPorJogo(jogoId);
    }
}