package com.feitico;

public class Canalizado extends Magia {

    
    
    public Canalizado(String nome, int custoMana, int tempoRecarga, String escola, int danoBase) {
        super(nome, custoMana, tempoRecarga, escola, danoBase);
    }



    @Override
    public int calcularDano(String classeMago) {
        int dano = getDanoBase()+(40*getDanoBase())/100;
        if ("Elemental".equalsIgnoreCase(classeMago)) {
            if (((dano/3)-5)<=0) {
                dano=1;
            }
            else dano=(dano/3)-5;
        }
        return dano;
    }
}
