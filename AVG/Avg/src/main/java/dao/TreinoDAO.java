package dao;

import database.ConexaoDB;
import model.Treino;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreinoDAO {

    public void inserir(Treino t) {
        String sql = "INSERT INTO treino (data_treino, duracao_minutos, foco_principal) VALUES (?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, t.getDataTreino());
            ps.setInt(2, t.getDuracaoMinutos());
            ps.setString(3, t.getFocoPrincipal());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir treino: " + e.getMessage());
        }
    }

    public void atualizar(Treino t) {
        String sql = "UPDATE treino SET data_treino = ?, duracao_minutos = ?, foco_principal = ? WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, t.getDataTreino());
            ps.setInt(2, t.getDuracaoMinutos());
            ps.setString(3, t.getFocoPrincipal());
            ps.setInt(4, t.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar treino: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM treino WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir treino: " + e.getMessage());
        }
    }

    public List<Treino> listarTodos() {
        List<Treino> lista = new ArrayList<>();
        String sql = "SELECT * FROM treino ORDER BY id DESC"; // Traz os mais recentes primeiro
        try (Statement stmt = ConexaoDB.getConexao().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Treino t = new Treino();
                t.setId(rs.getInt("id"));
                t.setDataTreino(rs.getString("data_treino"));
                t.setDuracaoMinutos(rs.getInt("duracao_minutos"));
                t.setFocoPrincipal(rs.getString("foco_principal"));
                lista.add(t);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar treinos: " + e.getMessage());
        }
        return lista;
    }
}