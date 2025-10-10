package com.feitico;

public class Area extends Magia {




    public Area(String nome, int custoMana, int tempoRecarga, String escola) {
        super(nome, custoMana, tempoRecarga, escola);
        this.setCustoMana(10);
    }



    @Override
    public int calcularDano(String classeMago, int poderBase) {
    int dano = poderBase + (15 * poderBase) / 100;
    if ("Elemental".equalsIgnoreCase(classeMago)) {
        dano += 15;
    }
    if ("Sombrio".equalsIgnoreCase(classeMago)) {
        dano +=5;
        
    }
    return dano;
}

}
