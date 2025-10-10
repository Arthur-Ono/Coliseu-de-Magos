package com.Menu;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import com.personagem.Ranqueados;
import com.GerenciadorDeMagos.Gerenciador;
import com.csv.GerenciadorCSV;
import com.servicos.*;
import com.Agenda.*;
import com.Mapas.*;
import com.feitico.Projetil;
import com.feitico.Canalizado;
import com.feitico.Magia;
import com.feitico.Area;

public class Menu {

    Scanner scan = new Scanner(System.in);
    private GerenciadorCSV gerenciadorCSV = new GerenciadorCSV();
    private final String NOME_ARQUIVO = "magos.csv";

    public void menuPrincipal(Gerenciador gerenciador) {
        
        // Instancia os gerenciadores do sistema de agendamento e o "relógio" do jogo.
        ControladorDeTurno relogio = new ControladorDeTurno();
        GerenciadorDeAgendamentos gerenciadorAgendamentos = new GerenciadorDeAgendamentos();

        boolean running = true;
        while (running) {
            System.out.println("\n--- MENU PRINCIPAL | TURNO: " + relogio.getTurnoAtual() + " ---");
            System.out.println("1. Criar Mago");
            System.out.println("2. Listar Magos");
            System.out.println("3. Agendar Duelo"); // NOVA OPÇÃO
            System.out.println("4. Iniciar Duelo Imediato");
            System.out.println("5. Gerenciar Grimório de um Mago");
            System.out.println("6. Salvar Magos em CSV");
            System.out.println("7. Carregar Magos de CSV");
            System.out.println("8. Buscar Mago por ID");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao) {
                case 1:
                    CriadorDeMagos criador = new CriadorDeMagos(scan, gerenciador);
                    criador.executar();
                    break;
                case 2:
                    ListadorDeMagos listador = new ListadorDeMagos(gerenciador, scan);
                    listador.executar();
                    break;
                case 3:
                    // Cria e executa o especialista em agendar duelos.
                    AgendadorDeDuelo agendador = new AgendadorDeDuelo(scan, gerenciador, gerenciadorAgendamentos, relogio);
                    agendador.executar();
                    break;
                case 4:
                    OrganizadorDeDuelos organizador = new OrganizadorDeDuelos(scan, gerenciador);
                    organizador.executar();
                    break;
                case 5:
                    // Lógica para gerenciar o grimório
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
                    // Removido o scan.nextLine() extra que estava aqui.
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
                case 6:
                    gerenciadorCSV.salvar(gerenciador.listarTodos(), NOME_ARQUIVO);
                    System.out.println("Magos salvos com sucesso em " + NOME_ARQUIVO);
                    break;
                case 7:
                    List<Ranqueados> magosCarregados = gerenciadorCSV.carregar(NOME_ARQUIVO);
                    gerenciador.setListaDeMagos(magosCarregados);
                    System.out.println("Magos carregados com sucesso de " + NOME_ARQUIVO);
                    System.out.println(magosCarregados.size() + " magos foram carregados.");
                    break;
                case 8:
                    BuscadorDeMagos buscador = new BuscadorDeMagos(gerenciador, scan);
                    buscador.executar();
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

            // A CADA AÇÃO COMPLETADA, O JOGO AVANÇA E VERIFICA A AGENDA
            
            // Avança o tempo do jogo em um turno.
            relogio.avancarTurno();

            // Pede ao gerenciador da agenda a lista de duelos que devem acontecer NESTE novo turno.
            List<Agendamento> duelosDoTurno = gerenciadorAgendamentos.verificarEObterAgendamentosParaTurno(relogio.getTurnoAtual());

            // Se a lista não estiver vazia, significa que temos um ou mais duelos para hoje.
            if (!duelosDoTurno.isEmpty()) {
                System.out.println("\n--- ATENÇÃO! UM DUELO AGENDADO ESTÁ COMEÇANDO! ---");
                // Passa por cada duelo agendado.
                for (Agendamento ag : duelosDoTurno) {
                    System.out.println("Iniciando duelo agendado na arena: " + ag.getArena().getNome());
                    // Chama o especialista em organizar duelos para executar a batalha agendada.
                    OrganizadorDeDuelos.iniciarDuelo(ag.getTime1(), ag.getTime2(), ag.getArena());
                }
                System.out.println("Pressione Enter para continuar...");
                scan.nextLine();
            }
        }
    }
}