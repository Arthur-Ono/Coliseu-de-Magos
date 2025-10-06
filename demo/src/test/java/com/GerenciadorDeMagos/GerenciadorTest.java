package com.GerenciadorDeMagos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import com.personagem.Personagem;

public class GerenciadorTest {

    private Gerenciador gerenciador; 

    @BeforeEach
    void setUp() {
        gerenciador = new Gerenciador();
    }

    @Test
    void testeAdicionarMagoEVerificarTamanhoDaLista() {
        
        Personagem magoDeTeste = new MagoElemental(1, "Gandalf", 100, 100, "Cajado", 50, 30, 1, 0);

    
        gerenciador.adicionar(magoDeTeste);

        
        assertEquals(1, gerenciador.listarTodos().size());
    }

    @Test
    void testeBuscarMagoPorIdExistente() {
        
        Personagem mago1 = new MagoElemental(10, "Merlin", 100, 100, "Cajado", 50, 30, 1, 0);
        gerenciador.adicionar(mago1);

        
        Personagem magoEncontrado = gerenciador.buscarPorId(10);

        
        assertNotNull(magoEncontrado);
        
        assertEquals("Merlin", magoEncontrado.getCodinome());
    }

    @Test
    void testeBuscarMagoPorIdInexistente() {
        
        Personagem magoEncontrado = gerenciador.buscarPorId(999);

        
        assertNull(magoEncontrado);
    }
}