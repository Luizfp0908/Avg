package dao;

import database.ConexaoDB;
import model.Jogada;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogadaDAO {
    public int inserir(Jogada j) {
        String sql = "INSERT INTO jogada (jogo_id, treino_id, set_numero, equipe_analisada, observacao) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, j.getJogoId() == 0 ? null : j.getJogoId());
            ps.setObject(2, j.getTreinoId() == 0 ? null : j.getTreinoId());
            ps.setInt(3, j.getSetNumero());
            ps.setString(4, j.getEquipeAnalisada());
            ps.setString(5, j.getObservacao());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Erro ao inserir jogada: " + e.getMessage());
        }
        return 0;
    }

    public List<Jogada> listarPorContexto(String campo, int id) {
        List<Jogada> lista = new ArrayList<>();
        String sql = "SELECT * FROM jogada WHERE " + campo + " = ? ORDER BY id DESC";
        try (PreparedStatement ps = ConexaoDB.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Jogada j = new Jogada();
                j.setId(rs.getInt("id"));
                j.setSetNumero(rs.getInt("set_numero"));
                j.setEquipeAnalisada(rs.getString("equipe_analisada"));
                lista.add(j);
            }
        } catch (SQLException e) {
            System.err.println("Erro listar jogadas: " + e.getMessage());
        }
        return lista;
    }
}