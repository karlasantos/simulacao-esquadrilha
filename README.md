# simulacao-esquadrilha
Trabalho realizado durante curso de graduação em 14 de janeiro de 2018 para exemmplificar os modelos de simulação:
* Estocástico: pela direção (Norte, Sul, Leste e Oeste) dos personagens definidas através de valores aleatórios com probabilidade definida.
* Dinâmico: considera o tempo e cada variação atrelada a um evento;
* Discreto: com eventos de mudança de voo ocorrendo a cada segundo.

## Fluxograma

## Informações Sobre o Código
###Critérios utilizados na representação:
* O campo de voo é representado por uma matriz 10x10
* Posições da matriz: cada posição representa 10m
* Velocidades: a cada segundo da simulação o Pombo Correio pode voar 3 casas na matriz (30m/s) enquanto o Avião da Esquadrilha Abutre pode voar 9 (90m/s)
* Direções de voo: as escolhas de direções de voo são dadas através de números aleatórios, e cada direção possui uma porcentagem x de ocorrência
* Se um determinado personagem chegar na borda da matriz e não tiver realizado todo o voo uma volta na matriz será realizada

###Visualização em formato de linhas: visualização descritiva da posição e direção de cada personagem;

### Visualização em formato de matriz: visualização dos personagens em suas posições com suas respectivas direções 
* P = Pombo Correio e A = Avião Esquadrilha Abutre
* Direções atuais: N(^), S(v), L(>), O(<)
* Exemplo: 'P>' = Pombo Correio indo para direção Leste
