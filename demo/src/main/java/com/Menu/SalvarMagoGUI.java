package com.Menu;

import javax.swing.*;
import java.awt.*;
import com.GerenciadorDeMagos.Gerenciador;
import com.csv.GerenciadorCSV;

public class SalvarMagoGUI extends JFrame {

    public SalvarMagoGUI(Gerenciador gerenciador, GerenciadorCSV gerenciadorCSV, String nomeArquivo) {
        setTitle("Salvar Magos em CSV");
        setSize(350, 150);
        setLocationRelativeTo(null);

        JLabel lblInfo = new JLabel("Clique para salvar todos os magos em arquivo CSV:");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnFechar = new JButton("Fechar");

        btnSalvar.addActionListener(e -> {
            gerenciadorCSV.salvar(gerenciador.listarTodos(), nomeArquivo);
            JOptionPane.showMessageDialog(this, "Magos salvos com sucesso em " + nomeArquivo);
        });

        btnFechar.addActionListener(e -> dispose());

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.add(lblInfo);
        panel.add(btnSalvar);
        panel.add(btnFechar);

        add(panel);
        setVisible(true);
    }
}
