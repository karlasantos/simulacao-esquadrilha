/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esquadrilha;

import com.sun.management.GarbageCollectionNotificationInfo;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Gerenciador da simulação
 * @author Karla
 */
public class SimulacaoEsquadrilha {
    /**
     * Tamanho da matriz utilizada na simulação como representação do céu
     */
    static final Integer TAMANHO_MATRIZ_CEU = 10;
    
    /**
     * Matriz utilizada como representação do céu
     */
    static String[][] ceu = new String[TAMANHO_MATRIZ_CEU][TAMANHO_MATRIZ_CEU];
    
    /**
     * Opção de visualização escolhida pelo usuário
     */
    static Integer visualizacao;
    
    /**
     * Tempo corrente de simulação
     */
    static Integer tempoCorrente;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        executar();
    }
    
    /**
     * Executor da simulação
     */
    public static void executar() {
        
        Aviao esquadrilhaAbutre = new Aviao(5, 0, 'L', 5, 9);
        Pombo pomboCorreio = new Pombo(5, 9, 'O', 4, 3); 
        Scanner leitura = new Scanner(System.in);
        
        printsInformacoes();
       
        //Leitura de opção de visualização
        do {
            System.out.print("Visualizar simulação em [(1) Linha | (2) Matriz]: ");
            visualizacao = leitura.nextInt();
            if(visualizacao == 1 || visualizacao == 2) {
                System.out.println("\n------------------- Posições Iniciais -----------------------");
                escolhaOpcaoImpressao(pomboCorreio, esquadrilhaAbutre);
            }
        }while(visualizacao != 1 && visualizacao != 2);
        
        for(tempoCorrente = 1; ; tempoCorrente++) {          
            //incrementa os tempos de checagem
            pomboCorreio.setContadorTempoChecagem(pomboCorreio.getContadorTempoChecagem()+1);
            esquadrilhaAbutre.setContadorTempoChecagem(esquadrilhaAbutre.getContadorTempoChecagem()+1);
            
            //verifica se já pode visualizar a localização do avião
            if(pomboCorreio.check()) {
                pomboCorreio.fugirAviao(esquadrilhaAbutre.getPosicaoAtual(), esquadrilhaAbutre.getDirecaoFrente(), esquadrilhaAbutre.getVelocidade());
            } else { //caso não define para onde irá voar de acordo com suas táticas
                pomboCorreio.voar(pomboCorreio.definirTatica());
            }
            
            //Imprime o tempo
            System.out.println("\n");
            System.out.println("+++++++++++++++++++++++++++");
            System.out.println("Tempo: " + tempoCorrente + "s");
            System.out.println("+++++++++++++++++++++++++++");
            
            
            //Imprime o estado atual após o movimento do pombo
            System.out.println("MOVIMENTO DO POMBO:");
            escolhaOpcaoImpressao(pomboCorreio, esquadrilhaAbutre);
            
            //verifica a cada movimento do pombo se a simulação terminou
            if(isEnd(esquadrilhaAbutre, pomboCorreio)) {
                escolhaOpcaoImpressao(pomboCorreio, esquadrilhaAbutre);
                System.out.println("THE END!");
                break;
            }
            
            System.out.println("MOVIMENTO DA ESQUADRILHA:");
            
            //verifica se já pode visualizar a localização do pombo
            if(esquadrilhaAbutre.check()) {
                //Faz movimento e já verifica se pegou o pombo
                if(esquadrilhaAbutre.pegarPombo(pomboCorreio.getPosicaoAtual(), pomboCorreio.getDirecaoFrente())) {   
                    escolhaOpcaoImpressao(pomboCorreio, esquadrilhaAbutre);
                    System.out.println("THE END!");
                    break;
                }
            } else { //caso contrário continua voando para sua direção atual
                esquadrilhaAbutre.voar(esquadrilhaAbutre.getDirecaoFrente());
                
                //verifica se a simulação terminou
                if(isEnd(esquadrilhaAbutre, pomboCorreio)) {
                    escolhaOpcaoImpressao(pomboCorreio, esquadrilhaAbutre);
                    System.out.println("THE END!");
                    break;
                }
            }
            //Imprime estado atual após o movimento da esquadrilha
            escolhaOpcaoImpressao(pomboCorreio, esquadrilhaAbutre);
        }
    }

    /**
     * Veirifica o fim de simulação
     * 
     * @param posicaoAviao Posicao
     * @param posicaoPombo Posicao
     * @param direcaoAviao char
     * @param direcaoPombo char
     * @return Boolean
     */
    public static Boolean isEnd(Aviao esquadrilhaAbutre, Pombo pomboCorreio) {
        if (esquadrilhaAbutre.getPosicaoAtual().getLinha() == pomboCorreio.getPosicaoAtual().getLinha() && esquadrilhaAbutre.getPosicaoAtual().getColuna() == pomboCorreio.getPosicaoAtual().getColuna()) {
            return true;
        }
        else if(((esquadrilhaAbutre.getPosicaoAtual().getLinha() == pomboCorreio.getPosicaoAtual().getLinha() && esquadrilhaAbutre.getPosicaoAtual().getColuna() < pomboCorreio.getPosicaoAtual().getColuna()) && isOposta(esquadrilhaAbutre.getDirecaoFrente(), pomboCorreio.getDirecaoFrente())) || ((esquadrilhaAbutre.getPosicaoAtual().getLinha() == pomboCorreio.getPosicaoAtual().getLinha() && esquadrilhaAbutre.getPosicaoAtual().getColuna() > pomboCorreio.getPosicaoAtual().getColuna()) && isOposta(esquadrilhaAbutre.getDirecaoFrente(), pomboCorreio.getDirecaoFrente()))){       
            esquadrilhaAbutre.getPosicaoAtual().setColuna(pomboCorreio.getPosicaoAtual().getColuna());
            return true;
        }     
        else if(((esquadrilhaAbutre.getPosicaoAtual().getLinha() > pomboCorreio.getPosicaoAtual().getLinha() && esquadrilhaAbutre.getPosicaoAtual().getColuna() == pomboCorreio.getPosicaoAtual().getColuna()) && isOposta(esquadrilhaAbutre.getDirecaoFrente(), pomboCorreio.getDirecaoFrente())) || ((esquadrilhaAbutre.getPosicaoAtual().getLinha() < pomboCorreio.getPosicaoAtual().getLinha() && esquadrilhaAbutre.getPosicaoAtual().getColuna() == pomboCorreio.getPosicaoAtual().getColuna()) && isOposta(esquadrilhaAbutre.getDirecaoFrente(), pomboCorreio.getDirecaoFrente()))) {
            esquadrilhaAbutre.getPosicaoAtual().setLinha(pomboCorreio.getPosicaoAtual().getLinha());
            return true;
        } 
        return false;

    }
    
    /**
     * Verifica se oponentes estão em direções opostas
     * 
     * @param direcaoAviao char
     * @param direcaoPombo char
     * @return Boolean
     */
    public static Boolean isOposta(char direcaoAviao, char direcaoPombo) {
        return (direcaoAviao == 'N' && direcaoPombo == 'S' || direcaoAviao == 'S' && direcaoPombo == 'N' || direcaoAviao == 'L' && direcaoPombo == 'O' || direcaoAviao == 'O' && direcaoPombo == 'L');
    }
    
    /**
     * Impressão do resultado de cada passagem de tempo em linhas
     * 
     * @param pomboCorreio Pombo
     * @param esquadrilhaAbutre Aviao
     */
    public static void printsInline(Pombo pomboCorreio, Aviao esquadrilhaAbutre) {
        System.out.println(" Posicao Pombo Correio       - linha: " + pomboCorreio.getPosicaoAtual().getLinha() + " e coluna: " + pomboCorreio.getPosicaoAtual().getColuna() + "      - Direcao: " + pomboCorreio.getDirecaoFrente());
        System.out.println(" Posicao Esquadrilha Abutre  - linha: " + esquadrilhaAbutre.getPosicaoAtual().getLinha() + " e coluna: " + esquadrilhaAbutre.getPosicaoAtual().getColuna() + "      - Direcao: " + esquadrilhaAbutre.getDirecaoFrente() + "\n");
    }
    
    /**
     * Impressão do resultado de cada passagem de tempo em forma de matriz
     * @param pomboCorreio Pombo
     * @param esquadrilhaAbutre Avião
     */
    public static void printsMatriz(Pombo pomboCorreio, Aviao esquadrilhaAbutre) {
        Integer linha, coluna;
        if(esquadrilhaAbutre.getPosicaoAtual().getLinha() == pomboCorreio.getPosicaoAtual().getLinha() && esquadrilhaAbutre.getPosicaoAtual().getColuna() == pomboCorreio.getPosicaoAtual().getColuna())
            ceu[pomboCorreio.getPosicaoAtual().getLinha()][pomboCorreio.getPosicaoAtual().getColuna()] = "P" + direcaoSimbol(pomboCorreio.getDirecaoFrente()) + "A" + direcaoSimbol(esquadrilhaAbutre.getDirecaoFrente());
        else {
            ceu[pomboCorreio.getPosicaoAtual().getLinha()][pomboCorreio.getPosicaoAtual().getColuna()] = " P" + direcaoSimbol(pomboCorreio.getDirecaoFrente());
            ceu[esquadrilhaAbutre.getPosicaoAtual().getLinha()][esquadrilhaAbutre.getPosicaoAtual().getColuna()] = " A" + direcaoSimbol(esquadrilhaAbutre.getDirecaoFrente());
        }

        for(linha=0; linha < TAMANHO_MATRIZ_CEU; linha++) {
            System.out.println("-------------------------------------------------------------");
            System.out.print("| ");
            for(coluna=0; coluna < TAMANHO_MATRIZ_CEU; coluna++) {
                System.out.print(((ceu[linha][coluna] != null)? ceu[linha][coluna] : "   ") + " | ");
                ceu[linha][coluna] = null;
            }
            System.out.println("");
        }
        System.out.println("-------------------------------------------------------------");
        

    }
    
    /**
     * Impressão da simbolização de direção [N(^), S(v), L(>), O(<)]
     * @param direcao char
     * @return char
     */
    public static char direcaoSimbol(char direcao) {
        switch(direcao) {
            case 'N':
                return '^';
            case 'S':
                return 'v';
            case 'L':
                return '>';
            default:
                return '<';
        }
    }
    
    /**
     * Seleciona a opção de impressão
     * @param pomboCorreio Pombo
     * @param esquadrilhaAbutre Avião
     */
    public static void escolhaOpcaoImpressao(Pombo pomboCorreio, Aviao esquadrilhaAbutre) {
        switch(visualizacao) {
            case 1:
                printsInline(pomboCorreio, esquadrilhaAbutre);
                break;
            case 2:
                printsMatriz(pomboCorreio, esquadrilhaAbutre);
                break;
            default:
                System.out.println("Opção inválida, tente novamente!");
        }
    }
    
    /**
     * Impressão das informações da simulação
     */
    public static void printsInformacoes() {
        System.out.println("+---------------------------------------------------------------------------- INFORMAÇÕES -------------------------------------------------------------------------------+");
        System.out.println("\tCritérios utilizados na representação:");
        System.out.println("\t\tO campo de voo é representado por uma matriz 10x10");
        System.out.println("\t\tPosições da matriz: cada posição representa 10m");
        System.out.println("\t\tVelocidades: a cada segundo da simulação o Pombo Correio pode voar 3 casas na matriz (30m/s) enquanto o Avião da Esquadrilha Abutre pode voar 9 (90m/s)");
        System.out.println("\t\tDireções de voo: as escolhas de direções de voo são dadas através de números aleatórios, e cada direção possui uma porcentagem x de ocorrência");
        System.out.println("\t\tSe um determinado personagem chegar na borda da matriz e não tiver realizado todo o voo uma volta na matriz será realizada");

        
        System.out.println("\n\tVisualização em formato de linhas: visualização descritiva da posição e direção de cada personagem;\n");
        System.out.println("\tVisualização em formato de matriz: visualização dos personagens em suas posições com suas respectivas direções \n\t\tP = Pombo Correio e A = Avião Esquadrilha Abutre");
        System.out.println("\t\tDireções atuais: N(^), S(v), L(>), O(<)");
        System.out.println("\t\tExemplo: 'P>' = Pombo Correio indo para direção Leste");
        System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------------------------+\n");

    }
}
