package com.personagem;

public class MagoElemental extends Ranqueados {

    private int alto;

    public MagoElemental(int id, String codinome, String escola, int vidaMax, int manaMax, String foco, int poderBase, int resistencia, int controlador, int horaEntrada, int abates, int assistencias, int danoCausado, int danoMitigado, int rupturas, int capturas, int alto) {
        super(id, codinome, escola, vidaMax, manaMax, foco, poderBase, resistencia, controlador, horaEntrada, abates, assistencias, danoCausado, danoMitigado, rupturas, capturas);
        
        
        this.alto = alto;
        this.setEscola("Elemental");
        this.setAbates(0);
        this.setCapturas(0);
        this.setDanoCausado(0);
        this.setDanoMitigado(0);
        this.setAssistencias(0);
        this.setRupturas(0);
    }

    public MagoElemental(int id, String codinome, int vidaMax, int manaMax, String foco, int poderBase, int resistencia, int controlador, int alto) {
        this(id, codinome, "Elemental", vidaMax, manaMax, foco, poderBase, resistencia, controlador, 2, 0, 0, 0, 0, 0, 0, alto);
    }
    public int getAlto(){
        return this.alto;
    }
}