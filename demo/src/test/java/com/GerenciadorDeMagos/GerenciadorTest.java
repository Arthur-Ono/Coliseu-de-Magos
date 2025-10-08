package com.GerenciadorDeMagos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.personagem.MagoElemental;
import com.personagem.Ranqueados;

public class GerenciadorTest {

    private Gerenciador gerenciador; 

    @BeforeEach
    void setUp() {
        gerenciador = new Gerenciador();
    }

    @Test
    void testeAdicionarMagoEVerificarTamanhoDaLista() {
        
        Ranqueados magoDeTeste = new MagoElemental(1, "Gandalf", 100, 100, "Cajado", 50, 30, 1, 0);

    
        gerenciador.adicionar(magoDeTeste);

        
        assertEquals(1, gerenciador.listarTodos().size());
    }

    @Test
    void testeBuscarMagoPorIdExistente() {
        
        Ranqueados mago1 = new MagoElemental(10, "Merlin", 100, 100, "Cajado", 50, 30, 1, 0);
        gerenciador.adicionar(mago1);

        
        Ranqueados magoEncontrado = gerenciador.buscarPorId(10);

        
        assertNotNull(magoEncontrado);
        
        assertEquals("Merlin", magoEncontrado.getCodinome());
    }

    @Test
    void testeBuscarMagoPorIdInexistente() {
        
        Ranqueados magoEncontrado = gerenciador.buscarPorId(999);

        
        assertNull(magoEncontrado);
    }
}