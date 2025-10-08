package com.Menu;

import java.util.List;
import java.util.Scanner;

import com.personagem.Ranqueados;
import com.GerenciadorDeMagos.Gerenciador;
import com.csv.GerenciadorCSV;
import com.feitico.Magia;
import com.servicos.BuscadorDeMagos;
import com.servicos.CriadorDeMagos;
import com.servicos.ListadorDeMagos;
import com.servicos.OrganizadorDeDuelos;
import com.feitico.Projetil;
import com.feitico.Canalizado;
import com.feitico.Area;

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
            System.out.println("3. Gerenciar Grimório de um Mago");
            System.out.println("4. Iniciar Duelo");
            System.out.println("5. Salvar Magos em CSV");
            System.out.println("6. Carregar Magos de CSV");
            System.out.println("7. Buscar Mago por ID");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scan.nextInt();
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
                case 4:
                    OrganizadorDeDuelos organizador = new OrganizadorDeDuelos(scan, gerenciador);
                    organizador.executar();
                    break;
                case 5:
                    gerenciadorCSV.salvar(gerenciador.listarTodos(), NOME_ARQUIVO);
                    System.out.println("Magos salvos com sucesso em " + NOME_ARQUIVO);
                    break;

                case 6:
                    List<Ranqueados> magosCarregados = gerenciadorCSV.carregar(NOME_ARQUIVO);
                    gerenciador.setListaDeMagos(magosCarregados);
                    System.out.println("Magos carregados com sucesso de " + NOME_ARQUIVO);
                    System.out.println(magosCarregados.size() + " magos foram carregados.");
                    break;
                case 7:
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
        }

    }

}