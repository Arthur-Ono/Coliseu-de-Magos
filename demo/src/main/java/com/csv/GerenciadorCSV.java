package com.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.personagem.MagoElemental;
import com.personagem.Personagem;
import com.personagem.Ranqueados; // Importa a nova classe Ranqueados

public class GerenciadorCSV {

    public void salvar(List<Personagem> magos, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            //Intancia um objeto writer da classe BufferedWriter e cria um novo arquivo para poder escrever nele
            writer.write("id,codinome,escola,vidaMax,manaMax,foco,poderBase,resistencia,controlador,horaEntrada,tipo,abates,assistencias,danoCausado,danoMitigado,rupturas,capturas");
            writer.newLine();
            //Cria um cabeçalho e pula uma linha
            for (Personagem mago : magos) {
                //Foreach, normal
                StringBuilder linha = new StringBuilder();
                //Crie tipo uma caixa de texto editável e flexível, permitindo assim "concatenar" appends
                linha.append(mago.getId()).append(",");
                //Escreve o elemento Id do mago, que é passado como parâmetro e depois cria uma vígula, que foi concatenada.
                //Poderia ser escrito como linha.append(mago.id); (na outra linha) linha.append(",");
                //Mas aí teria que criar uma vírgula em cada linha, o que é menos eficiente
                linha.append(mago.getCodinome()).append(",");
                linha.append(mago.getEscola()).append(",");
                linha.append(mago.getVidaMax()).append(",");
                linha.append(mago.getManaMax()).append(",");
                linha.append(mago.getFoco()).append(",");
                linha.append(mago.getPoderBase()).append(",");
                linha.append(mago.getResistencia()).append(",");
                linha.append(mago.getControlador()).append(",");
                linha.append(mago.getHoraEntrada()).append(",");
                linha.append(mago.getClass().getSimpleName());
                //Pega o nome da classe do mago (MagoElemental, MagoArcano, etc) e escreve no CSV
                //Porque o getSimple? Pra pegar apenas o nome da classe, em string, e não o objetivo inteiro.
                //Porque isso? pra não dar aquela desgraça de "class com.personagem.MagoElemental"
                
                if (mago instanceof Ranqueados) {
                    //verifica se o mago é uma instância de Ranqueados antes de tentar acessar os métodos de ranking
                    Ranqueados ranqueado = (Ranqueados) mago;
                    linha.append(",").append(ranqueado.getAbates());
                    linha.append(",").append(ranqueado.getAssistencias());
                    linha.append(",").append(ranqueado.getDanoCausado());
                    linha.append(",").append(ranqueado.getDanoMitigado());
                    linha.append(",").append(ranqueado.getRupturas());
                    linha.append(",").append(ranqueado.getCapturas());
                } else {
                    // Se não for ranqueado, preenche com zeros para manter a estrutura do CSV
                    linha.append(",0,0,0,0,0,0");
                }
                
                writer.write(linha.toString());
                //Transforma a "caixa de texto" em uma string e escreve no arquivo
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo CSV: " + e.getMessage());
        }
    }

    public List<Personagem> carregar(String nomeArquivo) {
        List<Personagem> magos = new ArrayList<>();
        File arquivo = new File(nomeArquivo);

        if (!arquivo.exists()) {
            return magos;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            reader.readLine();

            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");

                int id = Integer.parseInt(dados[0]);
                String codinome = dados[1];
                String escola = dados[2];
                int vidaMax = Integer.parseInt(dados[3]);
                int manaMax = Integer.parseInt(dados[4]);
                String foco = dados[5];
                int poderBase = Integer.parseInt(dados[6]);
                int resistencia = Integer.parseInt(dados[7]);
                int controlador = Integer.parseInt(dados[8]);
                int horaEntrada = Integer.parseInt(dados[9]);
                String tipo = dados[10];

                // Carrega os dados de ranking
                int abates = Integer.parseInt(dados[11]);
                int assistencias = Integer.parseInt(dados[12]);
                int danoCausado = Integer.parseInt(dados[13]);
                int danoMitigado = Integer.parseInt(dados[14]);
                int rupturas = Integer.parseInt(dados[15]);
                int capturas = Integer.parseInt(dados[16]);
                
                Personagem mago = null;
                switch (tipo) {
                    case "Ranqueados":
                        mago = new Ranqueados(id, codinome, escola, vidaMax, manaMax, foco, poderBase, resistencia, controlador, horaEntrada, abates, assistencias, danoCausado, danoMitigado, rupturas, capturas);
                        break;
                    
                }
                
                if (mago != null) {
                    magos.add(mago);
                }
            }
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Erro ao carregar o arquivo CSV: " + e.getMessage());
        }

        return magos;
    }
}