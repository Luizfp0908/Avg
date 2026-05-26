package controller;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AnaliseController {
    private final int jogoId;
    private final int setAtual;

    public AnaliseController(int jogoId, int setAtual) {
        this.jogoId = jogoId;
        this.setAtual = setAtual;
    }

    public Scene criarCena(Stage stage) {
        VBox sidebar = Sidebar.criar(stage, "Jogos");

        Label titulo = new Label("📈 Análise de Desempenho - Jogo ID: " + jogoId);
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titulo.setTextFill(Color.WHITE);

        Label aviso = new Label("Integração com gráficos e exportação para PDF em construção...");
        aviso.setTextFill(Color.web("#cbd5e1"));
        aviso.setFont(Font.font("Arial", 16));

        Button btnVoltar = new Button("⬅ Voltar para Jogos");
        btnVoltar.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 15 8 15;");
        btnVoltar.setOnMouseEntered(e -> btnVoltar.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 15 8 15;"));
        btnVoltar.setOnMouseExited(e -> btnVoltar.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 15 8 15;"));
        btnVoltar.setOnAction(e -> stage.setScene(new JogoController().criarCena(stage)));

        VBox conteudo = new VBox(20, titulo, aviso, btnVoltar);
        conteudo.setPadding(new Insets(35));
        conteudo.setStyle("-fx-background-color: #2d1b69;");

        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setCenter(conteudo);
        return new Scene(root, 1050, 680);
    }
}