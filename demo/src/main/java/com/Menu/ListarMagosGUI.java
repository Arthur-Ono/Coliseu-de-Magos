package com.Menu;

import javax.swing.*;
import java.awt.*;
import com.GerenciadorDeMagos.Gerenciador;
import com.personagem.Ranqueados;
import java.util.List;

public class ListarMagosGUI extends JFrame {

    public ListarMagosGUI(Gerenciador gerenciador) {
        setTitle("Lista de Magos");
        setSize(400, 400);
        setLocationRelativeTo(null);

        List<Ranqueados> magos = gerenciador.listarTodos();

        StringBuilder texto = new StringBuilder();
        if (magos.isEmpty()) {
            texto.append("Nenhum mago cadastrado no momento.");
        } else {
            for (Ranqueados mago : magos) {
                texto.append(mago.toString()).append("\n\n");
            }
        }

        JTextArea areaMagos = new JTextArea(texto.toString());
        areaMagos.setEditable(false);
        areaMagos.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(areaMagos);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnFechar, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}
