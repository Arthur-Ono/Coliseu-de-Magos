package com.feitiço;

public class Area extends Magia {




    public Area(String nome, int custoMana, int tempoRecarga, String escola, int danoBase) {
        super(nome, custoMana, tempoRecarga, escola, danoBase);
    }



    @Override
    public int calcularDano(String classeMago) {
        int dano = getDanoBase();
        if ("Elemental".equalsIgnoreCase(classeMago)) {
            dano += 10;
        }
        return dano;
    }

}
