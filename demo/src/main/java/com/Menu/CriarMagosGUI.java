package com.Menu;

import javax.swing.*;
import java.awt.*;
import com.GerenciadorDeMagos.Gerenciador;
import com.personagem.MagoArcano;
import com.personagem.MagoElemental;
import com.personagem.MagoSombrio;

import javax.swing.event.ChangeListener;

public class CriarMagosGUI extends JFrame {

    public CriarMagosGUI(Gerenciador gerenciador) {
        setTitle("Criar Mago");
        setSize(420, 420);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 3, 10, 10));

        JTextField txtId = new JTextField();
        JTextField txtCodinome = new JTextField();
        JTextField txtFoco = new JTextField();
        JComboBox<String> cmbEscola = new JComboBox<>(new String[] { "Elemental", "Arcano", "Sombrio" });

        // Campos para distribuição de pontos
        JSpinner spVida = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        JSpinner spMana = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        JSpinner spPoder = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        JSpinner spResistencia = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        JSpinner spVelocidade = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));

        JLabel lblVidaValor = new JLabel("10");
        JLabel lblManaValor = new JLabel("10");
        JLabel lblPoderValor = new JLabel("10");
        JLabel lblResistenciaValor = new JLabel("10");
        JLabel lblVelocidadeValor = new JLabel("10");
        JLabel lblPontosRestantes = new JLabel("Pontos restantes: 10");

        panel.add(new JLabel("ID:"));
        panel.add(txtId);
        panel.add(new JLabel(""));
        panel.add(new JLabel("Codinome:"));
        panel.add(txtCodinome);
        panel.add(new JLabel(""));
        panel.add(new JLabel("Foco:"));
        panel.add(txtFoco);
        panel.add(new JLabel(""));
        panel.add(new JLabel("Escola:"));
        panel.add(cmbEscola);
        panel.add(new JLabel(""));

        panel.add(new JLabel("Pontos em Vida:"));
        panel.add(spVida);
        panel.add(lblVidaValor);
        panel.add(new JLabel("Pontos em Mana:"));
        panel.add(spMana);
        panel.add(lblManaValor);
        panel.add(new JLabel("Pontos em Poder Base:"));
        panel.add(spPoder);
        panel.add(lblPoderValor);
        panel.add(new JLabel("Pontos em Resistência:"));
        panel.add(spResistencia);
        panel.add(lblResistenciaValor);
        panel.add(new JLabel("Pontos em Velocidade:"));
        panel.add(spVelocidade);
        panel.add(lblVelocidadeValor);

        JPanel btnPanel = new JPanel();
        JButton btnCriar = new JButton("Criar");
        JButton btnFechar = new JButton("Fechar");
        btnPanel.add(btnCriar);
        btnPanel.add(btnFechar);

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.add(lblPontosRestantes);

        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
        add(infoPanel, BorderLayout.NORTH);

        // Atualiza os valores dos atributos e pontos restantes ao mudar qualquer
        // spinner
        ChangeListener updateListener = e -> {
            int pontosDistribuidos = (int) spVida.getValue() + (int) spMana.getValue() +
                    (int) spPoder.getValue() + (int) spResistencia.getValue() +
                    (int) spVelocidade.getValue();

            // Se passou de 10, volta o spinner que mudou para o valor anterior
            if (pontosDistribuidos > 10) {
                JSpinner source = (JSpinner) e.getSource();
                int valorAtual = (int) source.getValue();
                source.setValue(valorAtual - 1);
                return;
            }

            int vida = 10 + ((int) spVida.getValue() * 5);
            int mana = 10 + ((int) spMana.getValue() * 5);
            int poder = 10 + ((int) spPoder.getValue() * 5);
            int resistencia = 10 + ((int) spResistencia.getValue() * 5);
            int velocidade = 10 + ((int) spVelocidade.getValue() * 5);

            lblVidaValor.setText(String.valueOf(vida));
            lblManaValor.setText(String.valueOf(mana));
            lblPoderValor.setText(String.valueOf(poder));
            lblResistenciaValor.setText(String.valueOf(resistencia));
            lblVelocidadeValor.setText(String.valueOf(velocidade));

            lblPontosRestantes.setText("Pontos restantes: " + (10 - pontosDistribuidos));
        };

        spVida.addChangeListener(updateListener);
        spMana.addChangeListener(updateListener);
        spPoder.addChangeListener(updateListener);
        spResistencia.addChangeListener(updateListener);
        spVelocidade.addChangeListener(updateListener);

        btnCriar.addActionListener(e -> {
            try {
                int pontosDistribuidos = (int) spVida.getValue() + (int) spMana.getValue() +
                        (int) spPoder.getValue() + (int) spResistencia.getValue() +
                        (int) spVelocidade.getValue();

                if (pontosDistribuidos != 10) {
                    JOptionPane.showMessageDialog(this, "Distribua exatamente 10 pontos entre os atributos!");
                    return;
                }

                int id = Integer.parseInt(txtId.getText());
                String codinome = txtCodinome.getText();
                String foco = txtFoco.getText();
                String escola = (String) cmbEscola.getSelectedItem();

                int vida = 10 + ((int) spVida.getValue() * 5);
                int mana = 10 + ((int) spMana.getValue() * 5);
                int poder = 10 + ((int) spPoder.getValue() * 5);
                int resistencia = 10 + ((int) spResistencia.getValue() * 5);
                int velocidade = 10 + ((int) spVelocidade.getValue() * 5);

                if ("Elemental".equals(escola)) {
                    gerenciador.adicionar(
                        new MagoElemental(id, codinome, vida, mana, foco, poder, resistencia, 0, 0, velocidade)
                    );
                } else if ("Arcano".equals(escola)) {
                    gerenciador.adicionar(
                        new MagoArcano(id, codinome, vida, mana, foco, poder, resistencia, 0, 0, velocidade)
                    );
                } else if ("Sombrio".equals(escola)) {
                    gerenciador.adicionar(
                        new MagoSombrio(id, codinome, vida, mana, foco, poder, resistencia, 0, 0, velocidade)
                    );
                }

                JOptionPane.showMessageDialog(this, "Mago criado com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente!");
            }
        });

        btnFechar.addActionListener(e -> dispose());

        setVisible(true);
    }
}
