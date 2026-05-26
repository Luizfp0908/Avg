package model;

public class Treino {
    private int id = 0;
    private String dataTreino;
    private int duracaoMinutos;
    private String focoPrincipal;

    public Treino() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDataTreino() { return dataTreino; }
    public void setDataTreino(String dataTreino) { this.dataTreino = dataTreino; }

    public int getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(int duracaoMinutos) { this.duracaoMinutos = duracaoMinutos; }

    public String getFocoPrincipal() { return focoPrincipal; }
    public void setFocoPrincipal(String focoPrincipal) { this.focoPrincipal = focoPrincipal; }
}