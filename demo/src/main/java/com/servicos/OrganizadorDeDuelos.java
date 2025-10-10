package com.servicos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.GerenciadorDeMagos.Gerenciador;
import com.Mapas.*;
import com.personagem.Personagem;
import com.personagem.Ranqueados;
import com.IA.AcaoIA;
import com.IA.LogicaIA;
import com.IA.TipoAcao;
import com.feitico.Magia;

// Esta é a classe mais importante do combate. Ela é o "juiz" ou "mestre de cerimônias"
// que organiza e executa toda a lógica de uma batalha.
public class OrganizadorDeDuelos extends Servicos {

    // O Organizador agora tem o "cérebro" da IA como uma ferramenta.
    private LogicaIA cerebroIA = new LogicaIA();

    public OrganizadorDeDuelos(Scanner scanner, Gerenciador gerenciador) {
        super(scanner, gerenciador);
    }

    // --- MÉTODOS DE CONDIÇÃO DE CAMPO ---
    // Estes métodos aplicam e removem os efeitos da arena nos magos.
    public void aplicarCondicaoDeCampo(Ranqueados mago, CondicaoDeCampo condicao) {
        switch (condicao.getNome()) {
            case "AUMENTA_DANO":
                mago.setPoderBase(mago.getPoderBase() + 10);
                break;
            case "AUMENTA_DEFESA":
                mago.setResistencia(mago.getResistencia() + 10);
                break;
            case "REDUZ_VELOCIDADE":
                mago.setVelocidade(Math.max(1, mago.getVelocidade() - 5));
                break;
        }
    }

    private void removerCondicaoDeCampo(Ranqueados mago, CondicaoDeCampo condicao) {
        // Este método desfaz o que o 'aplicarCondicaoDeCampo' fez.
        switch (condicao.getNome()) {
            case "AUMENTA_DANO":
                mago.setPoderBase(mago.getPoderBase() - 10);
                break;
            case "AUMENTA_DEFESA":
                mago.setResistencia(mago.getResistencia() - 10);
                break;
            case "REDUZ_VELOCIDADE":
                mago.setVelocidade(mago.getVelocidade() + 5);
                break;
        }
    }

    @Override
    // O 'executar' funciona como o menu de preparação para um Duelo Imediato.
    public void executar() {
        System.out.println("\n----- DUELO IMEDIATO -----");

        GerenciadorDeArenas gerenciadorArenas = new GerenciadorDeArenas();
        List<Arena> arenasDisponiveis = gerenciadorArenas.getArenas();
        System.out.println("Selecione a Arena para o combate:");
        for (int i = 0; i < arenasDisponiveis.size(); i++) {
            System.out.println((i + 1) + ". " + arenasDisponiveis.get(i).getNome());
        }
        System.out.print("Escolha uma opção de Arena: ");
        int escolhaArena = scanner.nextInt() - 1;
        scanner.nextLine();

        if (escolhaArena < 0 || escolhaArena >= arenasDisponiveis.size()) {
            System.out.println("Arena inválida. Duelo cancelado.");
            return;
        }
        Arena arenaEscolhida = arenasDisponiveis.get(escolhaArena);

        System.out.println("SELECIONE O TIPO DE DUELO:");
        System.out.println("1. 1v1 | 2. 2v2 | 3. 3v3");
        System.out.print("Opção: ");
        int tamanho = scanner.nextInt();
        scanner.nextLine();

        if (tamanho < 1 || tamanho > 3) {
            System.out.println("Opção inválida.");
            return;
        }

        List<Ranqueados> time1 = montarTime(1, tamanho);
        List<Ranqueados> time2 = montarTime(2, tamanho);

        if (time1 != null && time2 != null) {
            iniciarDuelo(time1, time2, arenaEscolhida);
        }
    }

