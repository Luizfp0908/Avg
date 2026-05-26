package dao;

import database.ConexaoDB;
import model.Scout;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoutDAO {

    public void inserir(Scout s) {
        String sql = "INSERT INTO scout (jogo_id, equipe, fundamento) VALUES (?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, s.getJogoId());
            ps.setString(2, s.getEquipe());
            ps.setString(3, s.getFundamento());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao registrar scout: " + e.getMessage());
        }
    }

    public List<Scout> listarPorJogo(int jogoId) {
        List<Scout> lista = new ArrayList<>();
        String sql = "SELECT * FROM scout WHERE jogo_id = ? ORDER BY id ASC";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, jogoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Scout s = new Scout();
                    s.setId(rs.getInt("id"));
                    s.setJogoId(rs.getInt("jogo_id"));
                    s.setEquipe(rs.getString("equipe"));
                    s.setFundamento(rs.getString("fundamento"));
                    lista.add(s);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar scout: " + e.getMessage());
        }
        return lista;
    }
}