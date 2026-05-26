package model;

public class Atleta {
    private int id;
    private String nomeCompleto;
    private String dataNascimento;
    private int idade;
    private int numeroCamisa;
    private String posicao;
    private String categoriaPrincipal;
    private String categoriaSecundaria;
    private double altura;
    private double peso;
    private double envergadura;
    private double alcanceParado;
    private double alcanceSaltando;
    private double bloqueioParado;
    private double bloqueioSaltando;
    private double saltoHorizontal1Pe;
    private double saltoHorizontal2Pes;
    private double velocidadeDeslocamento;
    private double flexibilidade;

    public Atleta() {}

    // Getters e Setters (Pode gerar automaticamente na sua IDE para economizar tempo)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }
    public int getNumeroCamisa() { return numeroCamisa; }
    public void setNumeroCamisa(int numeroCamisa) { this.numeroCamisa = numeroCamisa; }
    public String getPosicao() { return posicao; }
    public void setPosicao(String posicao) { this.posicao = posicao; }
    public String getCategoriaPrincipal() { return categoriaPrincipal; }
    public void setCategoriaPrincipal(String categoriaPrincipal) { this.categoriaPrincipal = categoriaPrincipal; }
    public String getCategoriaSecundaria() { return categoriaSecundaria; }
    public void setCategoriaSecundaria(String categoriaSecundaria) { this.categoriaSecundaria = categoriaSecundaria; }
    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public double getEnvergadura() { return envergadura; }
    public void setEnvergadura(double envergadura) { this.envergadura = envergadura; }
    public double getAlcanceParado() { return alcanceParado; }
    public void setAlcanceParado(double alcanceParado) { this.alcanceParado = alcanceParado; }
    public double getAlcanceSaltando() { return alcanceSaltando; }
    public void setAlcanceSaltando(double alcanceSaltando) { this.alcanceSaltando = alcanceSaltando; }
    public double getBloqueioParado() { return bloqueioParado; }
    public void setBloqueioParado(double bloqueioParado) { this.bloqueioParado = bloqueioParado; }
    public double getBloqueioSaltando() { return bloqueioSaltando; }
    public void setBloqueioSaltando(double bloqueioSaltando) { this.bloqueioSaltando = bloqueioSaltando; }
    public double getSaltoHorizontal1Pe() { return saltoHorizontal1Pe; }
    public void setSaltoHorizontal1Pe(double saltoHorizontal1Pe) { this.saltoHorizontal1Pe = saltoHorizontal1Pe; }
    public double getSaltoHorizontal2Pes() { return saltoHorizontal2Pes; }
    public void setSaltoHorizontal2Pes(double saltoHorizontal2Pes) { this.saltoHorizontal2Pes = saltoHorizontal2Pes; }
    public double getVelocidadeDeslocamento() { return velocidadeDeslocamento; }
    public void setVelocidadeDeslocamento(double velocidadeDeslocamento) { this.velocidadeDeslocamento = velocidadeDeslocamento; }
    public double getFlexibilidade() { return flexibilidade; }
    public void setFlexibilidade(double flexibilidade) { this.flexibilidade = flexibilidade; }

    @Override
    public String toString() { return "#" + numeroCamisa + " - " + nomeCompleto; }
}