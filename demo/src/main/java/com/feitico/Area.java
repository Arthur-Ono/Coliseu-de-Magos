package com.feitico;

public class Area extends Magia {




    public Area(String nome, int custoMana, int tempoRecarga, String escola, int danoBase) {
        super(nome, custoMana, tempoRecarga, escola, danoBase);
    }



    @Override
    public int calcularDano(String classeMago) {
        int dano = getDanoBase()+(15*getDanoBase())/100;
        if ("Elemental".equalsIgnoreCase(classeMago)) {
            dano += 10;
        }
        return dano;
    }

}
