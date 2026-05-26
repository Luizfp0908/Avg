package dao;

import database.ConexaoDB;
import model.EquipeAdversaria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeAdversariaDAO {

    public void inserir(EquipeAdversaria equipe) {
        String sql = "INSERT INTO equipe_adversaria (nome_equipe, categoria, observacoes) VALUES (?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, equipe.getNomeEquipe());
            ps.setString(2, equipe.getCategoria());
            ps.setString(3, equipe.getObservacoes());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir equipe: " + e.getMessage());
        }
    }

    public void atualizar(EquipeAdversaria equipe) {
        String sql = "UPDATE equipe_adversaria SET nome_equipe = ?, categoria = ?, observacoes = ? WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, equipe.getNomeEquipe());
            ps.setString(2, equipe.getCategoria());
            ps.setString(3, equipe.getObservacoes());
            ps.setInt(4, equipe.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar equipe: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM equipe_adversaria WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir equipe: " + e.getMessage());
        }
    }

    public List<EquipeAdversaria> listarTodos() {
        List<EquipeAdversaria> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipe_adversaria ORDER BY nome_equipe";
        try (Statement stmt = ConexaoDB.getConexao().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                EquipeAdversaria equipe = new EquipeAdversaria("Castro Vôlei", "Adulto", "Time tático e defensivo");
                equipe.setId(rs.getInt("id"));
                equipe.setNomeEquipe(rs.getString("nome_equipe"));
                equipe.setCategoria(rs.getString("categoria"));
                equipe.setObservacoes(rs.getString("observacoes"));
                lista.add(equipe);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar equipes: " + e.getMessage());
        }
        return lista;
    }
}