/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esquadrilha;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Representação do avião
 * @author Karla
 */
public class Aviao extends Voador {

    public Aviao(Integer linha, Integer coluna, char direcaoFrente, Integer tempoChecagem, Integer velocidade) {
        super(new Posicao(linha, coluna), direcaoFrente, tempoChecagem, velocidade);
    }
   
    @Override
    public Boolean check() {
        if (getContadorTempoChecagem() == getTempoChecagem()) {
            setContadorTempoChecagem(0);
            return true;
        }
        return false;
    }
    
    /**
     * Voar em direção ao Pombo, método utilizado para quando o mesmo for avistado
     * @param posicaoPombo Posicao
     * @param direcaoPombo char
     */
    public void voarAtrasPombo(Posicao posicaoPombo, char direcaoPombo) {
        System.out.println("AVIÃO PERSEGUINDO!\n");

        //verifica a localização pela diferença entre os valores das posições em linhas e colunas
        Integer diferencaPosicoesLinha = getPosicaoAtual().getLinha() - posicaoPombo.getLinha() ;
        Integer diferencaPosicoesColuna = getPosicaoAtual().getColuna() - posicaoPombo.getColuna();
        
        //para saber onde dobrar, caso o pombo esteja em uma linha diferente da do avião
        Integer sobraMetrosUtilizados = 0;
        
        //se a diferença entre as linhas das posições for menor que zero, o avião está acima do pombo, então deve ir para o sul
        if(diferencaPosicoesLinha  < 0) {
            //se a diferença for menor que sua velocidade(quantas casas da matriz pode voar em cada segundo) ele voa toda a diferença para sul (chegando na linha do pombo) e armazena o que sobrou para fazer uma curva
            if((diferencaPosicoesLinha*-1) < getVelocidade()) {
                voar('S', diferencaPosicoesLinha*-1);
                sobraMetrosUtilizados = getVelocidade() + diferencaPosicoesLinha;
            } else { //se a diferença for maior ou igual a velocidade, voa com toda a sua velocidade disponível em direção a linha do pombo
                voar('S');
            }
            //se a diferença for maior que zero, o avisão está abaixo do pombo, então deve ir em direção norte
        } else if(diferencaPosicoesLinha > 0) { 
            //se a diferença for menor que a velocidade, anda toda a diferença para chegar até o pombo e armazena o que sobrou para fazer uma curva
            if(diferencaPosicoesLinha < getVelocidade()) {
                voar('N', diferencaPosicoesLinha);
                sobraMetrosUtilizados = getVelocidade() - diferencaPosicoesLinha;
            } else { //se a diferença for maior ou igual a velocidade, voa com toda a velocidade em direção ao pombo
                voar('N');
            }
        }
        //verifica se o avião teve que voar para alcançar o pombo de acordo com as linhas, através da sobra de metros percorridos
        if(sobraMetrosUtilizados != 0) {
            if(diferencaPosicoesColuna < 0) //voa para leste se a diferença entre as colunas for menor que zero
                voar('L', sobraMetrosUtilizados);
            else if(diferencaPosicoesColuna > 0) //se for maior voa para oeste
                voar('O', sobraMetrosUtilizados);
        } else {
            if(diferencaPosicoesColuna < 0)
                voar('L');
            else if((diferencaPosicoesColuna > 0))
                voar('O');
        }
    }
    
