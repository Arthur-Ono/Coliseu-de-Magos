package com.feitico;

public abstract class Magia {

    private String nome;
    private int custoMana;
    private int tempoRecarga;
    private String escola;
    

    public Magia(String nome, int custoMana, int tempoRecarga, String escola) {
        this.nome = nome;
        this.custoMana = custoMana;
        this.tempoRecarga = tempoRecarga;
        this.escola = escola;
    }

    public Magia() {
    }



    public abstract int calcularDano(String classeMago, int poderBase);

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCustoMana() {
        return custoMana;
    }

    public void setCustoMana(int custoMana) {
        this.custoMana = custoMana;
    }

    public int getTempoRecarga() {
        return tempoRecarga;
    }

    public void setTempoRecarga(int tempoRecarga) {
        this.tempoRecarga = tempoRecarga;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

  

    

}
