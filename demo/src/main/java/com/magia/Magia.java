package com.magia;

import java.util.ArrayList;

public class Magia {

    private String nome;
    private String afinidadeEscola;
    private int tempoRecarga;
    private int custoMana;
    private String alcance;

    ArrayList<Magia> Grimorio = new ArrayList<>();

    public Magia(String nome, String afinidadeEscola, int tempoRecarga, int custoMana, String alcance) {
        this.nome = nome;
        this.afinidadeEscola = afinidadeEscola;
        this.tempoRecarga = tempoRecarga;
        this.custoMana = custoMana;
        this.alcance = alcance;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAfinidadeEscola() {
        return afinidadeEscola;
    }

    public void setAfinidadeEscola(String afinidadeEscola) {
        this.afinidadeEscola = afinidadeEscola;
    }

    public int getTempoRecarga() {
        return tempoRecarga;
    }

    public void setTempoRecarga(int tempoRecarga) {
        this.tempoRecarga = tempoRecarga;
    }

    public int getCustoMana() {
        return custoMana;
    }

    public void setCustoMana(int custoMana) {
        this.custoMana = custoMana;
    }

    public String getAlcance() {
        return alcance;
    }

    public void setAlcance(String alcance) {
        this.alcance = alcance;
    }

    
     
}
