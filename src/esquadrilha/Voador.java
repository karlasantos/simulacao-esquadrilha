/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esquadrilha;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Contém todos os dados em comum entre os voadores (avião e pombo)
 * @author Karla
 */
public abstract class Voador {
    /**
     * Posição atual no céu (matriz)
     */
    private Posicao posicaoAtual;
    
    /**
     * Controle de tempo de checagem de posição do oponente
     */
    private Integer contadorTempoChecagem;
    
    /**
     * Valor de tempo de checagem de posição do oponente
     */
    private Integer tempoChecagem;
    
    /**
     * Quantidade de casas da matriz a serem incrementadas em cada segundo de tempo
     */
    private Integer velocidade;
    
    /**
     *  Armazena qual o sentido de direção [N(norte), S(sul), L(leste), O(oeste)]
     */
    private char direcaoFrente;

    public Voador(Posicao posicaoAtual, char direcaoFrente, Integer tempoChecagem, Integer velocidade) {
        this.posicaoAtual = posicaoAtual;
        this.contadorTempoChecagem = 0;
        this.tempoChecagem = tempoChecagem;
        this.direcaoFrente = direcaoFrente;
        this.velocidade = velocidade;
    }
        
    /**
     * Checagem de tempo de visualização de posição do oponente
     * @return Boolean
     */
    public abstract Boolean check();
    
    /**
     * Verificar se a nova direção a ser seguida é oposta da direção atual
     * @param novaDirecao char
     * @return Boolean
     */
    protected Boolean isOposta(char novaDirecao) {
        return (getDirecaoFrente() == 'N' && novaDirecao == 'S' || getDirecaoFrente() == 'S' && novaDirecao == 'N' || getDirecaoFrente() == 'L' && novaDirecao == 'O' || getDirecaoFrente() == 'O' && novaDirecao == 'L');
    }
    
    /**
     * Executar ação de voo
     * @param novaDirecao char
     */
    public void voar(char novaDirecao) {
        //cópia de velocidade para realizar modificações sem alterar o valor real 
        Integer velocidadeCopy = velocidade; 
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
    
    /**
     * Controle de borda máxima de matriz
     * @param novaPosicao Integer
     * @return 
     */
    protected Integer controleVoltaMatrizMaximo(Integer novaPosicao) {
        if(novaPosicao < SimulacaoEsquadrilha.TAMANHO_MATRIZ_CEU)
            return novaPosicao;
        return (novaPosicao - SimulacaoEsquadrilha.TAMANHO_MATRIZ_CEU);
    }
    
    /**
     * Controle de borda mínima de matriz
     * @param novaPosicao Integer
     * @return 
     */
    protected Integer controleVoltaMatrizMinimo(Integer novaPosicao){
        if(novaPosicao >= 0)
            return novaPosicao;
        return (SimulacaoEsquadrilha.TAMANHO_MATRIZ_CEU + novaPosicao);
    }

    public char getDirecaoFrente() {
        return direcaoFrente;
    }

    public void setDirecaoFrente(char direcaoFrente) {
        this.direcaoFrente = direcaoFrente;
    }
    
    public Posicao getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(Posicao posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public Integer getContadorTempoChecagem() {
        return contadorTempoChecagem;
    }
    
    public void setContadorTempoChecagem(Integer contadorTempoChecagem) {
        this.contadorTempoChecagem = contadorTempoChecagem;
    }

    public Integer getTempoChecagem() {
        return tempoChecagem;
    }

    public void setTempoChecagem(Integer tempoChecagem) {
        this.tempoChecagem = tempoChecagem;
    }

    public Integer getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(Integer velocidade) {
        this.velocidade = velocidade;
    }
}
