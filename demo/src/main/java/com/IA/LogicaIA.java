package com.IA;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import com.personagem.Ranqueados;
import com.feitico.Magia;
import com.feitico.Canalizado;

// Esta classe é o "cérebro" da IA. A única responsabilidade dela é decidir
// qual ação um mago controlado por IA deve tomar durante seu turno.
public class LogicaIA {

    // Instancio um objeto 'Random' uma única vez. É mais eficiente do que criar um novo a cada decisão.
    private Random random = new Random();

    
    public AcaoIA decidirAcao(Ranqueados iaPersonagem, List<Ranqueados> aliados, List<Ranqueados> inimigos) {

        // Antes de mais nada, eu preciso de uma lista apenas com os inimigos que ainda estão vivos,
        // para a IA não gastar um ataque em um corpo morto.
        List<Ranqueados> inimigosVivos = inimigos.stream()
                .filter(p -> p.getVidaAtual() > 0)
                .collect(Collectors.toList());

        // Uma verificação de segurança: se não há mais inimigos vivos, a batalha já deveria ter acabado.
        // Mas por via das dúvidas, se chegar aqui, a IA decide defender (passar o turno).
        if (inimigosVivos.isEmpty()) {
            return new AcaoIA(TipoAcao.DEFENDER, null, null);
        }

        // --- A LÓGICA DINÂMICA DE "HUMOR" DA IA ---
        
        // Aqui eu calculo a porcentagem de vida da IA.
        // O '(double)' é importante para forçar uma divisão com casas decimais (ex: 0.35),
        // em vez de uma divisão de inteiros (que daria 0).
        double porcentagemVida = (double) iaPersonagem.getVidaAtual() / iaPersonagem.getVidaMax();

        // Agora, com base na vida, a IA decide seu "humor" para este turno.
        if (porcentagemVida < 0.35) { // Se a vida estiver crítica (abaixo de 35%)
            // A IA fica com medo e entra em modo defensivo.
            System.out.println(iaPersonagem.getCodinome() + " (IA) está ferido e agindo com cautela!");
            return estrategiaDefensiva(iaPersonagem, inimigosVivos);
            
        } else if (porcentagemVida > 0.75) { // Se a vida estiver alta (acima de 75%)
            // A IA fica confiante e entra em modo agressivo.
            System.out.println(iaPersonagem.getCodinome() + " (IA) está confiante e parte para o ataque!");
            return estrategiaAgressiva(iaPersonagem, inimigosVivos);
            
        } else { // Se a vida estiver em um nível médio (entre 35% e 75%)
            // A IA age de forma mais equilibrada, sem grandes riscos.
            System.out.println(iaPersonagem.getCodinome() + " (IA) analisa o campo de batalha...");
            return estrategiaTatica(iaPersonagem, inimigosVivos);
        }
    }

    // --- ESTRATÉGIAS (As "emoções" da IA) ---

    // Agressivo: Tenta usar a magia que tiver no alvo mais fraco para finalizar.
    private AcaoIA estrategiaAgressiva(Ranqueados ia, List<Ranqueados> inimigos) {
        // Primeiro, o "olho" da IA encontra o inimigo com a menor vida.
        Ranqueados alvo = encontrarInimigoComMenorVida(inimigos);
        
        // Agora, a IA olha para o próprio grimório para ver se pode usar alguma magia.
        for (Magia magia : ia.getGrimorio()) {
            // A IA (por enquanto) é simples e não sabe usar magias canalizadas, então ela as ignora.
            // Ela verifica se tem mana suficiente para a magia.
            if (ia.getManaAtual() >= magia.getCustoMana() && !(magia instanceof Canalizado)) {
                // Se encontrou uma magia que pode usar, essa é a decisão dela.
                return new AcaoIA(TipoAcao.USAR_MAGIA, alvo, magia);
            }
        }
        // Se o loop terminar e ela não encontrar nenhuma magia que possa usar (ou não tiver mana),
        // ela parte para o ataque básico como plano B.
        return new AcaoIA(TipoAcao.ATACAR_BASICO, alvo, null);
    }

    // Defensivo: A prioridade é sobreviver e eliminar a maior ameaça.
    private AcaoIA estrategiaDefensiva(Ranqueados ia, List<Ranqueados> inimigos) {
        // A decisão de usar a ação "Defender" de fato, já foi tratada lá em cima.
        // Se o código chegou até aqui, significa que a IA, mesmo com medo, decidiu que a melhor
        // defesa é o ataque. Nesse caso, o alvo é o inimigo mais forte, para tirar a maior ameaça de jogo.
        Ranqueados alvo = encontrarInimigoMaisForte(inimigos);
        return new AcaoIA(TipoAcao.ATACAR_BASICO, alvo, null);
    }

    // Tático: Uma abordagem equilibrada e um pouco imprevisível.
    private AcaoIA estrategiaTatica(Ranqueados ia, List<Ranqueados> inimigos) {
        // Apenas ataca um inimigo aleatório que esteja vivo. Simples e direto.
        Ranqueados alvo = inimigos.get(random.nextInt(inimigos.size()));
        return new AcaoIA(TipoAcao.ATACAR_BASICO, alvo, null);
    }

    // --- MÉTODOS "AJUDANTES" (Os "sentidos" da IA) ---

    // Este método funciona como o "olho" da IA para encontrar o alvo mais ferido.
    private Ranqueados encontrarInimigoComMenorVida(List<Ranqueados> inimigos) {
        Ranqueados alvoEscolhido = null;
        int menorVida = Integer.MAX_VALUE; // Começo com um valor de vida absurdamente alto.
        for (Ranqueados inimigo : inimigos) {
            // Se a vida do inimigo atual for menor que a menor vida que eu encontrei até agora...
            if (inimigo.getVidaAtual() < menorVida) {
                // ...então este inimigo se torna o meu novo alvo preferido.
                menorVida = inimigo.getVidaAtual();
                alvoEscolhido = inimigo;
            }
        }
        return alvoEscolhido;
    }

    // Este método funciona como o "olho" da IA para encontrar o alvo mais perigoso.
    private Ranqueados encontrarInimigoMaisForte(List<Ranqueados> inimigos) {
        Ranqueados alvoEscolhido = null;
        int maiorPoder = -1; // Começo com um valor de poder negativo.
        for (Ranqueados inimigo : inimigos) {
            // Se o poder base do inimigo atual for maior que o maior poder que eu vi até agora...
            if (inimigo.getPoderBase() > maiorPoder) {
                // ...então ele se torna a maior ameaça e meu novo alvo.
                maiorPoder = inimigo.getPoderBase();
                alvoEscolhido = inimigo;
            }
        }
        return alvoEscolhido;
    }
}