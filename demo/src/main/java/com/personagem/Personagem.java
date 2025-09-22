package com.personagem;

import java.util.ArrayList;

public class Personagem {

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

    private ArrayList<Personagem> personagens = new ArrayList<>();

    public Personagem(int id, String codinome, String escola, int vidaMax, int manaMax, String foco, int poderBase,
            int resistencia, int controlador, int horaEntrada) {
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
        this.vidaAtual = vidaMax;
    }

    public Personagem(){}

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

    public void adicionar(Personagem personagem) {
        personagens.add(personagem);
    }

    public void imprimir() {

        System.out.println("----------------");
        System.out.println("id: " + id);
        System.out.println("Codinome: " + codinome);
        System.out.println("Escola: " + escola);
        System.out.println("Vida máxima: " + vidaMax);
        System.out.println("Mana máxima: " + manaMax);
        System.out.println("Foco: " + foco);
        System.out.println("Poder base: " + poderBase);
        System.out.println("Resistência Mágica: " + resistencia);
        System.out.println("Controlador: " + controlador);
        System.out.println("Hora de entrada na area: " + horaEntrada);

    }

    public void receberDano(int poderBase) {
    }

    
    public void causarDano(Personagem alvo) {
        
    }
    
    public void imprimirVidaAtual() {
        System.out.println("----------------------");
        System.out.println("Vida atual do " + getCodinome()+": "+getVidaAtual());
    }
    
}
