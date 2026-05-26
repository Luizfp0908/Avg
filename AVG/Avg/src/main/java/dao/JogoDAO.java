package dao;

import database.ConexaoDB;
import model.Jogo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogoDAO {

    public void inserir(Jogo jogo) {
        String sql = "INSERT INTO jogo (data_jogo, equipe_adversaria_id, placar_por_set) VALUES (?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, jogo.getDataJogo());
            ps.setInt(2, jogo.getEquipeAdversariaId());
            ps.setString(3, jogo.getPlacarPorSet());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir jogo: " + e.getMessage());
        }
    }

    public void atualizar(Jogo jogo) {
        String sql = "UPDATE jogo SET data_jogo = ?, equipe_adversaria_id = ?, placar_por_set = ? WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, jogo.getDataJogo());
            ps.setInt(2, jogo.getEquipeAdversariaId());
            ps.setString(3, jogo.getPlacarPorSet());
            ps.setInt(4, jogo.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar jogo: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM jogo WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir jogo: " + e.getMessage());
        }
    }

    public List<Jogo> listarTodos() {
        List<Jogo> lista = new ArrayList<>();
        // O INNER JOIN resolve o problema de mostrar o nome da equipe na tabela, em vez de só o ID
        String sql = "SELECT j.id, j.data_jogo, j.equipe_adversaria_id, j.placar_por_set, e.nome_equipe " +
                "FROM jogo j " +
                "INNER JOIN equipe_adversaria e ON j.equipe_adversaria_id = e.id " +
                "ORDER BY j.id DESC";

        try (Statement stmt = ConexaoDB.getConexao().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Jogo jogo = new Jogo();
                jogo.setId(rs.getInt("id"));
                jogo.setDataJogo(rs.getString("data_jogo"));
                jogo.setEquipeAdversariaId(rs.getInt("equipe_adversaria_id"));
                jogo.setPlacarPorSet(rs.getString("placar_por_set"));

                // Esse atributo transiente serve só para a TableView do JavaFX
                jogo.setNomeAdversaria(rs.getString("nome_equipe"));

                lista.add(jogo);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar jogos: " + e.getMessage());
        }
        return lista;
    }
}