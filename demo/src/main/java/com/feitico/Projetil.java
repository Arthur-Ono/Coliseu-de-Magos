package com.feitico;


public class Projetil extends Magia {



    public Projetil(String nome, int custoMana, int tempoRecarga, String escola) {
        super(nome, custoMana, tempoRecarga, escola);
        this.setCustoMana(5);
    }


    

    @Override
    public int calcularDano(String classeMago, int poderBase) {
    int dano = poderBase + (25 * poderBase) / 100;
    if ("Arcano".equalsIgnoreCase(classeMago)) {
        dano += 10;
    }
    return dano;
}

}
