# simulacao-esquadrilha
Trabalho em realizado durante curso de graduação em *14 de janeiro de 2018*, desenvolvido em **Java**:

* Exemplificação dos modelos de simulação
* Questão do livro **_Modelagem e simulação de eventos discretos: Teoria e Aplicações, de  Leonardo Chwif e Afonso Medina_**:

_A esquadrilha Abutre está perseguindo o pombo-correio Doodle. O avião projetado por Klunk está em perseguição ao pombo utilizando um radar capaz de identificar sua localização a cada cinco segundos. Assim que o pombo é localizado, o avião muda a sua trajetória em direção a ele. O pombo Doodle é capaz de realizar manobras evasivas a qualquer instante; contudo, só pode olhar a posição do avião a cada 4 segundos. Na primeira identificação do radar, o pombo está a 100m do avião, no mesmo plano e olhando para a frente do avião. Considere que a velocidade do avião é igual a 90m/s e a do pombo é igual a 30m/s._

*a) Qual o tipo de simulação mais adequada?*

*b) Construa um Fluxograma representando a simulação do sistema. (Atribua as condições iniciais que achar necessárias).*

*c) Construa, a partir do Fluxograma obtido no item anterior, um programa de computador para representar o sistema. Elabore e teste algumas táticas evasivas diferentes para o pombo. Qual foi a melhor tática?*

## Detalhamento da simulação

* Estocástico: pela direção (Norte, Sul, Leste e Oeste) dos personagens definidas através de valores aleatórios com probabilidade definida.
* Dinâmico: considera o tempo e cada variação atrelada a um evento;
* Discreto: com eventos de mudança de voo ocorrendo a cada segundo.

## Fluxograma

![Fluxograma](https://github.com/karlasantos/simulacao-esquadrilha/blob/master/fluxograma.jpg)

## Informações Sobre o Código
### Critérios utilizados na representação:
* O **campo de voo** é representado por uma **matriz 10x10**
* **Posições da matriz**: **cada posição** representa **10m**
* **Velocidades**: a cada segundo da simulação o **Pombo Correio** pode voar **3 casas** na matriz **(30m/s)** enquanto o **Avião da Esquadrilha Abutre** pode voar **9 (90m/s)**
* **Direções de voo**: as escolhas de direções de voo são dadas através de números aleatórios, e cada direção possui uma porcentagem x de ocorrência
* Se um determinado personagem chegar na **borda da matriz** e não tiver realizado todo o voo **uma volta** na matriz será realizada

### Visualização em formato de linhas
Visualização descritiva da posição e direção de cada personagem;

### Visualização em formato de matriz
Visualização dos personagens em suas posições com suas respectivas direções 
* P = Pombo Correio e A = Avião Esquadrilha Abutre
* Direções atuais: **Norte(^)**, **Sul(v)**, **Leste(>)**, **Oeste(<)**

Exemplo: **'P>'** = Pombo Correio indo para direção Leste
