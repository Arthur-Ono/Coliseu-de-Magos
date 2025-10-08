package com.Mapas;

import java.util.ArrayList;
import java.util.List;

public class CatalogoDeCondicoes {

    
    public static final CondicaoDeCampo TEMPESTADE_DE_MANA = new CondicaoDeCampo("Tempestade de Mana", "AUMENTA_DANO");

    public static final CondicaoDeCampo SOLO_SAGRADO = new CondicaoDeCampo("SOLO SAGRADO", "AUMENTA DEFESA");

    public static final CondicaoDeCampo PANTANO_SOMBRIO = new CondicaoDeCampo("Pântano Sombrio","REDUZ_VELOCIDADE");

    public static List<CondicaoDeCampo> getTodasAsCondicoes() {
        List<CondicaoDeCampo> todas = new ArrayList<>();
        todas.add(TEMPESTADE_DE_MANA);
        todas.add(SOLO_SAGRADO);
        todas.add(PANTANO_SOMBRIO);
        return todas;
    }
}