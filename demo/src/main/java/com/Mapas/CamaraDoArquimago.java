package com.Mapas;

import java.util.ArrayList;

public class CamaraDoArquimago extends Arena {

    public CamaraDoArquimago() {
        this.nome = "Câmara do Arquimago";
        this.condicoesPossiveis = new ArrayList<>();

        this.condicoesPossiveis.add(CatalogoDeCondicoes.TEMPESTADE_DE_MANA);
        this.condicoesPossiveis.add(CatalogoDeCondicoes.SOLO_SAGRADO);
        this.condicoesPossiveis.add(CatalogoDeCondicoes.PANTANO_SOMBRIO);
    }
}