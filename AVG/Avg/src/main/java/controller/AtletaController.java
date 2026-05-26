package controller;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Atleta;
import service.AtletaService;

import java.util.Optional;

public class AtletaController {
    private final AtletaService service = new AtletaService();
    private final TableView<Atleta> tabela = new TableView<>();

    public Scene criarCena(Stage stage) {
        VBox sidebar = Sidebar.criar(stage, "Atletas");

        // Título
        Label titulo = new Label("Gestão de Atletas");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titulo.setTextFill(Color.WHITE);

        // Configuração das Colunas
        TableColumn<Atleta, Integer> colCamisa = new TableColumn<>("Nº");
        colCamisa.setCellValueFactory(new PropertyValueFactory<>("numeroCamisa"));
        colCamisa.setMaxWidth(50);

        TableColumn<Atleta, String> colNome = new TableColumn<>("Nome Completo");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));

        TableColumn<Atleta, String> colPos = new TableColumn<>("Posição");
        colPos.setCellValueFactory(new PropertyValueFactory<>("posicao"));

        tabela.getColumns().addAll(colCamisa, colNome, colPos);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        carregarTabela();

        // --- BARRA DE FERRAMENTAS (Ação em cima da tabela) ---
        Button btnNovo = criarBotao("+ Novo Atleta", "#10b981", "#059669"); // Verde
        btnNovo.setOnAction(e -> abrirFormulario(null));

        Button btnEditar = criarBotao("✏️ Editar", "#f59e0b", "#d97706"); // Amarelo/Laranja
        btnEditar.setOnAction(e -> {
            Atleta selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                abrirFormulario(selecionado);
            } else {
                new Alert(Alert.AlertType.WARNING, "Selecione um atleta na tabela para editar!").showAndWait();
            }
        });

        Button btnExcluir = criarBotao("🗑️ Excluir", "#ef4444", "#dc2626"); // Vermelho
        btnExcluir.setOnAction(e -> excluirAtleta());

        HBox toolbar = new HBox(10, btnNovo, btnEditar, btnExcluir);

        VBox conteudo = new VBox(15, titulo, toolbar, tabela);
        conteudo.setPadding(new Insets(35));
        conteudo.setStyle("-fx-background-color: #2d1b69;"); // Fundo escuro

        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setCenter(conteudo);
        return new Scene(root, 1050, 680);
    }

    // Método utilitário para criar botões bonitos e padronizados
    private Button criarBotao(String texto, String corNormal, String corHover) {
        Button btn = new Button(texto);
        String estiloBase = "-fx-background-color: " + corNormal + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;";
        String estiloHover = "-fx-background-color: " + corHover + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;";

        btn.setStyle(estiloBase);
        btn.setOnMouseEntered(e -> btn.setStyle(estiloHover));
        btn.setOnMouseExited(e -> btn.setStyle(estiloBase));
        return btn;
    }

    // Formulário Inteligente (Serve para Criar e Editar)
    private void abrirFormulario(Atleta atletaParaEditar) {
        Stage s = new Stage();
        s.setTitle(atletaParaEditar == null ? "Cadastrar Novo Atleta" : "Editar Atleta");
        s.initModality(Modality.APPLICATION_MODAL);
        s.setResizable(false);

        Label lblNome = new Label("Nome Completo:"); lblNome.setTextFill(Color.WHITE);
        Label lblCamisa = new Label("Nº da Camisa:"); lblCamisa.setTextFill(Color.WHITE);
        Label lblPos = new Label("Posição:"); lblPos.setTextFill(Color.WHITE);

        TextField fNome = new TextField();
        TextField fCamisa = new TextField();
        TextField fPos = new TextField();

        // Se for edição, preenche os campos com os dados do atleta selecionado
        if (atletaParaEditar != null) {
            fNome.setText(atletaParaEditar.getNomeCompleto());
            fCamisa.setText(String.valueOf(atletaParaEditar.getNumeroCamisa()));
            fPos.setText(atletaParaEditar.getPosicao());
        }

        Button btnSalvar = criarBotao("Salvar Atleta", "#7c3aed", "#6d28d9");
        btnSalvar.setMaxWidth(Double.MAX_VALUE);

        btnSalvar.setOnAction(e -> {
            if (fNome.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "O nome do atleta é obrigatório!").showAndWait();
                return;
            }

            Atleta a = (atletaParaEditar == null) ? new Atleta() : atletaParaEditar;
            a.setNomeCompleto(fNome.getText());
            a.setPosicao(fPos.getText());
            try {
                a.setNumeroCamisa(Integer.parseInt(fCamisa.getText()));
            } catch (NumberFormatException ex) {
                a.setNumeroCamisa(0);
            }

            service.salvar(a);
            carregarTabela();
            s.close();
        });

        VBox layout = new VBox(10, lblNome, fNome, lblCamisa, fCamisa, lblPos, fPos, new Region(), btnSalvar);
        layout.setPadding(new Insets(25));
        layout.setStyle("-fx-background-color: #1e1145;");

        s.setScene(new Scene(layout, 350, 350));
        s.show();
    }

    private void excluirAtleta() {
        Atleta selecionado = tabela.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            new Alert(Alert.AlertType.WARNING, "Selecione um atleta na tabela para excluir!").showAndWait();
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Deseja realmente excluir " + selecionado.getNomeCompleto() + "?");
        confirmacao.setContentText("Esta ação não poderá ser desfeita.");

        Optional<ButtonType> resultado = confirmacao.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            service.excluir(selecionado);
            carregarTabela();
        }
    }

    private void carregarTabela() {
        try {
            tabela.setItems(FXCollections.observableArrayList(service.listarTodos()));
        } catch (Exception e) {
            System.out.println("Erro ao carregar banco: " + e.getMessage());
        }
    }
}