package com.servicos;

import java.util.List;
import java.util.Scanner;
import com.GerenciadorDeMagos.Gerenciador;
import com.feitico.Area;
import com.feitico.Canalizado;
import com.feitico.Magia;
import com.feitico.Projetil;
import com.personagem.Ranqueados;

public class CriadorDeMagias extends Servicos {

    public CriadorDeMagias(Gerenciador gerenciador, Scanner scanner) {
        super(scanner, gerenciador);
    }

    @Override
    public void executar() {
        System.out.println("\n ---- Gerenciador do grimório ---");
        System.out.print("Digite o ID do mago: ");
        int idMago = scanner.nextInt();
        scanner.nextLine();
        Ranqueados mago = gerenciador.buscarPorId(idMago);
        while (mago == null) {
            System.out.println("Mago não encontrado");
            System.out.println("Digite o ID do mago: ");
            idMago = scanner.nextInt();
            scanner.nextLine();
            mago = gerenciador.buscarPorId(idMago);
        }
        Boolean running = true;
        while (running) {
            System.out.println("-------------------");
            System.out.println("Escolha uma ação:");
            System.out.println("0 - Voltar ao Menu");
            System.out.println("1 - Listar magias no grimório");
            System.out.println("2 - Remover magia no grimório");
            System.out.println("3 - Adicionar magia ao grimório");
            int opcaoGrimorio = scanner.nextInt();
            scanner.nextLine();
            List<Magia> grimorio = mago.getGrimorio();

            switch (opcaoGrimorio) {
                case 1:
                    System.out.println("Magias atuais no grimório:");
                    for (int i = 0; i < grimorio.size(); i++) {
                        System.out.println("-------------------");
                        System.out.println((i + 1) + " - " + grimorio.get(i).getNome());
                        System.out.println("Tipo de magia: " + grimorio.get(i).getClass().getSimpleName());
                        System.out.println("Escola: " + grimorio.get(i).getEscola());
                        System.out.println("Mana: " + grimorio.get(i).getCustoMana());
                        System.out.println("Tempo de recarga: " + grimorio.get(i).getTempoRecarga());
                    }
                    if (grimorio.size() == 0) {
                        System.out.println("Não há magias no grimório!");
                    }

                    break;
                case 2:

                    for (int i = 0; i < grimorio.size(); i++) {
                        System.out.println((i + 1) + " - " + grimorio.get(i).getNome());
                    }
                    System.out.print("Qual magia deseja remover? (1 ou 2): ");
                    int idxRemover = scanner.nextInt();
                    scanner.nextLine();
                    while (idxRemover < 1 || idxRemover > grimorio.size()) {
                        System.out.println("Índice inválido.");
                        break;

                    }
                    if (idxRemover >= 1 && idxRemover <= grimorio.size()) {
                        grimorio.remove(idxRemover - 1);
                    }

                    break;

                case 3:

                    if (grimorio.size() >= 2) {
                        System.out.println("Grimório cheio! Remova uma magia primeiro");
                        break;
                    }

                    // Adicionar nova magia
                    System.out.println("Escolha o tipo de magia para adicionar:");
                    System.out.println("1 - Projétil");
                    System.out.println("2 - Área");
                    System.out.println("3 - Canalizado");
                    int tipoMagia = scanner.nextInt();
                    scanner.nextLine();
                    while (tipoMagia < 1 || tipoMagia > 3) {
                        System.out.println("Valor inválido!");
                        System.out.println("Escolha o tipo de magia para adicionar:");
                        System.out.println("1 - Projétil");
                        System.out.println("2 - Área");
                        System.out.println("3 - Canalizado");
                        tipoMagia = scanner.nextInt();
                        scanner.nextLine();
                    }

                    System.out.print("Nome da magia: ");
                    String nomeMagia = scanner.nextLine();

                    System.out.print("Escola da magia: \n");
                    System.out.println("1 - Arcano");
                    System.out.println("2 - Sombrio");
                    System.out.println("3 - Elemental");
                    int escolaOpcao = scanner.nextInt();
                    scanner.nextLine();
                    while (escolaOpcao < 1 || escolaOpcao > 3) {
                        System.out.println("Valor inválido!");
                        System.out.print("Escolha a escola da magia: ");
                        System.out.println("1 - Arcano");
                        System.out.println("2 - Sombrio");
                        System.out.println("3 - Elemental");
                        escolaOpcao = scanner.nextInt();
                        scanner.nextLine();
                    }
                    String escola = "";
                    if (escolaOpcao == 1) {
                        escola = "Arcano";
                    } else if (escolaOpcao == 2) {
                        escola = "Sombrio";
                    } else if (escolaOpcao == 3) {
                        escola = "Elemental";
                    }

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
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

}
