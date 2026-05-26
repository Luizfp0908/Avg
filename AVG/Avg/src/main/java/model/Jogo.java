package model;

public class Jogo {
    private int id = 0;
    private String dataJogo;
    private int equipeAdversariaId; // Vai para o banco de dados
    private String placarPorSet;

    // Atributo "Transiente" (Não vai para o banco, serve apenas para exibir na tabela do JavaFX)
    private String nomeAdversaria;

    public Jogo() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDataJogo() { return dataJogo; }
    public void setDataJogo(String dataJogo) { this.dataJogo = dataJogo; }

    public int getEquipeAdversariaId() { return equipeAdversariaId; }
    public void setEquipeAdversariaId(int equipeAdversariaId) { this.equipeAdversariaId = equipeAdversariaId; }

    public String getPlacarPorSet() { return placarPorSet; }
    public void setPlacarPorSet(String placarPorSet) { this.placarPorSet = placarPorSet; }

    public String getNomeAdversaria() { return nomeAdversaria; }
    public void setNomeAdversaria(String nomeAdversaria) { this.nomeAdversaria = nomeAdversaria; }
}