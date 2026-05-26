package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PopularDB {
    private static final String URL = "jdbc:sqlite:avv_performance.db";

    public static void popular() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            // Verifica se já existem atletas para não inserir duplicados
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM atletas");
            if (rs.next() && rs.getInt(1) == 0) {

                stmt.execute("INSERT INTO atletas (nome, posicao, data_nascimento) VALUES " +
                        "('João Silva', 'Ponteiro', '2005-05-15')," +
                        "('Lucas Oliveira', 'Levantador', '2004-08-22')," +
                        "('Pedro Santos', 'Libero', '2006-02-10');");

                stmt.execute("INSERT INTO treinos (data_treino, duracao_minutos) VALUES " +
                        "('2026-05-20', 120)," +
                        "('2026-05-22', 90);");

                System.out.println("Banco populado com sucesso!");
            } else {
                System.out.println("Dados já existentes, pulando população.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao popular banco: " + e.getMessage());
        }
    }
}