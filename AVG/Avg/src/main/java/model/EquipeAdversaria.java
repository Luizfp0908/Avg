package model;

public class EquipeAdversaria {
    private int id;
    private String nomeEquipe;
    private String categoria;
    private String observacoes;

    public EquipeAdversaria(String castroVôlei, String adulto, String timeTáticoEDefensivo) {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomeEquipe() { return nomeEquipe; }
    public void setNomeEquipe(String nomeEquipe) { this.nomeEquipe = nomeEquipe; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    @Override
    public String toString() { return nomeEquipe + " (" + categoria + ")"; }
}