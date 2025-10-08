package com.Mapas;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorDeArenas {
    private List<Arena> arenas = new ArrayList<>();

    public GerenciadorDeArenas() {
        arenas.add(new CirculoRunico());
        arenas.add(new LabirintoIlusorio());
        arenas.add(new CamaraDoArquimago());
    }

    public List<Arena> getArenas() {
        return this.arenas;
    }
}