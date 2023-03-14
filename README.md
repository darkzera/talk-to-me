Construcao de multiplos servicos utilizando Spring cloud.

Vamos nos basear no repositorio **indicado** e construir cada um baseado na arquitetura abaixo

1) RLS

- [ ]  Idealizar produto
- [ ]  Definir e criar o servico CORE e DEPOIS criar arquitetura ao redor
- [ ]  Testar integracoes a servicos externos pode nao ser tao simples
- [ ]  Reconstruir arquitetura das API REST(vamos tentar utilizar clean arch, vai ser chato. Talvez precise de alguma POC antes
- [ ]  Gateway
- [ ]  Autenticacao
- [ ]  Naming-server

## Application CORE

## FETCHER SERVICE

![Untitled](https://iili.io/HXkFCTg.png)

## SCHEDULER SERVICE

![Untitled](https://iili.io/HXkGIVa.png)

---

- Comunicacao - assincrona
    
    Treinar assertividade das mensagens, plataformas e ate quem sabe estabelecer algum tipo de TAG para issues. Utilizar nomeclaturas de branch pode ajudar tbem. 
    
    **obs: a entrada de mic da minha placa nao tá funcionando. entao, thats it**
    
- Fancy vs KISS
    
    Como vamos criar servicos diferentes, em cada um deles podemos seguir um padrao de implementacao, e assim, podemos avaliar com mais clareza os tradeoffs de cada escolha 
    
    Em cada um, podemos ou nao utilizar abordagens como:
    
    - Clean arch
    - Exceptions para lidar com o fluxo (controller adivce, @ResponseException) etc
    - add..
    
    
**O que nao é negociavel - (podemos usar como DEFINITION OF DONE)**
    
- Testes (unitarios principalmente)
-  Respeitar minimamente as normas REST 
-  Codigo assertivo: nomes de metodos respeitando convencoes, semantica e sintaxe.
- Abstrair quando necessario
- Comentarios inuteis
 - Imports com * ou nao utilizados
 - Codigo quebrado **APENAS EM BRANCHES AUXILIARES DE DESENVOLVIMENTO**
