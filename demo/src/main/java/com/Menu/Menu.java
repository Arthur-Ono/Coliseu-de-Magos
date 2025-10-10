package com.Menu;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList; // Import necessário para a correção do agendamento

import com.personagem.Ranqueados;
import com.GerenciadorDeMagos.Gerenciador;
import com.csv.GerenciadorCSV;
import com.servicos.*; // Importa todos os serviços
import com.Agenda.*; // Importa as classes da Agenda
//import com.Mapas.*;    // Importa as classes dos Mapas

public class Menu {

    // Instancia um objeto 'scan' da classe Scanner para ler o que o usuário digita
    // no console.
    Scanner scan = new Scanner(System.in);
    // Cria o especialista em lidar com arquivos CSV.
    private GerenciadorCSV gerenciadorCSV = new GerenciadorCSV();
    // Define o nome padrão do arquivo CSV como uma constante para não errar a
    // digitação.
    private final String NOME_ARQUIVO = "magos.csv";

    // O método principal que controla o fluxo do jogo.
    // Ele recebe o 'gerenciador' principal, que guarda a lista de magos.
    public void menuPrincipal(Gerenciador gerenciador) {

        // Instancia os gerenciadores do sistema de agendamento e o "relógio" do jogo.
        ControladorDeTurno relogio = new ControladorDeTurno();
        GerenciadorDeAgendamentos gerenciadorAgendamentos = new GerenciadorDeAgendamentos();

        // Cria cada "especialista" (serviço) uma única vez, antes do jogo começar.
        // Isso é mais eficiente do que criar um novo a cada ação no menu.
        CriadorDeMagos criadorDeMagos = new CriadorDeMagos(scan, gerenciador);
        ListadorDeMagos listadorDeMagos = new ListadorDeMagos(gerenciador, scan);
        OrganizadorDeDuelos organizadorDeDuelos = new OrganizadorDeDuelos(scan, gerenciador);
        BuscadorDeMagos buscadorDeMagos = new BuscadorDeMagos(gerenciador, scan);
        AgendadorDeDuelo agendadorDeDuelo = new AgendadorDeDuelo(scan, gerenciador, gerenciadorAgendamentos, relogio);
        CriadorDeMagias criadorDeMagias = new CriadorDeMagias(gerenciador, scan);

        // 'running' é uma flag que controla se o jogo deve continuar rodando.
        boolean running = true;
        // O loop principal do jogo. Enquanto 'running' for verdadeiro, o menu continua
        // aparecendo.
        while (running) {
            // Imprime o cabeçalho do menu, mostrando o turno atual para o jogador.
            System.out.println("\n--- MENU PRINCIPAL | TURNO: " + relogio.getTurnoAtual() + " ---");
            System.out.println("1. Criar Mago");
            System.out.println("2. Listar Magos");
            System.out.println("3. Agendar Duelo");
            System.out.println("4. Iniciar Duelo Imediato");
            System.out.println("5. Salvar Magos em CSV");
            System.out.println("6. Carregar Magos de CSV");
            System.out.println("7. Buscar Mago por ID");
            System.out.println("8. Gerenciar Grimório de um Mago.");
            System.out.println("9. Imprimir relatório por ID");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            // Lê o número que o usuário digitou.
            int opcao = scan.nextInt();
            // Limpa o "Enter" que o usuário digitou, para evitar bugs na próxima leitura.
            scan.nextLine();

            // O 'switch' direciona o programa para a ação que o usuário escolheu.
            switch (opcao) {
                case 1:
                    // Chama o especialista em criar magos para fazer seu trabalho.
                    criadorDeMagos.executar();
                    break;
                case 2:
                    // Chama o especialista em listar magos.
                    listadorDeMagos.executar();
                    break;
                case 3:
                    // Chama o especialista em agendar duelos.
                    agendadorDeDuelo.executar();
                    break;
                case 4:
                    // Chama o especialista em organizar duelos imediatos.
                    organizadorDeDuelos.executar();
                    break;
                case 5:
                    // Chama o gerenciador de CSV para salvar a lista atual de magos.
                    gerenciadorCSV.salvar(gerenciador.listarTodos(), NOME_ARQUIVO);
                    System.out.println("Magos salvos com sucesso em " + NOME_ARQUIVO);
                    break;
                case 6:
                    List<Ranqueados> magosCarregados = gerenciadorCSV.carregar(NOME_ARQUIVO);
                    // Atualiza a lista principal do jogo com os magos carregados do arquivo.
                    gerenciador.setListaDeMagos(magosCarregados);
                    System.out.println("Magos carregados com sucesso de " + NOME_ARQUIVO);
                    System.out.println(magosCarregados.size() + " magos foram carregados.");
                    break;
                case 7:
                    // Chama o especialista em buscar magos.
                    buscadorDeMagos.executar();
                    break;
                case 8:
                    // Gerencia o grimório do mago
                    criadorDeMagias.executar();
                    break;
                case 9:
                    System.out.print("Digite o ID do mago para imprimir o relatório: ");
                    int idBusca = scan.nextInt();
                    scan.nextLine(); // Limpa o buffer
                    Ranqueados magoEncontrado = gerenciador.buscarPorId(idBusca);
                    if (magoEncontrado != null) {
                        magoEncontrado.imprimirRanking();
                    } else {
                        System.out.println("Mago não encontrado!");
                    }
                    break;

                case 0:
                    // Se o usuário digitar 0, a flag 'running' se torna falsa e o loop 'while'
                    // termina.
                    System.out.println("Saindo do programa...");
                    running = false;
                    break;
                default:
                    // Se o usuário digitar qualquer outro número.
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

            // Se o usuário escolheu sair, o 'break' impede que a lógica de turno avance.
            if (!running) {
                break;
            }

            // A cada ação completada no menu, o tempo do jogo avança.
            relogio.avancarTurno();

            // Após avançar o turno, verifica na agenda se há algum duelo marcado para o
            // novo turno.
            List<Agendamento> duelosDoTurno = gerenciadorAgendamentos
                    .verificarEObterAgendamentosParaTurno(relogio.getTurnoAtual());

            // Se a lista de duelos para o turno não estiver vazia...
            if (!duelosDoTurno.isEmpty()) {
                System.out.println("\n ATENÇÃO! Um duelo agendado está começando! ");
                // Passa por cada duelo agendado para hoje.
                for (Agendamento ag : duelosDoTurno) {
                    System.out.println("Iniciando duelo agendado na arena: " + ag.getArena().getNome());

                    List<Ranqueados> time1Agendado = new ArrayList<>();
                    for (Ranqueados p : ag.getTime1()) {
                        time1Agendado.add((Ranqueados) p);
                    }

                    List<Ranqueados> time2Agendado = new ArrayList<>();
                    for (Ranqueados p : ag.getTime2()) {
                        time2Agendado.add((Ranqueados) p);
                    }

                    // Agora sim, chamamos o método com os tipos corretos.
                    organizadorDeDuelos.iniciarDuelo(time1Agendado, time2Agendado, ag.getArena());
                }
                System.out.println("Pressione Enter para continuar...");
                scan.nextLine();
            }
        }
    }
}