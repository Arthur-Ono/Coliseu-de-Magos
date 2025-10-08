package com.IA;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import com.personagem.Ranqueados;

public class LogicaIA {

    private Random random = new Random();

    // O método principal que toma a decisão com base no perfil da IA.
    public AcaoIA decidirAcao(Ranqueados iaPersonagem, List<Ranqueados> aliados, List<Ranqueados> inimigos) {

        // Filtra a lista de inimigos para conter apenas os que estão vivos.
        List<Ranqueados> inimigosVivos = inimigos.stream()
                                                .filter(p -> p.getVidaAtual() > 0)
                                                .collect(Collectors.toList());

        if (inimigosVivos.isEmpty()) {
            // Se não há inimigos, a IA não faz nada (embora a batalha já devesse ter acabado).
            return new AcaoIA(TipoAcao.DEFENDER, null, null);
        }

        // Usa o 'controlador' do personagem para decidir qual estratégia usar.
        switch (iaPersonagem.getControlador()) {
            case 2: // Perfil Agressivo
                return estrategiaAgressiva(iaPersonagem, inimigosVivos);
            case 3: // Perfil Defensivo
                return estrategiaDefensiva(iaPersonagem, inimigosVivos);
            case 4: // Perfil Tático
            default: // Caso padrão
                return estrategiaTatica(iaPersonagem, inimigosVivos);
        }
    }

    // --- ESTRATÉGIAS DE CADA PERFIL ---

    private AcaoIA estrategiaAgressiva(Ranqueados ia, List<Ranqueados> inimigos) {
        Ranqueados alvo = encontrarInimigoComMenorVida(inimigos);
        // Agressivo sempre tenta um ataque básico no alvo mais fraco.
        return new AcaoIA(TipoAcao.ATACAR_BASICO, alvo, null);
    }

    private AcaoIA estrategiaDefensiva(Ranqueados ia, List<Ranqueados> inimigos) {
        // Se a vida da IA estiver abaixo de 40%, ela se defende.
        if ((double) ia.getVidaAtual() / ia.getVidaMax() < 0.4) {
            return new AcaoIA(TipoAcao.DEFENDER, null, null);
        }
        // Senão, ataca o inimigo mais forte para neutralizar a maior ameaça.
        Ranqueados alvo = encontrarInimigoMaisForte(inimigos);
        return new AcaoIA(TipoAcao.ATACAR_BASICO, alvo, null);
    }

    private AcaoIA estrategiaTatica(Ranqueados ia, List<Ranqueados> inimigos) {
        // Apenas ataca um inimigo aleatório que esteja vivo.
        Ranqueados alvo = inimigos.get(random.nextInt(inimigos.size()));
        return new AcaoIA(TipoAcao.ATACAR_BASICO, alvo, null);
    }

    // --- MÉTODOS "AJUDANTES" PARA ENCONTRAR ALVOS ---

    private Ranqueados encontrarInimigoComMenorVida(List<Ranqueados> inimigos) {
        Ranqueados alvoEscolhido = null;
        int menorVida = Integer.MAX_VALUE;
        for (Ranqueados inimigo : inimigos) {
            if (inimigo.getVidaAtual() < menorVida) {
                menorVida = inimigo.getVidaAtual();
                alvoEscolhido = inimigo;
            }
        }
        return alvoEscolhido;
    }

    private Ranqueados encontrarInimigoMaisForte(List<Ranqueados> inimigos) {
        Ranqueados alvoEscolhido = null;
        int maiorPoder = -1;
        for (Ranqueados inimigo : inimigos) {
            if (inimigo.getPoderBase() > maiorPoder) {
                maiorPoder = inimigo.getPoderBase();
                alvoEscolhido = inimigo;
            }
        }
        return alvoEscolhido;
    }
}