package com.personagem;

public class MagoArcano extends Ranqueados {

    /**
     * CONSTRUTOR 1: O Construtor de "Nascimento".
     * Usado pelo CriadorDeMagos.
     */
    public MagoArcano(int id, String codinome, int vidaMax, int manaMax, String foco, int poderBase, 
                      int resistencia, int controlador, int horaEntrada, int velocidade) {
        
        // Chama o construtor da classe mãe (Ranqueados), definindo a escola como "Arcano".
        super(id, codinome, "Arcano", vidaMax, manaMax, foco, poderBase, resistencia, controlador, 
              horaEntrada, velocidade, 0,0, 0, 0, 0, 0, 0, 0,0);
    }

    /**
     * CONSTRUTOR 2: O Construtor de "Carregamento".
     * Usado pelo GerenciadorCSV.
     */
    public MagoArcano(int id, String codinome, String escola, int vidaMax, int manaMax, String foco, int poderBase, int resistencia, int controlador, int horaEntrada, int velocidade, float acerto, float critico, int abates, int assistencias, int danoCausado, int danoMitigado, int rupturas, int capturas, int tempoEmCombate) {
        
        // Simplesmente passa todos os dados recebidos para o construtor da classe mãe (Ranqueados).
        super(id, codinome, escola, vidaMax, manaMax, foco, poderBase, resistencia, controlador, horaEntrada, velocidade, acerto, critico, abates, assistencias, danoCausado, danoMitigado, rupturas, capturas, tempoEmCombate);
    }
}