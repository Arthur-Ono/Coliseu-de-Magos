package com.Mapas;

import java.util.ArrayList;

public class LabirintoIlusorio extends Arena {

    public LabirintoIlusorio() {
        this.nome = "Labirinto Ilusório";
        this.condicoesPossiveis = new ArrayList<>();

        this.condicoesPossiveis.add(CatalogoDeCondicoes.TEMPESTADE_DE_MANA);
        this.condicoesPossiveis.add(CatalogoDeCondicoes.SOLO_SAGRADO);
        this.condicoesPossiveis.add(CatalogoDeCondicoes.PANTANO_SOMBRIO);
    }
}