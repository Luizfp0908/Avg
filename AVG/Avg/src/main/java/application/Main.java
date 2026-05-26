package application;

import controller.LoginController;
import database.DataSeeder;
import database.InicializadorDB;
import database.PopularDB;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // 1. Inicializa o banco e dados
        InicializadorDB.inicializar();
        DataSeeder.popularSeVazio();
        PopularDB.popular();

        // 2. Configura a janela
        stage.setTitle("AVV Performance");
        stage.setResizable(true);
        stage.setMinWidth(950);
        stage.setMinHeight(620);

        // 3. Inicia com a cena vinda do LoginController
        // O CSS será vinculado dentro do método criarCena do LoginController
        stage.setScene(new LoginController().criarCena(stage));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}