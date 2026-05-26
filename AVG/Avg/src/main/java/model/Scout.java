package model;

public class Scout {
    private int id = 0;
    private int jogoId;
    private String equipe;     // Ex: "AVV" ou "Adversário"
    private String fundamento; // Ex: "Ataque", "Bloqueio", "Saque", "Erro do Adversário"

    public Scout() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getJogoId() { return jogoId; }
    public void setJogoId(int jogoId) { this.jogoId = jogoId; }
    public String getEquipe() { return equipe; }
    public void setEquipe(String equipe) { this.equipe = equipe; }
    public String getFundamento() { return fundamento; }
    public void setFundamento(String fundamento) { this.fundamento = fundamento; }
}