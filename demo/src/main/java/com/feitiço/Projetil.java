package com.feitiço;


public class Projetil extends Magia {



    public Projetil(String nome, int custoMana, int tempoRecarga, String escola, int danoBase) {
        super(nome, custoMana, tempoRecarga, escola, danoBase);
    }


    

    @Override
    public int calcularDano(String classeMago) {
        int dano = getDanoBase();
        if ("Arcano".equalsIgnoreCase(classeMago)) {
            dano+=10;
        }
        return dano;
    }

}
