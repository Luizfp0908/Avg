package dao;

import database.ConexaoDB;
import model.Atleta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtletaDAO {

    public void inserir(Atleta a) {
        String sql = "INSERT INTO atleta (nome_completo, numero_camisa, posicao) VALUES (?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, a.getNomeCompleto());
            ps.setInt(2, a.getNumeroCamisa());
            ps.setString(3, a.getPosicao());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir atleta: " + e.getMessage());
        }
    }

    public List<Atleta> listarTodos() {
        List<Atleta> lista = new ArrayList<>();
        String sql = "SELECT * FROM atleta ORDER BY nome_completo";
        try (Statement stmt = ConexaoDB.getConexao().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Atleta a = new Atleta();
                a.setId(rs.getInt("id"));
                a.setNomeCompleto(rs.getString("nome_completo"));
                a.setNumeroCamisa(rs.getInt("numero_camisa"));
                a.setPosicao(rs.getString("posicao"));
                lista.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar atletas: " + e.getMessage());
        }
        return lista;
    }

    public void atualizar(Atleta a) {
        String sql = "UPDATE atleta SET nome_completo = ?, numero_camisa = ?, posicao = ? WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setString(1, a.getNomeCompleto());
            ps.setInt(2, a.getNumeroCamisa());
            ps.setString(3, a.getPosicao());
            ps.setInt(4, a.getId()); // O ID filtra para alterar apenas o jogador selecionado
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar atleta: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM atleta WHERE id = ?";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir atleta: " + e.getMessage());
        }
    }
}