package com.GerenciadorDeMagos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Scanner;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.personagem.MagoElemental;
import com.personagem.Ranqueados;
import com.servicos.AgendadorDeDuelo;
import com.servicos.ControladorDeTurno;
import com.Agenda.Agendamento;
import com.Agenda.GerenciadorDeAgendamentos;
import com.Mapas.Arena;

public class AgendamentoTest {

    // Declaro as versões "de mentira" (mocks) das classes que o Agendador precisa para funcionar.
    private Gerenciador gerenciadorMock;
    private GerenciadorDeAgendamentos gerenciadorAgendamentosMock;
    private ControladorDeTurno controladorDeTurnoMock;
    
    // Declaro a classe que eu realmente quero testar.
    private AgendadorDeDuelo agendadorDeDuelo;

    // O @BeforeEach faz com que este método seja executado antes de CADA teste.
    // É como "limpar a mesa" antes de começar um novo experimento.
    @BeforeEach
    void setUp() {
        // Aqui eu crio as instâncias "de mentira" usando o Mockito.
        // A partir de agora, eu tenho total controle sobre como essas classes falsas se comportam.
        gerenciadorMock = mock(Gerenciador.class);
        gerenciadorAgendamentosMock = mock(GerenciadorDeAgendamentos.class);
        controladorDeTurnoMock = mock(ControladorDeTurno.class);
    }

