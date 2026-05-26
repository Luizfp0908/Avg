package model;

public class Jogada {
    private int id;
    private int jogoId;
    private int treinoId;
    private int setNumero;
    private int numeroJogada;
    private String equipeAnalisada;
    private String observacao;

    public Jogada() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getJogoId() { return jogoId; }
    public void setJogoId(int jogoId) { this.jogoId = jogoId; }
    public int getTreinoId() { return treinoId; }
    public void setTreinoId(int treinoId) { this.treinoId = treinoId; }
    public int getSetNumero() { return setNumero; }
    public void setSetNumero(int setNumero) { this.setNumero = setNumero; }
    public int getNumeroJogada() { return numeroJogada; }
    public void setNumeroJogada(int numeroJogada) { this.numeroJogada = numeroJogada; }
    public String getEquipeAnalisada() { return equipeAnalisada; }
    public void setEquipeAnalisada(String equipeAnalisada) { this.equipeAnalisada = equipeAnalisada; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}