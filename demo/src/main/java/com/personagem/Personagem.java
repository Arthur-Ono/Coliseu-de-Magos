package com.personagem;

public abstract class Personagem {

    private int id;
    private String codinome;
    private String escola;
    private int vidaMax;
    private int manaMax;
    private String foco;
    private int poderBase;
    private int resistencia;
    private int controlador;
    private int horaEntrada;
    private int vidaAtual = vidaMax;
    private int manaAtual = manaMax;
    private int velocidade;
    private int manaAtual; 

    public Personagem(int id, String codinome, String escola, int vidaMax, int manaMax, String foco, int poderBase,
                      int resistencia, int controlador, int horaEntrada, int velocidade) {
        this.id = id;
        this.codinome = codinome;
        this.escola = escola;
        this.vidaMax = vidaMax;
        this.manaMax = manaMax;
        this.foco = foco;
        this.poderBase = poderBase;
        this.resistencia = resistencia;
        this.controlador = controlador;
        this.horaEntrada = horaEntrada;
        this.velocidade = velocidade;
        
        this.vidaAtual = vidaMax;
        this.manaAtual = manaMax;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodinome() {
        return codinome;
    }

    public void setCodinome(String codinome) {
        this.codinome = codinome;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public int getVidaMax() {
        return vidaMax;
    }

    public void setVidaMax(int vidaMax) {
        this.vidaMax = vidaMax;
    }

    public int getManaMax() {
        return manaMax;
    }

    public void setManaMax(int manaMax) {
        this.manaMax = manaMax;
    }

    public String getFoco() {
        return foco;
    }

    public void setFoco(String foco) {
        this.foco = foco;
    }

    public int getPoderBase() {
        return poderBase;
    }

    public void setPoderBase(int poderBase) {
        this.poderBase = poderBase;
    }

    public int getResistencia() {
        return resistencia;
    }

    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }

    public int getControlador() {
        return controlador;
    }

    public void setControlador(int controlador) {
        this.controlador = controlador;
    }

    public int getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(int horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public void receberDano(int poderBase) {
    }

    public void causarDano(Personagem alvo) {
    }
    
    public void imprimirVidaAtual() {
        System.out.println("----------------------");
        System.out.println("Vida atual do " + getCodinome() + ": " + getVidaAtual());
    }

    @Override
    public String toString() {
        return "----------------\n" +
               "ID: " + id + "\n" +
               "Codinome: " + codinome + "\n" +
               "Escola: " + escola + "\n" +
               "Vida Máxima: " + vidaMax + "\n" +
               "Mana Máxima: " + manaMax + "\n" +
               "Foco: " + foco + "\n" +
               "Poder Base: " + poderBase + "\n" +
               "Resistência Mágica: " + resistencia + "\n" +
               "Controlador: " + controlador + "\n" +
               "Velocidade: " + velocidade + "\n" +
               "----------------";
    }

    public int getManaAtual() {
        return manaAtual;
    }

    public void setManaAtual(int manaAtual) {
        this.manaAtual = manaAtual;
    }

}

    public void setManaAtual(int manaAtual) {
        this.manaAtual = manaAtual;
    }
}