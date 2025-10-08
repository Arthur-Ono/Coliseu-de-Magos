package com.servicos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.GerenciadorDeMagos.Gerenciador;
import com.personagem.Ranqueados;
import com.feitico.Magia;

public class OrganizadorDeDuelos extends Servicos {

    public OrganizadorDeDuelos(Scanner scanner, Gerenciador gerenciador) {
        super(scanner, gerenciador);
    }

    @Override
    public void executar() {
        System.out.println("\n----- DUELOS -----");
        System.out.println("SELECIONE O TIPO DE DUELO:");
        System.out.println("1. Duelo 1v1");
        System.out.println("2. Duelo 2v2");
        System.out.println("3. Duelo 3v3");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        List<Ranqueados> time1 = null;
        List<Ranqueados> time2 = null;

        switch (opcao) {
            case 1:
                time1 = montarTime(1, 1);
                time2 = montarTime(2, 1);
                break;
            case 2:
                time1 = montarTime(1, 2);
                time2 = montarTime(2, 2);
                break;
            case 3:
                time1 = montarTime(1, 3);
                time2 = montarTime(2, 3);
                break;
            case 0:
                System.out.println("Voltando ao menu principal...");
                return;
            default:
                System.out.println("Opção inválida.");
                return;
        }

        // Se a montagem dos times foi bem-sucedida (não retornou null)
        if (time1 != null && time2 != null) {
            iniciarDuelo(time1, time2);
        }
    }

    private List<Ranqueados> montarTime(int numeroDoTime, int tamanhoDoTime) {
        List<Ranqueados> time = new ArrayList<>();
        System.out.println("\n--- Montando Time " + numeroDoTime + " ---");

        for (int i = 1; i <= tamanhoDoTime; i++) {
            System.out.print("Digite o ID do " + i + "º mago do Time " + numeroDoTime + ": ");
            int idMago = this.scanner.nextInt();
            this.scanner.nextLine();

            Ranqueados magoSelecionado = this.gerenciador.buscarPorId(idMago);

            if (magoSelecionado == null) {
                System.out.println("ERRO: Mago com ID " + idMago + " não encontrado. Montagem de time cancelada.");
                return null;
            }
            if (time.contains(magoSelecionado)) {
                System.out.println("ERRO: Mago " + magoSelecionado.getCodinome()
                        + " já está neste time. Montagem de time cancelada.");
                return null;
            }

            time.add(magoSelecionado);
            System.out.println(" -> " + magoSelecionado.getCodinome() + " adicionado ao Time " + numeroDoTime);
        }
        return time;
    }

