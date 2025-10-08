package com.GerenciadorDeMagos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.Mapas.Arena;
import com.Mapas.CamaraDoArquimago;
import com.Mapas.CatalogoDeCondicoes;
import com.Mapas.CondicaoDeCampo;
import com.Mapas.GerenciadorDeArenas;

public class ArenaTest {

    @Test
    void testeGerenciadorDeArenasCriaTresArenas() {
        GerenciadorDeArenas gerenciadorArenas = new GerenciadorDeArenas();

        List<Arena> arenas = gerenciadorArenas.getArenas();

        assertNotNull(arenas, "A lista de arenas não deveria ser nula.");
        assertEquals(3, arenas.size(), "O gerenciador deveria criar exatamente 3 arenas.");
    }

    @Test
    void testeSorteioDeCondicaoRetornaCondicaoValida() {
        Arena camara = new CamaraDoArquimago();

        List<CondicaoDeCampo> todasAsCondicoesPossiveis = CatalogoDeCondicoes.getTodasAsCondicoes();

        CondicaoDeCampo condicaoSorteada = camara.sortearCondicao();

        assertNotNull(condicaoSorteada, "A condição sorteada não deveria ser nula.");

        assertTrue(todasAsCondicoesPossiveis.contains(condicaoSorteada),
            "A condição sorteada '" + condicaoSorteada.getNome() + "' não é uma das condições válidas do catálogo.");
    }
}