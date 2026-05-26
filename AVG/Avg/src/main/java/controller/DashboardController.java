package controller;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import service.AtletaService;
import service.EquipeAdversariaService;
import service.JogoService;
import service.TreinoService;

public class DashboardController {

    public Scene criarCena(Stage stage) {
        // A sua classe Sidebar cuida do menu lateral
        VBox sidebar = Sidebar.criar(stage, "Dashboard");

        Label titulo = new Label("Dashboard AVV Performance");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titulo.setTextFill(Color.WHITE); // Mantendo o tema escuro

        // Cards usando a paleta de cores AVV (tons de roxo/magenta)
        HBox cards = new HBox(20,
                criarCard("👤 Atletas", obterTotalAtletas() + " registrados", "#7c3aed"),
                criarCard("🏆 Adversários", obterTotalAdversarios() + " equipes", "#5b21b6"),
                criarCard("🎯 Jogos", obterTotalJogos() + " partidas", "#4c1d95"),
                criarCard("💪 Treinos", obterTotalTreinos() + " sessões", "#3b0764")
        );

        VBox conteudo = new VBox(30, titulo, cards);
        conteudo.setPadding(new Insets(35));
        // O fundo escuro conectando com a interface do Login
        conteudo.setStyle("-fx-background-color: #2d1b69;");

        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setCenter(conteudo);
        return new Scene(root, 1050, 680);
    }

    private VBox criarCard(String titulo, String subtitulo, String cor) {
        Label tit = new Label(titulo);
        tit.setTextFill(Color.WHITE);
        tit.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Label sub = new Label(subtitulo);
        sub.setTextFill(Color.web("#cbd5e1")); // Cor de subtítulo suave
        sub.setFont(Font.font("Arial", 14));

        VBox card = new VBox(10, tit, sub);
        card.setPrefSize(190, 110);
        card.setPadding(new Insets(20));

        // Estilo inline com bordas arredondadas e sombra de profundidade
        card.setStyle(
                "-fx-background-color: " + cor + "; " +
                        "-fx-background-radius: 12; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);"
        );
        return card;
    }

    // --- MÉTODOS DE SEGURANÇA (Evita crash se o banco estiver vazio ou offline) ---

    private String obterTotalAtletas() {
        try { return String.valueOf(new AtletaService().listarTodos().size()); }
        catch (Exception e) { return "0"; }
    }

    private String obterTotalAdversarios() {
        try { return String.valueOf(new EquipeAdversariaService().listarTodos().size()); }
        catch (Exception e) { return "0"; }
    }

    private String obterTotalJogos() {
        try { return String.valueOf(new JogoService().listarTodos().size()); }
        catch (Exception e) { return "0"; }
    }

    private String obterTotalTreinos() {
        try { return String.valueOf(new TreinoService().listarTodos().size()); }
        catch (Exception e) { return "0"; }
    }
}