package com.Menu;

import java.util.List;
import java.util.Scanner;
import com.personagem.Personagem;
import com.GerenciadorDeMagos.Gerenciador;
import com.csv.GerenciadorCSV;
import com.servicos.*; // Importa todos os serviços
import com.Agenda.*;   // Importa as classes da Agenda
import com.Mapas.*;    // Importa as classes dos Mapas

public class Menu {

    Scanner scan = new Scanner(System.in);
    private GerenciadorCSV gerenciadorCSV = new GerenciadorCSV(); 
    private final String NOME_ARQUIVO = "magos.csv";

    public void menuPrincipal(Gerenciador gerenciador) {

        ControladorDeTurno relogio = new ControladorDeTurno();
        GerenciadorDeAgendamentos gerenciadorAgendamentos = new GerenciadorDeAgendamentos();

        CriadorDeMagos criadorDeMagos = new CriadorDeMagos(scan, gerenciador);
        ListadorDeMagos listadorDeMagos = new ListadorDeMagos(gerenciador, scan);
        OrganizadorDeDuelos organizadorDeDuelos = new OrganizadorDeDuelos(scan, gerenciador);
        BuscadorDeMagos buscadorDeMagos = new BuscadorDeMagos(gerenciador, scan);
        AgendadorDeDuelo agendadorDeDuelo = new AgendadorDeDuelo(scan, gerenciador, gerenciadorAgendamentos, relogio);
        
        boolean running = true;
        while (running) {
            System.out.println("\n--- MENU PRINCIPAL | TURNO: " + relogio.getTurnoAtual() + " ---"); 
            System.out.println("1. Criar Mago");
            System.out.println("2. Listar Magos");
            System.out.println("3. Agendar Duelo");
            System.out.println("4. Iniciar Duelo Imediato"); 
            System.out.println("5. Salvar Magos em CSV");
            System.out.println("6. Carregar Magos de CSV");
            System.out.println("7. Buscar Mago por ID");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scan.nextInt();
            scan.nextLine(); 

            switch (opcao) {
                case 1:
                    criadorDeMagos.executar(); 
                    break;
                case 2:
                    listadorDeMagos.executar(); 
                    break;
                case 3:
                    agendadorDeDuelo.executar(); 
                    break;
                case 4:
                    organizadorDeDuelos.executar(); 
                    break;
                case 5: 
                    gerenciadorCSV.salvar(gerenciador.listarTodos(), NOME_ARQUIVO);
                    System.out.println("Magos salvos com sucesso em " + NOME_ARQUIVO);
                    break;
                case 6: 
                    List<Personagem> magosCarregados = gerenciadorCSV.carregar(NOME_ARQUIVO);
                    gerenciador.setListaDeMagos(magosCarregados);
                    System.out.println("Magos carregados com sucesso de " + NOME_ARQUIVO);
                    System.out.println(magosCarregados.size() + " magos foram carregados.");
                    break;
                case 7:
                    buscadorDeMagos.executar(); 
                    break;
                case 0:
                    System.out.println("Saindo do programa...");
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

            if (!running) { 
                break;
            }

           
            relogio.avancarTurno();

            List<Agendamento> duelosDoTurno = gerenciadorAgendamentos.verificarEObterAgendamentosParaTurno(relogio.getTurnoAtual());

            if (!duelosDoTurno.isEmpty()) {
                System.out.println("\n🔔🔔🔔 ATENÇÃO! Um duelo agendado está começando! 🔔🔔🔔");
                for (Agendamento ag : duelosDoTurno) {
                    System.out.println("Iniciando duelo agendado na arena: " + ag.getArena().getNome());
                    // Usamos o organizador de duelos para executar a batalha agendada
                    organizadorDeDuelos.iniciarDuelo(ag.getTime1(), ag.getTime2(), ag.getArena());
                }
                System.out.println("Pressione Enter para continuar...");
                scan.nextLine();
            }
           
        }
    }
}