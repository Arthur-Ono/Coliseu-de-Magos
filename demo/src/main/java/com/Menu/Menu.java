package com.Menu;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList; // Import necessário para a correção do agendamento

import com.personagem.Ranqueados;
import com.GerenciadorDeMagos.Gerenciador;
import com.csv.GerenciadorCSV;
import com.servicos.*; // Importa todos os serviços
import com.Agenda.*; // Importa as classes da Agenda
//import com.Mapas.*;      // Importa as classes dos Mapas

import com.feitico.Projetil;
import com.feitico.Canalizado;
import com.feitico.Magia;
import com.feitico.Area;

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
                    System.out.print("Digite o ID do mago: ");
                    int idMago = scan.nextInt();
                    scan.nextLine();
                    Ranqueados mago = gerenciador.buscarPorId(idMago);
                    if (mago == null) {
                        System.out.println("Mago não encontrado!");
                        break;
                    }
                    List<Magia> grimorio = mago.getGrimorio();
                    System.out.println("Magias atuais no grimório:");
                    for (int i = 0; i < grimorio.size(); i++) {
                        System.out.println((i + 1) + " - " + grimorio.get(i).getNome());
                    }
                    if (grimorio.size() >= 2) {
                        System.out.println("Grimório cheio! Deseja trocar uma magia? (s/n)");
                        String trocar = scan.nextLine();
                        if (trocar.equalsIgnoreCase("s")) {
                            System.out.print("Qual magia deseja remover? (1 ou 2): ");
                            int idxRemover = scan.nextInt();
                            scan.nextLine();
                            if (idxRemover >= 1 && idxRemover <= grimorio.size()) {
                                grimorio.remove(idxRemover - 1);
                            } else {
                                System.out.println("Índice inválido.");
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    // Adicionar nova magia
                    System.out.println("Escolha o tipo de magia para adicionar:");
                    System.out.println("1 - Projétil");
                    System.out.println("2 - Área");
                    System.out.println("3 - Canalizado");
                    int tipoMagia = scan.nextInt();
                    scan.nextLine();
                    System.out.print("Nome da magia: ");
                    String nomeMagia = scan.nextLine();
                    System.out.print("Escola da magia: ");
                    String escola = scan.nextLine();
                    scan.nextLine();
                    Magia novaMagia = null;
                    if (tipoMagia == 1) {
                        novaMagia = new Projetil(nomeMagia, 5, 1, escola);
                    } else if (tipoMagia == 2) {
                        novaMagia = new Area(nomeMagia, 10, 2, escola);
                    } else if (tipoMagia == 3) {
                        novaMagia = new Canalizado(nomeMagia, 3, 3, escola);
                    }
                    if (novaMagia != null) {
                        mago.adicionarMagia(novaMagia);
                        System.out.println("Magia adicionada ao grimório!");
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