    @Test
    // O nome do teste deve dizer o que ele faz e qual o resultado esperado.
    // Este testa o "caminho feliz", onde tudo dá certo.
    void executar_QuandoInputValido_DeveAgendarDueloCorretamente() {
        
        // --- ARRANGE (Preparar o Cenário) ---
        // Eu preciso preparar tudo que o método 'executar' vai precisar.

        // 1. Preparo um "teclado fantasma" com tudo que o usuário digitaria.
        String inputSimulado = "1\n" + // Escolhe a primeira Arena (índice 0)
                               "1\n" + // Tamanho do time: 1v1
                               "101\n" +// ID do mago do Time 1
                               "202\n" +// ID do mago do Time 2
                               "5\n";  // Agendar para daqui a 5 turnos
        Scanner scannerFantasma = new Scanner(inputSimulado);

        // 2. Crio os magos que o meu 'gerenciadorMock' vai "encontrar".
        Ranqueados mago1 = new MagoElemental(101, "Gandalf", "Elemental", 100, 100, "Cajado", 50, 30, 1, 1, 55, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Ranqueados mago2 = new MagoElemental(202, "Saruman", "Elemental", 100, 100, "Cajado", 50, 30, 1, 1, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        // 3. "Ensino" os mocks a se comportarem.
        when(gerenciadorMock.buscarPorId(101)).thenReturn(mago1);
        when(gerenciadorMock.buscarPorId(202)).thenReturn(mago2);
        when(controladorDeTurnoMock.getTurnoAtual()).thenReturn(1);
        // "Quando o agendador perguntar se a arena está ocupada, responda 'false' (não está)."
        when(gerenciadorAgendamentosMock.isArenaOcupada(any(Arena.class), anyInt())).thenReturn(false);
        
        // 4. Finalmente, crio o AgendadorDeDuelo real, mas entrego para ele as ferramentas falsas.
        agendadorDeDuelo = new AgendadorDeDuelo(scannerFantasma, gerenciadorMock, gerenciadorAgendamentosMock, controladorDeTurnoMock);

        // --- ACT (Agir) ---
        agendadorDeDuelo.executar();

        // --- ASSERT (Verificar) ---
        ArgumentCaptor<Agendamento> agendamentoCaptor = ArgumentCaptor.forClass(Agendamento.class);
        verify(gerenciadorAgendamentosMock, times(1)).adicionarAgendamento(agendamentoCaptor.capture());
        Agendamento agendamentoRealizado = agendamentoCaptor.getValue();
        
        assertNotNull(agendamentoRealizado);
        assertEquals(1, agendamentoRealizado.getTime1().size());
        assertEquals("Gandalf", agendamentoRealizado.getTime1().get(0).getCodinome());
        assertEquals("Saruman", agendamentoRealizado.getTime2().get(0).getCodinome());
        assertEquals(6, agendamentoRealizado.getTurnoAgendado());
    }

    // --- NOVO TESTE ADICIONADO ---
    @Test
    // Testo o caso onde o usuário tenta agendar um duelo em uma arena e turno que já estão ocupados.
    void executar_QuandoArenaJaOcupada_NaoDeveAgendar() {
        // --- ARRANGE ---

        // 1. Preparo um input de usuário que, em outras circunstâncias, seria perfeitamente válido.
        String inputSimulado = "1\n" + "1\n" + "101\n" + "202\n" + "5\n";
        Scanner scannerFantasma = new Scanner(inputSimulado);

        // 2. Preparo os magos.
        Ranqueados mago1 = new MagoElemental(101, "Gandalf", "Elemental", 100, 100, "Cajado", 50, 30, 1, 1, 55, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Ranqueados mago2 = new MagoElemental(202, "Saruman", "Elemental", 100, 100, "Cajado", 50, 30, 1, 1, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        // 3. "Ensino" os mocks, e aqui está a parte crucial:
        when(gerenciadorMock.buscarPorId(101)).thenReturn(mago1);
        when(gerenciadorMock.buscarPorId(202)).thenReturn(mago2);
        when(controladorDeTurnoMock.getTurnoAtual()).thenReturn(1);
        
        // "Quando o AgendadorDeDuelo perguntar ao GerenciadorDeAgendamentos se a arena X no turno 6 está ocupada,
        // quero que você MINTA e responda 'true' (sim, está ocupada)."
        // any(Arena.class) -> para qualquer arena.
        // eq(6) -> especificamente para o turno 6.
        when(gerenciadorAgendamentosMock.isArenaOcupada(any(Arena.class), eq(6))).thenReturn(true);

        // 4. Crio o agendador com as ferramentas "mentirosas".
        agendadorDeDuelo = new AgendadorDeDuelo(scannerFantasma, gerenciadorMock, gerenciadorAgendamentosMock, controladorDeTurnoMock);
        
        // --- ACT ---
        agendadorDeDuelo.executar();

        // --- ASSERT ---
        // A verificação é simples: se a lógica de validação funcionou, o método 'adicionarAgendamento'
        // NUNCA deve ter sido chamado.
        verify(gerenciadorAgendamentosMock, never()).adicionarAgendamento(any(Agendamento.class));
    }

    @Test
    // Testo um "caminho infeliz": o usuário digita um ID de mago que não existe.
    void executar_QuandoIdMagoInvalido_NaoDeveAgendar() {
        // ARRANGE
        String inputSimulado = "1\n" + "1\n" + "999\n";
        Scanner scannerFantasma = new Scanner(inputSimulado);
        when(gerenciadorMock.buscarPorId(999)).thenReturn(null);
        agendadorDeDuelo = new AgendadorDeDuelo(scannerFantasma, gerenciadorMock, gerenciadorAgendamentosMock, controladorDeTurnoMock);

        // ACT
        agendadorDeDuelo.executar();

        // ASSERT
        verify(gerenciadorAgendamentosMock, never()).adicionarAgendamento(any(Agendamento.class));
    }
    
    @Test
    // Testo outro "caminho infeliz": o usuário agenda para um turno no passado.
    void executar_QuandoTurnoNoFuturoNegativo_NaoDeveAgendar() {
        // ARRANGE
        String inputSimulado = "1\n" + "1\n" + "101\n" + "202\n" + "-5\n";
        Scanner scannerFantasma = new Scanner(inputSimulado);
        
        Ranqueados mago1 = new MagoElemental(101, "Gandalf", "Elemental", 100, 100, "Cajado", 50, 30, 1, 1, 55, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Ranqueados mago2 = new MagoElemental(202, "Saruman", "Elemental", 100, 100, "Cajado", 50, 30, 1, 1, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        when(gerenciadorMock.buscarPorId(101)).thenReturn(mago1);
        when(gerenciadorMock.buscarPorId(202)).thenReturn(mago2);

        agendadorDeDuelo = new AgendadorDeDuelo(scannerFantasma, gerenciadorMock, gerenciadorAgendamentosMock, controladorDeTurnoMock);

        // ACT
        agendadorDeDuelo.executar();

        // ASSERT
        verify(gerenciadorAgendamentosMock, never()).adicionarAgendamento(any(Agendamento.class));
    }
}