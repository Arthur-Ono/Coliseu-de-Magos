package com.personagem;

public class MagoElemental extends Ranqueados {

    /**
     * CONSTRUTOR 1: O Construtor de "Nascimento".
     * Usado pelo CriadorDeMagos para criar um personagem novo em folha.
     * Ele recebe apenas os atributos básicos e define todos os de ranking como 0.
     */
    public MagoElemental(int id, String codinome, int vidaMax, int manaMax, String foco, int poderBase, 
                         int resistencia, int controlador, int horaEntrada, int velocidade) {
        
        // Chama o construtor da classe mãe (Ranqueados) com os dados recebidos.
        // A escola é fixa como "Elemental" e todos os 6 atributos de ranking começam como 0.
        super(id, codinome, "Elemental", vidaMax, manaMax, foco, poderBase, resistencia, controlador, 
              horaEntrada, velocidade, 0, 0, 0, 0, 0, 0);
    }

    /**
     * CONSTRUTOR 2: O Construtor de "Carregamento".
     * Usado pelo GerenciadorCSV para recriar um personagem a partir dos dados de um arquivo.
     * Ele recebe TODOS os atributos, incluindo os de ranking.
     */
    public MagoElemental(int id, String codinome, String escola, int vidaMax, int manaMax, String foco, int poderBase,
                         int resistencia, int controlador, int horaEntrada, int velocidade, int abates, int assistencias, 
                         int danoCausado, int danoMitigado, int rupturas, int capturas) {
        
        // Simplesmente passa todos os dados recebidos para o construtor da classe mãe (Ranqueados).
        super(id, codinome, escola, vidaMax, manaMax, foco, poderBase, resistencia, controlador, horaEntrada,
              velocidade, abates, assistencias, danoCausado, danoMitigado, rupturas, capturas);
    }
}