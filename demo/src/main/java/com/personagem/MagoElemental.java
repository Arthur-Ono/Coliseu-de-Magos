package com.personagem;

public class MagoElemental extends Ranqueados {

    

    public MagoElemental(int id, String codinome, int vidaMax, int manaMax, String foco, int poderBase, int resistencia, int controlador, int velocidade) {
        super(id, codinome, "Elemental", vidaMax, manaMax, foco, poderBase, resistencia, controlador, 2, 0, 0, 0, 0, 0, 0, 0);
        this.setVelocidade(velocidade);
        this.setEscola("Elemental");
        this.setAbates(0);
        this.setCapturas(0);
        this.setDanoCausado(0);
        this.setDanoMitigado(0);
        this.setAssistencias(0);
        this.setRupturas(0);
    }
}