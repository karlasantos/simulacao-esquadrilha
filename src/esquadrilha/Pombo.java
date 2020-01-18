/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esquadrilha;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Representação do Pombo
 * @author Karla
 */
public class Pombo extends Voador {

    public Pombo(Integer linha, Integer coluna, char direcaoFrente, Integer tempoChecagem, Integer velocidade) {
        super(new Posicao(linha, coluna), direcaoFrente, tempoChecagem, velocidade);
    }
    
    /**
     * Definir qual tática de voo será usada no momento
     * @return 
     */
    public char definirTatica() {
        int probabilidade = ThreadLocalRandom.current().nextInt(1, 100);
        
        if(probabilidade >= 1 && probabilidade <= 25) //25% para sul
            return 'S';
        else if(probabilidade >= 26 && probabilidade <= 50 ) //25% para norte
            return 'N';
        else if(probabilidade >= 51 && probabilidade <= 75) //25% para a leste
            return 'L';
        //25% para oeste
        return 'O';
    }
    
    /**
     * Decide qual manobra para fugir do avião e a executa
     * 
     * @param posicaoAviao Posicao
     * @param direcaoAviao char
     * @param velocidadeAviao Integer
     */
    public void fugirAviao(Posicao posicaoAviao, char direcaoAviao, Integer velocidadeAviao) {
        Integer proximaPosicaoAviao;
        Integer proximaPosicaoPombo;
        Integer probabilidade = ThreadLocalRandom.current().nextInt(1, 100);
        char novaDirecao;
        Boolean aviaoVoltaMatriz = false;
        Boolean pomboVoltaMatriz = false;
        
        //se as direções forem iguais, analisa se poderá ou não ser apanhado, essa previsão influencia na probabilidade de sua escolha para seguir
        if(direcaoAviao == getDirecaoFrente()) {
            switch(direcaoAviao) {
                case 'N':
                    proximaPosicaoAviao = posicaoAviao.getLinha()-velocidadeAviao;
                    proximaPosicaoPombo = getPosicaoAtual().getLinha()-getVelocidade();
                    break;
                case 'S':
                    proximaPosicaoAviao = posicaoAviao.getLinha()+velocidadeAviao;
                    proximaPosicaoPombo = getPosicaoAtual().getLinha()+getVelocidade();                   
                    break;
                case 'L':
                    proximaPosicaoAviao = posicaoAviao.getColuna()+velocidadeAviao;                   
                    proximaPosicaoPombo = getPosicaoAtual().getColuna()+getVelocidade();
                    break;
                default:
                    proximaPosicaoAviao = posicaoAviao.getColuna()-velocidadeAviao;
                    proximaPosicaoPombo = getPosicaoAtual().getColuna()-getVelocidade();
            }
            
            //controles de retornos na matriz
            if(proximaPosicaoAviao >= SimulacaoEsquadrilha.TAMANHO_MATRIZ_CEU) {
                proximaPosicaoAviao -= SimulacaoEsquadrilha.TAMANHO_MATRIZ_CEU;
                aviaoVoltaMatriz = true;
            } else if(proximaPosicaoAviao < 0) {
                proximaPosicaoAviao = SimulacaoEsquadrilha.TAMANHO_MATRIZ_CEU + proximaPosicaoAviao;
                aviaoVoltaMatriz = true;
            }
            
            if(proximaPosicaoPombo >= SimulacaoEsquadrilha.TAMANHO_MATRIZ_CEU) {
                proximaPosicaoPombo -= SimulacaoEsquadrilha.TAMANHO_MATRIZ_CEU;
                pomboVoltaMatriz = true;
            } else if(proximaPosicaoPombo < 0) {
                proximaPosicaoPombo = SimulacaoEsquadrilha.TAMANHO_MATRIZ_CEU + proximaPosicaoPombo;
                pomboVoltaMatriz = true;
            }
            
            //se a próxima posição do avião será menor que a próxima posição do pombo e nenhum dos dois contornou a matriz, ele segue uma nova direção ou continuar em sua direção atual
            if(proximaPosicaoAviao < proximaPosicaoPombo && !aviaoVoltaMatriz && !pomboVoltaMatriz) {
                if(direcaoAviao == 'N' || direcaoAviao == 'S') {      
                    if(probabilidade >= 1 && probabilidade <= 45) //45% para leste
                        voar('L');
                    else if(probabilidade >= 46 && probabilidade <= 90) //45% para oeste
                        voar('O');
                    else //10% para seguir sua mesma direção (direção avião == direção pombo)
                        voar(getDirecaoFrente());
                    
                } else {
                    if(probabilidade >= 1 && probabilidade <= 45) //45% para norte
                        voar('N');
                    else if(probabilidade >= 46 && probabilidade <= 90) //45% para sul
                        voar('S');
                    else //10% para seguir sua mesma direção (direção avião == direção pombo)
                        voar(getDirecaoFrente());
                }
            } else{ //todos os outros casos fazem o pombo voar para uma direção diferente da do avião
                if(direcaoAviao == 'N' || direcaoAviao == 'S') { 
                    //50% para as duas direções
                    if(probabilidade >= 1 && probabilidade <= 50)
                        voar('L');
                    else
                        voar('O');
                    
                } else {
                    //50% para as duas direções
                    if(probabilidade >= 1 && probabilidade <= 50)
                        voar('N');
                    else
                        voar('S');
                }
            }
        } else { //se as direções atuais dos dois voadores forem diferentes, ele segue suas táticas de voo
            do {
                novaDirecao = definirTatica();
            }while(novaDirecao == direcaoAviao);
            voar(novaDirecao);
        }
    }

    @Override
    public Boolean check() {
        if(getContadorTempoChecagem() == getTempoChecagem()) {
            setContadorTempoChecagem(0);
            return true;
        }
        return false;
    }
}
    
