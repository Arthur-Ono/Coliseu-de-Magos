package com.Menu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.GerenciadorDeMagos.Gerenciador;
import com.Mapas.GerenciadorDeArenas;
import com.Mapas.Arena;
import com.Mapas.CondicaoDeCampo;
import com.personagem.Ranqueados;
import com.feitico.Magia;
import java.io.OutputStream;
import java.io.PrintStream;

public class DueloGUI extends JFrame {

    private JTextArea areaLog;
    private List<Ranqueados> time1, time2;
    private Arena arenaEscolhida;
    private Gerenciador gerenciador;
    private PrintStream originalOut;

    public DueloGUI(Gerenciador gerenciador) {
        setTitle("Iniciar Batalha");
        setSize(800, 500);
        setLocationRelativeTo(null);
        this.gerenciador = gerenciador;

        // Listar magos disponíveis
        List<Ranqueados> magos = gerenciador.listarTodos();
        DefaultListModel<Ranqueados> modeloMagos = new DefaultListModel<>();
        for (Ranqueados mago : magos) {
            modeloMagos.addElement(mago);
        }

        JList<Ranqueados> listaTime1 = new JList<>(modeloMagos);
        JList<Ranqueados> listaTime2 = new JList<>(modeloMagos);

        listaTime1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaTime2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Listar arenas disponíveis
        GerenciadorDeArenas gerArenas = new GerenciadorDeArenas();
        List<Arena> arenas = gerArenas.getArenas();
        JComboBox<Arena> cmbArena = new JComboBox<>(arenas.toArray(new Arena[0]));

        JButton btnIniciar = new JButton("Iniciar Duelo");
        JButton btnFechar = new JButton("Fechar");

        areaLog = new JTextArea();
        areaLog.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(areaLog);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(new JLabel("Selecione o(s) mago(s) do Time 1:"));
        panel.add(new JLabel("Selecione o(s) mago(s) do Time 2:"));
        panel.add(new JScrollPane(listaTime1));
        panel.add(new JScrollPane(listaTime2));

        JPanel arenaPanel = new JPanel(new FlowLayout());
        arenaPanel.add(new JLabel("Arena:"));
        arenaPanel.add(cmbArena);

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnIniciar);
        btnPanel.add(btnFechar);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        add(arenaPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
        add(scrollLog, BorderLayout.EAST);

        btnIniciar.addActionListener(e -> {
            time1 = listaTime1.getSelectedValuesList();
            time2 = listaTime2.getSelectedValuesList();
            arenaEscolhida = (Arena) cmbArena.getSelectedItem();

            if (time1.isEmpty() || time2.isEmpty() || arenaEscolhida == null) {
                JOptionPane.showMessageDialog(this, "Selecione pelo menos um mago para cada time e uma arena!");
                return;
            }

            // Após selecionar os magos para cada time:
            for (Ranqueados mago : time1) {
                String[] opcoes = {"Humano", "IA"};
                int controlador = JOptionPane.showOptionDialog(this,
                    "Quem controlará o mago " + mago.getCodinome() + "?",
                    "Controlador",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
                mago.setControlador(controlador == 0 ? 1 : 2);
            }
            for (Ranqueados mago : time2) {
                String[] opcoes = {"Humano", "IA"};
                int controlador = JOptionPane.showOptionDialog(this,
                    "Quem controlará o mago " + mago.getCodinome() + "?",
                    "Controlador",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
                mago.setControlador(controlador == 0 ? 1 : 2);
            }

            areaLog.setText("Duelo iniciado na arena: " + arenaEscolhida.getNome() + "\n");
            iniciarDueloGUI();
        });

        btnFechar.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void iniciarDueloGUI() {
        redirecionarSystemOutParaAreaLog(); 
        List<Ranqueados> ordemTurno = new ArrayList<>();
        ordemTurno.addAll(time1);
        ordemTurno.addAll(time2);
        ordemTurno.sort((a, b) -> Integer.compare(b.getVelocidade(), a.getVelocidade()));

        Map<Ranqueados, Magia> canalizando = new HashMap<>();
        Map<Ranqueados, Boolean> sofreuRuptura = new HashMap<>();
        Map<Ranqueados, Integer> defesa = new HashMap<>();
        Map<Ranqueados, Ranqueados> ultimoAlvoAtacado = new HashMap<>();

        CondicaoDeCampo condicaoAtiva = arenaEscolhida.sortearCondicao();
        areaLog.append("Condição Inicial: " + condicaoAtiva.getNome() + "\n");
        for (Ranqueados ranqueado : ordemTurno) {
            aplicarCondicaoDeCampo(ranqueado, condicaoAtiva);
        }

        int turno = 1;
        boolean dueloAtivo = true;

        while (timeEstaVivo(time1) && timeEstaVivo(time2)) {
            areaLog.append("\n--- Turno " + turno + " ---\n");

            if (turno > 1 && (turno - 1) % 3 == 0) {
                for (Ranqueados mago : ordemTurno) {
                    removerCondicaoDeCampo(mago, condicaoAtiva);
                }
                condicaoAtiva = arenaEscolhida.sortearCondicao();
                areaLog.append("A CONDIÇÃO DO CAMPO MUDOU!\n");
                for (Ranqueados mago : ordemTurno) {
                    aplicarCondicaoDeCampo(mago, condicaoAtiva);
                }
            }
            areaLog.append("Condição Ativa: " + condicaoAtiva.getNome() + "\n");

            for (Ranqueados atacante : ordemTurno) {
                if (!timeEstaVivo(time1) || !timeEstaVivo(time2)) break;
                if (atacante.getVidaAtual() <= 0) continue;

                if (defesa.containsKey(atacante)) {
                    atacante.setResistencia(defesa.get(atacante));
                    defesa.remove(atacante);
                    areaLog.append(atacante.getCodinome() + " parou de defender.\n");
                }

                // Apenas modo humano (sem IA)
                String[] opcoes = {"Atacar", "Defender"};
                int acao = JOptionPane.showOptionDialog(this,
                        "Turno de: " + atacante.getCodinome() + "\nVida: " + atacante.getVidaAtual() + " | Mana: " + atacante.getManaAtual(),
                        "Ação",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

                if (acao == 0) { // Atacar
                    List<Ranqueados> adversarios = time1.contains(atacante) ? time2 : time1;
                    List<Ranqueados> alvosVivos = new ArrayList<>();
                    for (Ranqueados p : adversarios) {
                        if (p.getVidaAtual() > 0) alvosVivos.add(p);
                    }
                    if (alvosVivos.isEmpty()) {
                        areaLog.append("Não há alvos vivos para atacar! A batalha acabou!\n");
                        continue;
                    }

                    Ranqueados alvo = (Ranqueados) JOptionPane.showInputDialog(this,
                            "Escolha o alvo:",
                            "Alvo",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            alvosVivos.toArray(),
                            alvosVivos.get(0));
                    if (alvo == null) continue;

                    List<Magia> grimorio = atacante.getGrimorio();
                    List<String> nomesMagias = new ArrayList<>();
                    nomesMagias.add("Ataque básico");
                    for (Magia m : grimorio) {
                        nomesMagias.add(m.getNome() + " (Mana: " + m.getCustoMana() + ")");
                    }
                    String escolhaMagia = (String) JOptionPane.showInputDialog(this,
                            "Escolha o tipo de ataque:",
                            "Ataque",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            nomesMagias.toArray(),
                            nomesMagias.get(0));
                    Magia magiaSelecionada = null;
                    int idx = nomesMagias.indexOf(escolhaMagia);
                    if (idx > 0) magiaSelecionada = grimorio.get(idx - 1);

                    // Validação de mana
                    boolean ataqueValido = false;
                    while (!ataqueValido) {
                        if (magiaSelecionada != null && atacante.getManaAtual() < magiaSelecionada.getCustoMana()) {
                            JOptionPane.showMessageDialog(this, "Mana insuficiente para " + magiaSelecionada.getNome() + "!");
                            escolhaMagia = (String) JOptionPane.showInputDialog(this,
                                    "Escolha outro ataque:",
                                    "Ataque",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    nomesMagias.toArray(),
                                    nomesMagias.get(0));
                            idx = nomesMagias.indexOf(escolhaMagia);
                            magiaSelecionada = idx > 0 ? grimorio.get(idx - 1) : null;
                        } else {
                            if (magiaSelecionada != null)
                                atacante.setManaAtual(atacante.getManaAtual() - magiaSelecionada.getCustoMana());
                            ataqueValido = true;
                        }
                    }

                    // Canalizado
                    if (magiaSelecionada instanceof com.feitico.Canalizado) {
                        if (!canalizando.containsKey(atacante)) {
                            canalizando.put(atacante, magiaSelecionada);
                            sofreuRuptura.put(atacante, false);
                            areaLog.append(atacante.getCodinome() + " começou a canalizar " + magiaSelecionada.getNome() + "!\n");
                            continue;
                        } else if (canalizando.containsKey(atacante) && !sofreuRuptura.get(atacante)) {
                            int danoAntes = atacante.getContadorDano();
                            atacante.causarDano(alvo, magiaSelecionada);
                            int danoDepois = atacante.getContadorDano();
                            int danoAtual = danoDepois - danoAntes;
                            areaLog.append(atacante.getCodinome() + " causou " + danoAtual + " de dano em " + alvo.getCodinome() + ".\n");
                            canalizando.remove(atacante);
                            sofreuRuptura.remove(atacante);
                            areaLog.append(atacante.getCodinome() + " lançou a magia canalizada " + magiaSelecionada.getNome() + "!\n");
                        } else if (sofreuRuptura.get(atacante)) {
                            areaLog.append("A canalização de " + atacante.getCodinome() + " foi rompida!\n");
                            canalizando.remove(atacante);
                            sofreuRuptura.remove(atacante);
                            continue;
                        }
                    } else if (magiaSelecionada instanceof com.feitico.Area) {
                        if ("Sombrio".equalsIgnoreCase(atacante.getEscola())) {
                            for (Ranqueados alvoArea : alvosVivos) {
                                alvoArea.setManaAtual(Math.max(0, alvoArea.getManaAtual() - 5));
                                areaLog.append(alvoArea.getCodinome() + " teve sua mana drenada em 5 pontos!\n");
                            }
                        }
                        for (Ranqueados alvoArea : alvosVivos) {
                            atacante.causarDano(alvoArea, magiaSelecionada);
                            ultimoAlvoAtacado.put(atacante, alvoArea);
                            if (canalizando.containsKey(alvoArea)) {
                                sofreuRuptura.put(alvoArea, true);
                                areaLog.append("A canalização de " + alvoArea.getCodinome() + " foi interrompida!\n");
                                atacante.setRupturas(atacante.getRupturas() + 1);
                            }
                        }
                    } else {
                        int danoAntes = atacante.getContadorDano();
                        atacante.causarDano(alvo, magiaSelecionada);
                        int danoDepois = atacante.getContadorDano();
                        int danoAtual = danoDepois - danoAntes;
                        areaLog.append(atacante.getCodinome() + " causou " + danoAtual + " de dano em " + alvo.getCodinome() + ".\n");
                    }

                    ultimoAlvoAtacado.put(atacante, alvo);

                    if (canalizando.containsKey(alvo)) {
                        sofreuRuptura.put(alvo, true);
                        areaLog.append("A canalização de " + alvo.getCodinome() + " foi rompida por um ataque!\n");
                        atacante.setRupturas(atacante.getRupturas() + 1);
                    }
                } else if (acao == 1) { // Defender
                    defesa.put(atacante, atacante.getResistencia());
                    atacante.setResistencia(atacante.getResistencia() + atacante.getResistencia() / 2);
                    areaLog.append(atacante.getCodinome() + " está em modo de defesa!\n");
                }
            }

            areaLog.append("\n-- Status dos Times --\n");
            areaLog.append("Time 1: ");
            time1.forEach(p -> areaLog.append(p.getCodinome() + "(" + p.getVidaAtual() + ") "));
            areaLog.append("\nTime 2: ");
            time2.forEach(p -> areaLog.append(p.getCodinome() + "(" + p.getVidaAtual() + ") "));
            areaLog.append("\n");

            for (Ranqueados mago : ordemTurno) {
                if (mago.getVidaAtual() > 0) {
                    mago.setTempoEmCombate(mago.getTempoEmCombate() + 1);
                }
            }
            turno++;
        }

        areaLog.append("\n--- FIM DO DUELO ---\n");
        JOptionPane.showMessageDialog(this, "A batalha acabou! Time vencedor: " +
            (timeEstaVivo(time1) ? "Time 1" : "Time 2"));

        restaurarSystemOut();
        dispose(); // Fecha a janela e retorna ao menu principal

        List<Ranqueados> timeVencedor = timeEstaVivo(time1) ? time1 : time2;
        for (Ranqueados mago : timeVencedor) {
            mago.setCapturas(mago.getCapturas() + 1);
        }

        for (Ranqueados ranqueado : ordemTurno) {
            removerCondicaoDeCampo(ranqueado, condicaoAtiva);
        }
        for (Ranqueados ranqueado : ordemTurno) {
            ranqueado.incrementarRanking(ranqueado, new ArrayList<>(ordemTurno));
        }
    }

    private void aplicarCondicaoDeCampo(Ranqueados mago, CondicaoDeCampo condicao) {
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

    private boolean timeEstaVivo(List<Ranqueados> time) {
        for (Ranqueados p : time) {
            if (p.getVidaAtual() > 0) {
                return true;
            }
        }
        return false;
    }

    private void redirecionarSystemOutParaAreaLog() {
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                areaLog.append(String.valueOf((char) b));
            }
        });
        originalOut = System.out;
        System.setOut(printStream);
    }

    private void restaurarSystemOut() {
        if (originalOut != null) {
            System.setOut(originalOut);
        }
    }

    {
        for (Ranqueados mago : time1) {
            mago.setVidaAtual(mago.getVidaMax());
        }
        for (Ranqueados mago : time2) {
            mago.setVidaAtual(mago.getVidaMax());
        }
    }
}
