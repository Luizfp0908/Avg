package service;

import dao.JogoDAO;
import dao.ScoutDAO; // Importante para buscar os dados de scout
import model.Jogo;
import model.Scout;
import java.util.List;

public class JogoService {
    private final JogoDAO dao = new JogoDAO();
    private final ScoutDAO scoutDao = new ScoutDAO(); // Adicionamos o DAO de Scout aqui

    public void salvar(Jogo jogo) {
        if (jogo.getId() == 0) {
            dao.inserir(jogo);
        } else {
            dao.atualizar(jogo);
        }
    }

    public void excluir(Jogo jogo) {
        if (jogo != null && jogo.getId() > 0) {
            dao.excluir(jogo.getId());
        }
    }

    public List<Jogo> listarTodos() {
        return dao.listarTodos();
    }

    // --- NOVO MÉTODO PARA O PDF ---
    public void exportarRelatorioParaPDF(Jogo jogo) {
        // 1. Busca todos os scouts daquele jogo no banco
        List<Scout> scouts = scoutDao.listarPorJogo(jogo.getId());

        // 2. Chama a classe de exportação que criamos
        new ExportacaoService().gerarRelatorioScoutPDF(
                "Relatório de Scout - Jogo: " + jogo.getDataJogo() + " vs " + jogo.getNomeAdversaria(),
                scouts,
                "Relatorio_Jogo_" + jogo.getId() + ".pdf"
        );
    }
}