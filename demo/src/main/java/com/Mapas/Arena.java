package com.Mapas;

import java.util.List;
import java.util.Random;

public abstract class Arena {

    protected String nome;
    protected List<CondicaoDeCampo> condicoesPossiveis;
    private Random sorteador = new Random();

    public CondicaoDeCampo sortearCondicao() {
        if (condicoesPossiveis == null || condicoesPossiveis.isEmpty()) {
            return new CondicaoDeCampo("Clima Normal", "CLIMA_NORMAL");
        }
        int indexSorteado = sorteador.nextInt(condicoesPossiveis.size());
        return condicoesPossiveis.get(indexSorteado);
    }

    public String getNome() {
        return this.nome;
    }
}