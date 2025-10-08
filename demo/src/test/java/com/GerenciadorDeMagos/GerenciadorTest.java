package com.GerenciadorDeMagos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.personagem.MagoElemental;
import com.personagem.Personagem;
import com.servicos.BuscadorDeMagos;
import com.servicos.CriadorDeMagos;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
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
    @Test
    void testeCriadorDeMagos() {
        String inputDoUsuario = "1\n" +
                                "101\n" +
                                "Mago-Teste\n" +
                                "150\n" +
                                "80\n" +
                                "Orbe\n" +
                                "60\n" +
                                "40\n" +
                                "2\n" +
                                "99\n";

        InputStream tecladoFantasma = new ByteArrayInputStream(inputDoUsuario.getBytes());
        Scanner scannerFantasma = new Scanner(tecladoFantasma);

        CriadorDeMagos criador = new CriadorDeMagos(scannerFantasma, gerenciador);

        criador.executar();

        assertEquals(1, gerenciador.listarTodos().size(), "A lista de magos deveria ter 1 mago após a criação.");
        
        Personagem magoCriado = gerenciador.buscarPorId(101);
        assertNotNull(magoCriado, "O mago com ID 101 deveria ter sido encontrado.");
        assertEquals("Mago-Teste", magoCriado.getCodinome(), "O codinome do mago criado está incorreto.");
    }

    @Test
    void testeBuscadorDeMagosEncontraCorretamente() {
        Personagem magoExistente = new MagoElemental(77, "AlvoDoTeste", 100, 100, "Cajado", 50, 30, 1, 0);
        gerenciador.adicionar(magoExistente);

        String inputDoUsuario = "77\n";
        InputStream tecladoFantasma = new ByteArrayInputStream(inputDoUsuario.getBytes());
        Scanner scannerFantasma = new Scanner(tecladoFantasma);

        BuscadorDeMagos buscador = new BuscadorDeMagos(gerenciador, scannerFantasma);

        buscador.executar();

        assertEquals(1, gerenciador.listarTodos().size());
    }
}