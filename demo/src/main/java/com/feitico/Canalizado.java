package com.feitico;

public class Canalizado extends Magia {

    
    
    public Canalizado(String nome, int custoMana, int tempoRecarga, String escola) {
        super(nome, custoMana, tempoRecarga, escola);
        this.setCustoMana(3);
    }



    @Override
    public int calcularDano(String classeMago, int poderBase) {
    int dano = poderBase + (40 * poderBase) / 100;
    if ("Elemental".equalsIgnoreCase(classeMago)) {
        dano -= 10;
    }
    if ("Sombrio".equalsIgnoreCase(classeMago)) {
        dano+=10;
    }
    return dano;
}
}
