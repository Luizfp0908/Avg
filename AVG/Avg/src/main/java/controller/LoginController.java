package controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginController {
    public Scene criarCena(Stage stage) {
        // 1. Ícone / Logo
        Label lblIcon = new Label("🏐");
        lblIcon.setFont(Font.font(60));
        // Sombra leve no ícone para dar um efeito de "brilho"
        DropShadow glow = new DropShadow(15, Color.web("#7c3aed"));
        lblIcon.setEffect(glow);

        // 2. Títulos
        Label lblTitulo = new Label("AVV Performance");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        lblTitulo.setTextFill(Color.WHITE);

        Label lblSubtitulo = new Label("Gestão Esportiva de Alta Performance");
        lblSubtitulo.setFont(Font.font("Arial", 14));
        lblSubtitulo.setTextFill(Color.web("#a78bfa")); // Roxo claro
        VBox.setMargin(lblSubtitulo, new Insets(-10, 0, 20, 0)); // Aproxima o subtítulo do título

        // 3. Campos de Texto (Modernos)
        String estiloCampo = "-fx-background-color: #2d1b69; " +
                "-fx-text-fill: white; " +
                "-fx-prompt-text-fill: #8b5cf6; " +
                "-fx-border-color: #4a30a3; " +
                "-fx-border-radius: 8; " +
                "-fx-background-radius: 8; " +
                "-fx-padding: 10px;";

        String estiloCampoFocus = estiloCampo.replace("#4a30a3", "#a78bfa"); // Borda clareia ao clicar

        TextField fUsuario = new TextField();
        fUsuario.setPromptText("Usuário");
        fUsuario.setMaxWidth(300);
        fUsuario.setPrefHeight(45);
        fUsuario.setStyle(estiloCampo);
        fUsuario.focusedProperty().addListener((obs, oldVal, newVal) ->
                fUsuario.setStyle(newVal ? estiloCampoFocus : estiloCampo)
        );

        PasswordField fSenha = new PasswordField();
        fSenha.setPromptText("Senha");
        fSenha.setMaxWidth(300);
        fSenha.setPrefHeight(45);
        fSenha.setStyle(estiloCampo);
        fSenha.focusedProperty().addListener((obs, oldVal, newVal) ->
                fSenha.setStyle(newVal ? estiloCampoFocus : estiloCampo)
        );

        // 4. Botão de Ação
        Button btnEntrar = new Button("ENTRAR");
        btnEntrar.setMaxWidth(300);
        btnEntrar.setPrefHeight(45);
        btnEntrar.setStyle("-fx-background-color: #7c3aed; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;");

        // Efeito Hover no botão
        btnEntrar.setOnMouseEntered(e -> btnEntrar.setStyle("-fx-background-color: #6d28d9; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;"));
        btnEntrar.setOnMouseExited(e -> btnEntrar.setStyle("-fx-background-color: #7c3aed; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;"));

        // Lógica de Login
        btnEntrar.setOnAction(e -> {
            if(fUsuario.getText().equals("avv") && fSenha.getText().equals("avv123")) {
                stage.setScene(new DashboardController().criarCena(stage));
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Credenciais inválidas! (Use avv / avv123)");
                alerta.setHeaderText("Falha na Autenticação");
                alerta.showAndWait();
            }
        });

        // 5. O Cartão Central
        VBox card = new VBox(15, lblIcon, lblTitulo, lblSubtitulo, fUsuario, fSenha, new Region(), btnEntrar);
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(420);
        card.setPadding(new Insets(50));
        card.setStyle("-fx-background-color: #1e1145; -fx-background-radius: 20;");

        // Sombra no cartão
        DropShadow sombraCard = new DropShadow();
        sombraCard.setColor(Color.rgb(0, 0, 0, 0.5));
        sombraCard.setRadius(25);
        sombraCard.setOffsetY(10);
        card.setEffect(sombraCard);

        // 6. O Fundo com Gradiente
        StackPane root = new StackPane(card);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a0f3d, #3b2285);");

        return new Scene(root, 1050, 680);
    }
}