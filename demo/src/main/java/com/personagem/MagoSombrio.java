package com.personagem;

public class MagoSombrio extends Ranqueados {

    /**
     * CONSTRUTOR 1: O Construtor de "Nascimento".
     * Usado pelo CriadorDeMagos.
     */
    public MagoSombrio(int id, String codinome, int vidaMax, int manaMax, String foco, int poderBase, 
                       int resistencia, int controlador, int horaEntrada, int velocidade) {
        
        // Chama o construtor da classe mãe (Ranqueados), definindo a escola como "Sombrio".
        super(id, codinome, "Sombrio", vidaMax, manaMax, foco, poderBase, resistencia, controlador, 
              horaEntrada, velocidade, 0,0, 0, 0, 0, 0, 0, 0,0);
    }

    /**
     * CONSTRUTOR 2: O Construtor de "Carregamento".
     * Usado pelo GerenciadorCSV.
     */
    public MagoSombrio(int id, String codinome, String escola, int vidaMax, int manaMax, String foco, int poderBase, int resistencia, int controlador, int horaEntrada, int velocidade, float acerto, float critico, int abates, int assistencias, int danoCausado, int danoMitigado, int rupturas, int capturas, int tempoEmCombate) {
        
        // Simplesmente passa todos os dados recebidos para o construtor da classe mãe (Ranqueados).
        super(id, codinome, escola, vidaMax, manaMax, foco, poderBase, resistencia, controlador, horaEntrada, velocidade, acerto, critico, abates, assistencias, danoCausado, danoMitigado, rupturas, capturas, tempoEmCombate);
    }
}