    private void iniciarDuelo(List<Ranqueados> time1, List<Ranqueados> time2) {
        // Validação básica para garantir que os times não são os mesmos
        if (time1.stream().anyMatch(time2::contains)) {
            System.out.println("Um mago não pode estar em ambos os times. Duelo Cancelado.");
            return;
        }

        // Lista para saber quem ataca primeiro, baseado na velocidade.
        List<Ranqueados> ordemTurno = new ArrayList<>();
        ordemTurno.addAll(time1);
        ordemTurno.addAll(time2);
        ordemTurno.sort((a, b) -> Integer.compare(b.getVelocidade(), a.getVelocidade()));

        System.out.println("\n💥 O DUELO ENTRE TIME 1 E TIME 2 COMEÇOU! 💥");

        Map<Ranqueados, Integer> defesa = new HashMap<>();
        Map<Ranqueados, Ranqueados> ultimoAlvoAtacado = new HashMap<>();

        int turno = 1;
        while (timeEstaVivo(time1) && timeEstaVivo(time2)) {
            System.out.println("\n--- Turno " + turno + " ---");

            for (Ranqueados atacante : ordemTurno) {

                // atacante que estava defendendo agora não defende mais!
                if (defesa.containsKey(atacante)) {
                    atacante.setResistencia(defesa.get(atacante));
                    defesa.remove(atacante);
                    System.out.println(atacante.getCodinome() + "Parou de defender");
                }

                if (atacante.getVidaAtual() > 0) {
                    System.out.println("Atacante " + atacante.getCodinome());
                    System.out.println("Vida: " + atacante.getVidaAtual() + "Mana: ");
                    System.out.println("Escolha sua ação:\n (1) Atacar\n (2) Defender");
                    int acao = scanner.nextInt();
                    if (acao == 1) {

                        // verifica se o atacante é do time 1, se sim, os inimigos são o time 2, caso
                        // contrário, são do time 1
                        List<Ranqueados> adversarios = time1.contains(atacante) ? time2 : time1;

                        // aqui eu fiz pra listar os magos vivos do time adversário, iamgina escolher
                        // atacar um corpo morto........
                        List<Ranqueados> alvosVivos = new ArrayList<>();
                        System.out.println("Magos vivos do time adversário:");
                        for (int i = 0; i < adversarios.size(); i++) {
                            Ranqueados p = adversarios.get(i);
                            if (p.getVidaAtual() > 0) {
                                alvosVivos.add(p);
                                System.out.println((alvosVivos.size()) + " - " + p.getCodinome() + " (Vida: "
                                        + p.getVidaAtual() + ")");

                            }
                        }

                        if (alvosVivos.isEmpty()) {
                            System.out.println("Não há alvos vivos para atacar! A batalha acabou!");
                            continue;
                        }

                        System.out.println("Escolha quem você deseja atacar!");
                        int escolha = scanner.nextInt();
                        while (escolha < 1 || escolha > alvosVivos.size()) {

                            System.out.println("Escolha inválida!\n Escolha outra vez!");
                            System.out.println("Escolha quem você deseja atacar!");
                            escolha = scanner.nextInt();
                            scanner.nextLine();

                        }

                        Ranqueados alvo = alvosVivos.get(escolha - 1);

                        // abre o grimório e mostra as magias.... se tiver...
                        List<Magia> grimorio = atacante.getGrimorio();
                        System.out.println("Escolha o tipo de ataque: ");
                        System.out.println("(0) Ataque básico| Poder base: " + atacante.getPoderBase());
                        for (int i = 0; i < grimorio.size(); i++) {
                            Magia m = grimorio.get(i);
                            System.out.println("(" + (i + 1) + ") " + m.getNome() + "| Dano: "
                                    + m.calcularDano(atacante.getEscola(), atacante.getPoderBase()));
                        }

                        // escolhe a magia ou ataque básico
                        System.out.println("Digite o que você fará: ");
                        int escolhaAtaque = scanner.nextInt();
                        scanner.nextLine();
                        while (escolhaAtaque < 0 || escolhaAtaque > grimorio.size()) {
                            System.out.println("Faça uma escolha válida!\n");
                            escolhaAtaque = scanner.nextInt();
                        }
                        // se escolheu magia, armazena a informação aqui!!!!
                        Magia magiaSelecionada = null;
                        if (escolhaAtaque > 0 && escolhaAtaque <= grimorio.size()) {
                            magiaSelecionada = grimorio.get(escolhaAtaque - 1);
                        }

                        boolean ataqueValido = false;
                        while (!ataqueValido) {
                            magiaSelecionada = null;
                            if (escolhaAtaque > 0 && escolhaAtaque <= grimorio.size()) {
                                magiaSelecionada = grimorio.get(escolhaAtaque - 1);
                                if (atacante.getManaAtual() < magiaSelecionada.getCustoMana()) {
                                    System.out.println("Mana insuficiente para " + magiaSelecionada.getNome() + "!");
                                    System.out.println("Escolha outro ataque:");
                                    System.out.println("(0) Ataque básico| Poder base: " + atacante.getPoderBase());
                                    for (int i = 0; i < grimorio.size(); i++) {
                                        Magia m = grimorio.get(i);
                                        System.out.println("(" + (i + 1) + ") " + m.getNome() + "| Dano: "
                                                + m.calcularDano(atacante.getEscola(), atacante.getPoderBase())
                                                + " | Mana: " + m.getCustoMana());
                                    }
                                    escolhaAtaque = scanner.nextInt();
                                    scanner.nextLine();
                                    continue;
                                } else {
                                    // desconta a mana
                                    atacante.setManaAtual(atacante.getManaAtual() - magiaSelecionada.getCustoMana());
                                    ataqueValido = true;
                                }
                            } else {
                                // ataque básico
                                ataqueValido = true;
                            }
                        }

                        // realiza o ataque
                        atacante.causarDano(alvo, magiaSelecionada);
                        ultimoAlvoAtacado.put(atacante, alvo);

                    }

                    else if (acao == 2) {
                        if (!defesa.containsKey(atacante)) {
                            defesa.put(atacante, atacante.getResistencia());
                            atacante.setResistencia(atacante.getResistencia() + atacante.getResistencia() / 2);
                            System.out.println(atacante.getCodinome() + " está em modo de defesa!");
                        }
                    }
                }
            }

            // Impressão da vida de todos
            System.out.println("\n-- Status dos Times --");
            System.out.print("Time 1: ");
            time1.forEach(p -> System.out.print(p.getCodinome() + "(" + p.getVidaAtual() + ") "));
            System.out.print("\nTime 2: ");
            time2.forEach(p -> System.out.print(p.getCodinome() + "(" + p.getVidaAtual() + ") "));
            System.out.println();

            turno++;
            // Quando tu terminares o ngc de dano remove esse break aqui. ele serve só pro
            // esqueleto não ficar em loop

        }

        System.out.println("\n--- FIM DO DUELO ---");
        if (timeEstaVivo(time1)) {
            System.out.println("🏆 O VENCEDOR É: TIME 1!");
        } else if (timeEstaVivo(time2)) {
            System.out.println("🏆 O VENCEDOR É: TIME 2!");
        } else {
            System.out.println("O duelo terminou em empate!");
        }

        if (timeEstaVivo(time1)) {
            for (Ranqueados mago : time1) {
                mago.setCapturas(mago.getCapturas() + 1);
            }
        } else if (timeEstaVivo(time2)) {
            for (Ranqueados mago : time2) {
                mago.setCapturas(mago.getCapturas() + 1);
            }
        }

        // contabilizar os rankings aqui
        ArrayList<Ranqueados> todosMAgos = new ArrayList<>();
        todosMAgos.addAll(time1);
        todosMAgos.addAll(time2);
        for (Ranqueados mago : todosMAgos) {
            Ranqueados alvo = ultimoAlvoAtacado.get(mago);
            if (alvo != null) {
                mago.incrementarRanking(alvo, todosMAgos);
            } else {
                mago.incrementarRanking(mago, todosMAgos);
            }
        }

        System.out.println("\nPressione Enter para voltar ao menu...");
        this.scanner.nextLine();
    }

    private boolean timeEstaVivo(List<Ranqueados> time) {
        for (Ranqueados p : time) {
            if (p.getVidaAtual() > 0) {
                return true;
            }
        }
        return false;
    }
}