    /**
     * Voar em direção ao Pombo, método utilizado para quando o mesmo for avistado
     * @param posicaoPombo Posicao
     * @param direcaoPombo char
     */
    public Boolean pegarPombo(Posicao posicaoPombo, char direcaoPombo) {
        //verifica a localização pela diferença entre os valores das posições em linhas e colunas
        Integer diferencaPosicoesLinha = getPosicaoAtual().getLinha() - posicaoPombo.getLinha() ;
        Integer diferencaPosicoesColuna = getPosicaoAtual().getColuna() - posicaoPombo.getColuna();
        
        //para saber onde dobrar, caso o pombo esteja em uma linha diferente da do avião
        Integer sobraMetrosUtilizados = 0;
        
        //se a diferença entre as linhas das posições for menor que zero, o avião está acima do pombo, então deve ir para o sul
        if(diferencaPosicoesLinha  < 0) {
            //se a diferença for menor que sua velocidade(quantas casas da matriz pode voar em cada segundo) ele voa toda a diferença para sul (chegando na linha do pombo) e armazena o que sobrou para fazer uma curva
            if((diferencaPosicoesLinha*-1) < getVelocidade()) {
                voar('S', diferencaPosicoesLinha*-1);
                sobraMetrosUtilizados = getVelocidade() + diferencaPosicoesLinha;
            } else { //se a diferença for maior ou igual a velocidade, voa com toda a sua velocidade disponível em direção a linha do pombo
                voar('S');
            }
            //se a diferença for maior que zero, o avisão está abaixo do pombo, então deve ir em direção norte
        } else if(diferencaPosicoesLinha > 0) { 
            //se a diferença for menor que a velocidade, anda toda a diferença para chegar até o pombo e armazena o que sobrou para fazer uma curva
            if(diferencaPosicoesLinha < getVelocidade()) {
                voar('N', diferencaPosicoesLinha);
                sobraMetrosUtilizados = getVelocidade() - diferencaPosicoesLinha;
            } else { //se a diferença for maior ou igual a velocidade, voa com toda a velocidade em direção ao pombo
                voar('N');
            }
        }
        
        if(posicaoPombo.getLinha() == getPosicaoAtual().getLinha() && posicaoPombo.getColuna() == getPosicaoAtual().getColuna())
                return true;
        
        //verifica se o avião teve que voar para alcançar o pombo de acordo com as linhas, através da sobra de metros percorridos
        if(sobraMetrosUtilizados != 0) {
            if(diferencaPosicoesColuna < 0) //voa para leste se a diferença entre as colunas for menor que zero
                voar('L', sobraMetrosUtilizados);
            else if(diferencaPosicoesColuna > 0) //se for maior voa para oeste
                voar('O', sobraMetrosUtilizados);
        } else {
            if(diferencaPosicoesColuna < 0)
                voar('L');
            else if((diferencaPosicoesColuna > 0))
                voar('O');
        }
        if(posicaoPombo.getLinha() == getPosicaoAtual().getLinha() && posicaoPombo.getColuna() == getPosicaoAtual().getColuna())
                return true;
        return false;
    }
    
    /**
     * Executar ação de voo com um valor máximo de metros a ser percorrido
     * @param novaDirecao char
     * @param qtdMetros Integer
     */
    public void voar(char novaDirecao, Integer qtdMetros) {
        //cópia de qtdMetros (parte da velocidade) para realizar modificações sem alterar o valor real 
        Integer velocidadeCopy = qtdMetros; 
        Integer novaPosicao;
        
        if(isOposta(novaDirecao)) {
            int probabilidade = ThreadLocalRandom.current().nextInt(1, 100);
            velocidadeCopy--;
            
            if(probabilidade >= 1 && probabilidade <=50) {
                if(getDirecaoFrente() == 'N' || getDirecaoFrente() == 'S') { //se estiver indo para sul ou norte, define direção da curva para leste
                    novaPosicao = getPosicaoAtual().getColuna()+1;
                        getPosicaoAtual().setColuna(controleVoltaMatrizMaximo(novaPosicao));
                } else { //e se estiver indo para leste ou oeste, define a direção da curva para norte
                    novaPosicao = getPosicaoAtual().getLinha()-1;
                    getPosicaoAtual().setLinha(controleVoltaMatrizMinimo(novaPosicao));                }
            } else {
                if(getDirecaoFrente() == 'N' || getDirecaoFrente() == 'S') { //se estiver indo para sul ou norte, define direção da curva para oeste
                    novaPosicao = getPosicaoAtual().getColuna()-1;
                    getPosicaoAtual().setColuna(controleVoltaMatrizMinimo(novaPosicao));
                } else {//e se estiver indo para leste ou oeste, define a direção da curva para sul
                    novaPosicao = getPosicaoAtual().getLinha()+1;
                    getPosicaoAtual().setLinha(controleVoltaMatrizMaximo(novaPosicao));
                }
            }
        }
            
        switch(novaDirecao) {
            case 'N':
                novaPosicao = getPosicaoAtual().getLinha()-velocidadeCopy;
                getPosicaoAtual().setLinha(controleVoltaMatrizMinimo(novaPosicao));                
                setDirecaoFrente('N');
                break;
            case 'S':
                novaPosicao = getPosicaoAtual().getLinha()+velocidadeCopy;
                getPosicaoAtual().setLinha(controleVoltaMatrizMaximo(novaPosicao));                
                setDirecaoFrente('S');
                break;
            case 'L':
                novaPosicao = getPosicaoAtual().getColuna()+velocidadeCopy;
                getPosicaoAtual().setColuna(controleVoltaMatrizMaximo(novaPosicao));
                setDirecaoFrente('L');
                break;
            default:
                novaPosicao = getPosicaoAtual().getColuna()-velocidadeCopy;
                getPosicaoAtual().setColuna(controleVoltaMatrizMinimo(novaPosicao));
                setDirecaoFrente('O');
                break;
        }
    }
}