    // Este é o coração da classe, onde a batalha acontece.
    public void iniciarDuelo(List<Ranqueados> time1, List<Ranqueados> time2, Arena arenaEscolhida) {
        if (time1.stream().anyMatch(time2::contains)) {
            System.out.println("Um mago não pode estar em ambos os times. Duelo Cancelado.");
            return;
        }

        List<Ranqueados> ordemTurno = new ArrayList<>();
        ordemTurno.addAll(time1);
        ordemTurno.addAll(time2);
        ordemTurno.sort((a, b) -> Integer.compare(b.getVelocidade(), a.getVelocidade()));

        System.out.println("\n----| O DUELO ENTRE TIME 1 E TIME 2 COMEÇOU! |----");

        Map<Ranqueados, Magia> canalizando = new HashMap<>();
        Map<Ranqueados, Boolean> sofreuRuptura = new HashMap<>();
        Map<Ranqueados, Integer> defesa = new HashMap<>();
        Map<Ranqueados, Ranqueados> ultimoAlvoAtacado = new HashMap<>();

        CondicaoDeCampo condicaoAtiva = arenaEscolhida.sortearCondicao();
        System.out.println("Condição Inicial: " + condicaoAtiva.getNome());
        for (Ranqueados ranqueado : ordemTurno) {
            aplicarCondicaoDeCampo(ranqueado, condicaoAtiva);
        }

        int turno = 1;
        while (timeEstaVivo(time1) && timeEstaVivo(time2)) {
            System.out.println("\n--- Turno " + turno + " ---");
            
            if (turno > 1 && (turno - 1) % 3 == 0) {
                // Remove o efeito da condição anterior antes de aplicar a nova.
                for (Ranqueados mago : ordemTurno) {
                    removerCondicaoDeCampo(mago, condicaoAtiva);
                }
                condicaoAtiva = arenaEscolhida.sortearCondicao();
                System.out.println("A CONDIÇÃO DO CAMPO MUDOU!");
                // Aplica o efeito da nova condição.
                for (Ranqueados mago : ordemTurno) {
                    aplicarCondicaoDeCampo(mago, condicaoAtiva);
                }
            }
            System.out.println("Condição Ativa: " + condicaoAtiva.getNome());

            for (Ranqueados atacante : ordemTurno) {
                if (!timeEstaVivo(time1) || !timeEstaVivo(time2)) break;
                if (atacante.getVidaAtual() <= 0) continue;
                
                if (defesa.containsKey(atacante)) {
                    atacante.setResistencia(defesa.get(atacante));
                    defesa.remove(atacante);
                    System.out.println(atacante.getCodinome() + " parou de defender.");
                }
                
                // --- BIFURCAÇÃO HUMANO VS IA ---
                if (atacante.getControlador() == 1) {
                    // --- LÓGICA PARA JOGADOR HUMANO ---
                    System.out.println("\nTurno de: " + atacante.getCodinome() + " (Vida: " + atacante.getVidaAtual() + " | Mana: " + atacante.getManaAtual() + ")");
                    System.out.println("Escolha sua ação:\n (1) Atacar\n (2) Defender");
                    int acao = scanner.nextInt();
                    scanner.nextLine();
                    while (acao < 1 || acao > 2) {
                        System.out.println("Escolha uma ação válida!");
                        acao = scanner.nextInt();
                        scanner.nextLine();
                    }
                    
                    if (acao == 1) { // Lógica de Ataque do Humano
                        List<Ranqueados> adversarios = time1.contains(atacante) ? time2 : time1;

                        // aqui eu fiz pra listar os magos vivos do time adversário, iamgina escolher
                        // atacar um corpo morto........
                        List<Ranqueados> alvosVivos = new ArrayList<>();
                        System.out.println("Magos vivos do time adversário:");
                        for (int i = 0; i < adversarios.size(); i++) {
                            Ranqueados p = adversarios.get(i);
                            if (p.getVidaAtual() > 0) {
                                alvosVivos.add(p);
                                System.out.println((alvosVivos.size()) + " - " + p.getCodinome() + " (Vida: "+ p.getVidaAtual() + ")");

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
                            System.out.println("(" + (i + 1) + ") " + m.getNome() + "| Dano: " + m.calcularDano(atacante.getEscola(), atacante.getPoderBase()));
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
                        // aqui é pra caso o atacante nao tenha mana suficiente para soltar magia, ai
                        // ele escolhe outro ataque. Claro, poderia ser outra AÇÃO mas fiquei com
                        // preguiça kkkk :p
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
                                        System.out.println("(" + (i + 1) + ") " + m.getNome() + "| Dano: " + m.calcularDano(atacante.getEscola(), atacante.getPoderBase()) + " | Mana: " + m.getCustoMana());
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
                        // nessa parte aqui?
                        if (magiaSelecionada instanceof com.feitico.Canalizado) {
                            // Se ainda não está canalizando, inicia canalização
                            if (!canalizando.containsKey(atacante)) {
                                canalizando.put(atacante, magiaSelecionada);
                                sofreuRuptura.put(atacante, false);
                                System.out.println(atacante.getCodinome() + " começou a canalizar "+ magiaSelecionada.getNome() + "!");
                                continue;
                            } else if (canalizando.containsKey(atacante) && !sofreuRuptura.get(atacante)) {
                                atacante.causarDano(alvo, magiaSelecionada);
                                canalizando.remove(atacante);
                                sofreuRuptura.remove(atacante);
                                System.out.println(atacante.getCodinome() + " lançou a magia canalizada "+ magiaSelecionada.getNome() + "!");
                            } else if (sofreuRuptura.get(atacante)) {
                                System.out.println("A canalização de " + atacante.getCodinome() + " foi rompida!");
                                canalizando.remove(atacante);
                                sofreuRuptura.remove(atacante);
                                continue;
                            }
                        }else if (magiaSelecionada instanceof com.feitico.Area) {
                            // se o mago for da escolha sombrio ele drena 5 de mana com esse negocio abaixo
                            if ("Sombrio".equalsIgnoreCase(atacante.getEscola())) {
                                for (Ranqueados alvoArea : alvosVivos) {
                                    alvoArea.setManaAtual(Math.max(0, alvoArea.getManaAtual()-5));
                                    System.out.println(alvoArea.getCodinome()+"Teve sua mana drenada em 5 pontos!");
                                }
                            }
                            // causa o dano a todos os inimigos e verifica se há alguem canalizando e ainda faz ruptura nele, loucura essa batalha.
                            for (Ranqueados alvoArea : alvosVivos) {
                                atacante.causarDano(alvoArea,magiaSelecionada);
                                ultimoAlvoAtacado.put(atacante, alvoArea);
                                if (canalizando.containsKey(alvoArea)) {
                                    sofreuRuptura.put(alvoArea, true);
                                    System.out.println("A canalização de " +alvoArea.getCodinome()+" foi interrompida!");
                                    atacante.setRupturas(atacante.getRupturas()+1);
                                    
                                }
                            }
                        }
                         else {
                            atacante.causarDano(alvo, magiaSelecionada);
                        }

                        // Após o ataque, registre o alvo atacado
                        ultimoAlvoAtacado.put(atacante, alvo);

                        // Se o alvo estava canalizando, sofre ruptura
                        if (canalizando.containsKey(alvo)) {
                            sofreuRuptura.put(alvo, true);
                            System.out.println("A canalização de " + alvo.getCodinome() + " foi rompida por um ataque!");
                            atacante.setRupturas(atacante.getRupturas()+1);
                        }
                    } else if (acao == 2) { // Lógica de Defesa do Humano
                        defesa.put(atacante, atacante.getResistencia());
                        atacante.setResistencia(atacante.getResistencia() + atacante.getResistencia() / 2);
                        System.out.println(atacante.getCodinome() + " está em modo de defesa!");
                    }
                } else {
                    // --- LÓGICA PARA IA ---
                    System.out.println("\nTurno de: " + atacante.getCodinome() + " (IA) (Vida: " + atacante.getVidaAtual() + ")");
                    List<Ranqueados> aliados = time1.contains(atacante) ? time1 : time2;
                    List<Ranqueados> inimigos = time1.contains(atacante) ? time2 : time1;
                    AcaoIA decisao = cerebroIA.decidirAcao(atacante, aliados, inimigos);

                    switch (decisao.getTipo()) {
                        case ATACAR_BASICO:
                            System.out.println(atacante.getCodinome() + " (IA) decide usar um Ataque Básico em " + decisao.getAlvo().getCodinome() + "!");
                            atacante.causarDano(decisao.getAlvo(), null);
                            ultimoAlvoAtacado.put(atacante, decisao.getAlvo());
                            break;
                        case USAR_MAGIA:
                            System.out.println(atacante.getCodinome() + " (IA) usa " + decisao.getMagiaEscolhida().getNome() + " em " + decisao.getAlvo().getCodinome() + "!");
                            atacante.causarDano(decisao.getAlvo(), decisao.getMagiaEscolhida());
                            ultimoAlvoAtacado.put(atacante, decisao.getAlvo());
                            break;
                        case DEFENDER:
                            System.out.println(atacante.getCodinome() + " (IA) decide se defender!");
                            defesa.put(atacante, atacante.getResistencia());
                            atacante.setResistencia(atacante.getResistencia() + atacante.getResistencia() / 2);
                            break;
                    }
                    Ranqueados alvoDaIA = decisao.getAlvo();
                    if (alvoDaIA != null && canalizando.containsKey(alvoDaIA)) {
                        sofreuRuptura.put(alvoDaIA, true);
                        System.out.println("A canalização de " + alvoDaIA.getCodinome() + " foi rompida por um ataque da IA!");
                        atacante.setRupturas(atacante.getRupturas() + 1);
                    }
                }
            }
            
            System.out.println("\n-- Status dos Times --");
            System.out.print("Time 1: ");
            time1.forEach(p -> System.out.print(p.getCodinome() + "(" + p.getVidaAtual() + ") "));
            System.out.print("\nTime 2: ");
            time2.forEach(p -> System.out.print(p.getCodinome() + "(" + p.getVidaAtual() + ") "));
            System.out.println();

            // Lógica de final de turno (veneno, etc)

            for (Ranqueados mago : ordemTurno) {
                if (mago.getVidaAtual()>0) {
                    mago.setTempoEmCombate(mago.getTempoEmCombate()+1);
                }
            }
            turno++;
        }

        System.out.println("\n--- FIM DO DUELO ---");
        // ... (lógica de vencedor e ranking) ...
        for (Ranqueados ranqueado : ordemTurno) {
            removerCondicaoDeCampo(ranqueado, condicaoAtiva);
        }
        // ...
        System.out.println("\nPressione Enter para continuar...");
        this.scanner.nextLine();
    }

    private List<Ranqueados> montarTime(int numeroDoTime, int tamanhoDoTime) {
        List<Ranqueados> time = new ArrayList<>();
        System.out.println("\n--- Montando Time " + numeroDoTime + " ---");
        for (int i = 1; i <= tamanhoDoTime; i++) {
            System.out.print("Digite o ID do " + i + "º mago do Time " + numeroDoTime + ": ");
            int idMago = this.scanner.nextInt();
            this.scanner.nextLine();

            Personagem magoEncontrado = this.gerenciador.buscarPorId(idMago);
            if (magoEncontrado == null || !(magoEncontrado instanceof Ranqueados)) {
                System.out.println("ERRO: Mago com ID " + idMago + " não encontrado ou não é um combatente.");
                return null;
            }
            Ranqueados magoSelecionado = (Ranqueados) magoEncontrado;

            System.out.print("Controlador para " + magoSelecionado.getCodinome() + ": (1) Humano ou (2) IA? ");
            int tipoControlador = scanner.nextInt();
            scanner.nextLine();
            
            magoSelecionado.setControlador(tipoControlador);

            if (time.contains(magoSelecionado)) {
                System.out.println("ERRO: Este mago já foi adicionado ao time.");
                i--; 
                continue;
            }
            time.add(magoSelecionado);
        }
        return time;
    }

    private Ranqueados escolherAlvo(List<Ranqueados> adversarios) {
        List<Ranqueados> alvosVivos = new ArrayList<>();
        System.out.println("Magos vivos do time adversário:");
        for (int i = 0; i < adversarios.size(); i++) {
            Ranqueados p = adversarios.get(i);
            if (p.getVidaAtual() > 0) {
                alvosVivos.add(p);
                System.out.println((alvosVivos.size()) + " - " + p.getCodinome() + " (Vida: "+ p.getVidaAtual() + ")");
            }
        }
        if (alvosVivos.isEmpty()) {
            System.out.println("Não há alvos vivos para atacar!");
            return null;
        }
        System.out.print("Escolha quem você deseja atacar: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();
        while (escolha < 1 || escolha > alvosVivos.size()) {
            System.out.println("Escolha inválida! Tente de novo:");
            escolha = scanner.nextInt();
            scanner.nextLine();
        }
        return alvosVivos.get(escolha - 1);
    }

    private Magia escolherMagia(Ranqueados atacante) {
        List<Magia> grimorio = atacante.getGrimorio();
        if (grimorio.isEmpty()) {
            return null;
        }
        
        System.out.println("Escolha o tipo de ataque: ");
        System.out.println("(0) Ataque básico| Poder base: " + atacante.getPoderBase());
        for (int i = 0; i < grimorio.size(); i++) {
            Magia m = grimorio.get(i);
            System.out.println("(" + (i + 1) + ") " + m.getNome() + "| Custo de Mana: " + m.getCustoMana());
        }

        System.out.print("Digite o que você fará: ");
        int escolhaAtaque = scanner.nextInt();
        scanner.nextLine();
        while (escolhaAtaque < 0 || escolhaAtaque > grimorio.size()) {
            System.out.println("Faça uma escolha válida!");
            escolhaAtaque = scanner.nextInt();
            scanner.nextLine();
        }

        if (escolhaAtaque == 0) {
            return null;
        }

        Magia magiaSelecionada = grimorio.get(escolhaAtaque - 1);

        if (atacante.getManaAtual() < magiaSelecionada.getCustoMana()) {
            System.out.println("Mana insuficiente para " + magiaSelecionada.getNome() + "! Usando ataque básico.");
            return null;
        }
        
        atacante.setManaAtual(atacante.getManaAtual() - magiaSelecionada.getCustoMana());
        return magiaSelecionada;
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