package com.Menu;

import java.util.List;
import java.util.Scanner;


import com.personagem.Personagem;
import com.GerenciadorDeMagos.Gerenciador;
import com.csv.GerenciadorCSV;
public class Menu {

    Scanner scan = new Scanner(System.in);
    private GerenciadorCSV gerenciadorCSV = new GerenciadorCSV(); 
    private final String NOME_ARQUIVO = "magos.csv";
    public void menuPrincipal(Gerenciador gerenciador) {
        boolean running = true;
        while (running) {
            System.out.println("--- MENU PRINCIPAL ---");
            System.out.println("1. Criar Mago");
            System.out.println("2. Listar Magos");
            System.out.println("3. Agendar Duelo 1v1");
            System.out.println("4. Iniciar Duelo");
            System.out.println("5. Salvar Magos em CSV");
            System.out.println("6. Carregar Magos de CSV");
            System.out.println("7. Buscar Mago por ID");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scan.nextInt();
            switch (opcao) {
                case 1:
                    criarMago(gerenciador);
                    break;
                case 2:
                    listarMagos(gerenciador);
                    break;
                case 3:
                    System.out.println("Opção 3 selecionada: Agendar Duelo 1v1");
                    break;
                case 4:
                    iniciarDuelo(gerenciador);
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
                    buscarMagoPorId(gerenciador);
                    break;
                case 0:
                    System.out.println("Saindo do programa...");
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }

    }

    private void criarMago(Gerenciador gerenciador) {
        System.out.println("\n--- CRIAÇÃO DE NOVO MAGO ---");
        System.out.println("Escolha o tipo de Mago:");
        System.out.println("1. Mago Elemental");
        System.out.println("2. Mago Arcano");
        System.out.println("3. Mago Sombrio");
        System.out.print("Opção: ");
        int tipo = scan.nextInt();
        scan.nextLine();

        if (tipo < 1 || tipo > 3) {
            System.out.println("Opção inválida. Retornando ao menu principal.");
            return;
        }

        System.out.print("Digite o ID do mago: ");
        int id = scan.nextInt();
        scan.nextLine();

        System.out.print("Digite o Codinome do mago: ");
        String codinome = scan.nextLine();

        System.out.print("Digite a Vida Máxima: ");
        int vidaMax = scan.nextInt();

        System.out.print("Digite a Mana Máxima: ");
        int manaMax = scan.nextInt();
        scan.nextLine();

        System.out.print("Digite o Foco (Cajado, Varinha, etc): ");
        String foco = scan.nextLine();

        System.out.print("Digite o Poder Base: ");
        int poderBase = scan.nextInt();

        System.out.print("Digite a Resistência Mágica: ");
        int resistencia = scan.nextInt();

        System.out.print("Digite o Controlador (1 para Humano, 2 para IA): ");
        int controlador = scan.nextInt();

        System.out.print("Digite o valor para 'alto': ");
        int alto = scan.nextInt();
        scan.nextLine();

        //Criação da variável mago antes do switch para que ele não "morra no switch"
        Personagem novoMago = null;

        switch (tipo) {
            case 1:
                // atribuindo o novo mago à variável criada antes do switch
                novoMago = new MagoElemental(id, codinome, vidaMax, manaMax, foco, poderBase, resistencia, controlador, alto);
                break;
            case 2:
                // novoMago = new MagoArcano(id, codinome, vidaMax, manaMax, foco, poderBase,resistencia, controlador, horaEntrada, alto);
                System.out.println("Criação de Mago Arcano ainda não implementada.");
                break;
            case 3:
                // novoMago = new MagoSombrio(id, codinome, vidaMax, manaMax, foco, poderBase,resistencia, controlador, horaEntrada, alto);
                System.out.println("Criação de Mago Sombrio ainda não implementada.");
                break;
        }

        if (novoMago != null) {
            System.out.println("\n" + novoMago.getClass().getSimpleName() + " criado com sucesso!");
            System.out.println("------------------------------------");
            System.out.println("  ID: " + novoMago.getId());
            System.out.println("  Codinome: " + novoMago.getCodinome());
            System.out.println("  Escola: " + novoMago.getEscola());
            System.out.println("  Vida Máxima: " + novoMago.getVidaMax());
            System.out.println("  Mana Máxima: " + novoMago.getManaMax());
            System.out.println("  Foco: " + novoMago.getFoco());
            System.out.println("  Poder Base: " + novoMago.getPoderBase());
            System.out.println("  Resistência: " + novoMago.getResistencia());
            System.out.println("  Controlador: " + novoMago.getControlador());
            System.out.println("  Hora de Entrada: " + novoMago.getHoraEntrada());
            System.out.println("------------------------------------");

            gerenciador.adicionar(novoMago);
        }

        System.out.println("\nPressione Enter para voltar ao menu...");
        scan.nextLine();
    }

    private void listarMagos(Gerenciador gerenciador) {
        System.out.println("\n--- LISTA DE MAGOS ---");
        List<Personagem> magos = gerenciador.listarTodos(); // 1. O Garçom pega a bandeja.
        for (Personagem mago : magos) { // 2. O Garçom vai até a mesa...
            System.out.println(mago); // 3. ...e apresenta cada prato.
        }
    }

    private void buscarMagoPorId(Gerenciador gerenciador) {
        System.out.println("\n--- BUSCAR MAGO POR ID ---");
        System.out.print("Digite o ID do mago que deseja buscar: ");
        int idParaBuscar = scan.nextInt();
        scan.nextLine(); // Limpa o buffer do scanner

        Personagem magoEncontrado = gerenciador.buscarPorId(idParaBuscar);

        // Verifica se o mago foi encontrado ou não
        if (magoEncontrado != null) {
            System.out.println("Mago encontrado:");
            System.out.println(magoEncontrado); 
        } else {
            System.out.println("Nenhum mago encontrado com o ID: " + idParaBuscar);
        }

        System.out.println("\nPressione Enter para voltar ao menu...");
        scan.nextLine();
    }
    
    private void iniciarDuelo(Gerenciador gerenciador) {
    System.out.println("\n--- INICIAR DUELO 1v1 ---");

    if (gerenciador.listarTodos().size() < 2) {
        System.out.println("É necessário ter pelo menos 2 magos cadastrados para iniciar um duelo.");
        System.out.println("\nPressione Enter para voltar ao menu...");
        scan.nextLine();
        return;
    }

    System.out.println("Magos disponíveis para o duelo:");
    for (Personagem mago : gerenciador.listarTodos()) {
        System.out.println("ID: " + mago.getId() + " | Nome: " + mago.getCodinome() + " | Vida: " + mago.getVidaAtual());
    }
    
    System.out.print("\nDigite o ID do primeiro combatente: ");
    int id1 = scan.nextInt();
    
    System.out.print("Digite o ID do segundo combatente: ");
    int id2 = scan.nextInt();
    scan.nextLine(); // Limpa o buffer

    Personagem combatente1 = gerenciador.buscarPorId(id1);
    Personagem combatente2 = gerenciador.buscarPorId(id2);

    if (combatente1 == null || combatente2 == null) {
        System.out.println("Um ou ambos os IDs são inválidos. Duelo cancelado.");
        System.out.println("\nPressione Enter para voltar ao menu...");
        scan.nextLine();
        return;
    }

    if (id1 == id2) {
        System.out.println("Um mago não pode duelar contra si mesmo. Duelo cancelado.");
        System.out.println("\nPressione Enter para voltar ao menu...");
        scan.nextLine();
        return;
    }

    System.out.println("\nO DUELO ENTRE " + combatente1.getCodinome().toUpperCase() + " E " + combatente2.getCodinome().toUpperCase() + " COMEÇOU! 💥");
    
    int turno = 1;
    while (combatente1.getVidaAtual() > 0 && combatente2.getVidaAtual() > 0) {
        System.out.println("\n--- Turno " + turno + " ---");

        // ======================================================================
        //Bota aqui a lógica de dano, Mathematics.
        
        
        // ======================================================================

        
        combatente1.imprimirVidaAtual();
        combatente2.imprimirVidaAtual();

        turno++;
        //Quando tu terminares o ngc de dano remove esse break aqui. ele serve só pro esqueleto não ficar em loop
        break; 
        
         
    }

    System.out.println("\n--- FIM DO DUELO ---");
    if (combatente1.getVidaAtual() > 0) {
        System.out.println("O VENCEDOR É: " + combatente1.getCodinome().toUpperCase() + "!");
    } else if (combatente2.getVidaAtual() > 0) {
        System.out.println("O VENCEDOR É: " + combatente2.getCodinome().toUpperCase() + "!");
    } else {
        System.out.println("O duelo terminou em empate!");
    }

    System.out.println("\nPressione Enter para voltar ao menu...");
    scan.nextLine();
}
}