package com.GerenciadorDeMagos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        // CORREÇÃO: Usa o novo construtor completo, com valores padrão (0) para os novos atributos
        Personagem magoDeTeste = new MagoElemental(1, "Gandalf", "Elemental", 100, 100, "Cajado", 50, 30, 1, 1, 55, 0, 0, 0, 0, 0, 0);
        
        gerenciador.adicionar(magoDeTeste);

        assertEquals(1, gerenciador.listarTodos().size());
    }

    @Test
    void testeBuscarMagoPorIdExistente() {
        // CORREÇÃO: Usa o novo construtor completo
        Personagem mago1 = new MagoElemental(10, "Merlin", "Elemental", 100, 100, "Cajado", 50, 30, 1, 1, 60, 0, 0, 0, 0, 0, 0);
        gerenciador.adicionar(mago1);

        // CORREÇÃO: O método buscarPorId retorna um Personagem. Usar o tipo mais geral é mais seguro.
        Personagem magoEncontrado = gerenciador.buscarPorId(10);

        assertNotNull(magoEncontrado);
        assertEquals("Merlin", magoEncontrado.getCodinome());
    }

    @Test
    void testeBuscarMagoPorIdInexistente() {
        // CORREÇÃO: Usar o tipo Personagem
        Personagem magoEncontrado = gerenciador.buscarPorId(999);

        assertNull(magoEncontrado);
    }

    @Test
    void testeCriadorDeMagos() {
        // CORREÇÃO: O CriadorDeMagos agora também pede a velocidade.
        String inputDoUsuario = "1\n" +      // Tipo MagoElemental
                                "101\n" +    // ID
                                "Mago-Teste\n" + // Codinome
                                "150\n" +    // Vida Max
                                "80\n" +     // Mana Max
                                "Orbe\n" +   // Foco
                                "60\n" +     // Poder Base
                                "40\n" +     // Resistência
                                "2\n" +      // Controlador (IA)
                                "50\n";     // Velocidade

        InputStream tecladoFantasma = new ByteArrayInputStream(inputDoUsuario.getBytes());
        Scanner scannerFantasma = new Scanner(tecladoFantasma);

        // Lembre-se de ajustar o CriadorDeMagos para pedir a velocidade e usar o construtor correto
        CriadorDeMagos criador = new CriadorDeMagos(scannerFantasma, gerenciador);

        criador.executar();

        assertEquals(1, gerenciador.listarTodos().size(), "A lista de magos deveria ter 1 mago após a criação.");
        
        Personagem magoCriado = gerenciador.buscarPorId(101);
        assertNotNull(magoCriado, "O mago com ID 101 deveria ter sido encontrado.");
        assertEquals("Mago-Teste", magoCriado.getCodinome(), "O codinome do mago criado está incorreto.");
    }

    @Test
    void testeBuscadorDeMagosEncontraCorretamente() {
        // CORREÇÃO: Usa o novo construtor completo
        Personagem magoExistente = new MagoElemental(77, "AlvoDoTeste", "Elemental", 100, 100, "Cajado", 50, 30, 1, 1, 45, 0, 0, 0, 0, 0, 0);
        gerenciador.adicionar(magoExistente);

        String inputDoUsuario = "77\n";
        InputStream tecladoFantasma = new ByteArrayInputStream(inputDoUsuario.getBytes());
        Scanner scannerFantasma = new Scanner(tecladoFantasma);

        BuscadorDeMagos buscador = new BuscadorDeMagos(gerenciador, scannerFantasma);

        buscador.executar();

        assertEquals(1, gerenciador.listarTodos().size());
    }
}