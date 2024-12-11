# Dockerized Java Courses API

Este repositório é a solução para o primeiro desafio prático da trilha de 
DevOps da rocketset, na qual "dockerizo" uma aplicação para desenvolvimento local com docker-compose.

## Configurações

Para configurar a aplicação, basta criar um arquivo .env na raiz do projeto 
com base no arquivo `.env.sample`. Neste arquivo, serão configuradas algumas 
variáveis de ambiente como usuário e senha para acesso ao banco de dados, 
evitando assim a utilização do usuário root.

### Sobre as configurações

A aplicação está configurada para ser executada localmente com 
docker-compose, sendo 2 containers: 1 para a aplicação e 1 para o banco dados.

O container da aplicação possui uma dependência do container do banco, logo 
apenas irá ser iniciado uma vez que o container do banco for considerado 
_healthy_ através do health test.

#### Redes

Ambos os continers estão em uma rede compartilhada `corporate-network`, 
permitindo que ao configurar a conexão com o banco de dados seja utilizado o 
nome do container como referência.

Para verificar que ambos os containers estão associados a rede, execute 
`docker network ls`, localize a rede cujo final do nome é 
**corporate-network**, e inspecione-a utilizando a NETWORK ID: `docker 
inspect {NETWORK_ID}`

##### Verificando a comunicação entre os containers

Para assegurar que a aplicação está conectada ao banco de dados, inicie os 
containers co `docker compose up` e observe os logs, em casos de falha 
haverá uma mensagem de erro sobre a coneção com o banco de dados. Do 
contrário, a aplicação terá iniciado corretamente e estará disponível para 
receber requisições em `0.0.0.0:8080`.

#### Volumes

O container `postgres` possui um volume `db-data` atrelado ao mesmo, 
permitindo assim que dados utilizados durante o desenvolvimento sejam 
persistidos após a remoção do container.

Para verificar que o container está de fato atrelado ao volume, execute 
`docker ps` para obter a ID do container **courses-db** e execute `docker 
inspect {CONTAINER_ID}`, o volume deve estar listado no objeto **Mounts**.

Para maiores detalhes sobre o volume, localize-o através do comando `docker 
volume ls` e inspecione-o com `docker inspect {VOLUME_ID}`.

#### Dockerfile(s)

A configuração atual conta com um dockerfile dedicado para desenvolvimento 
local `dev.dockerfile`, cuja única diferença deste para o dockerfile oficial 
(a ser utilizado em produção) é a utilização do .env de configuração local. 
Para produção, um .env poderia ser configurado na pipeline de deploy.

Ambos os dockerfiles estão configurados como multi-staged, onde a primeira 
etapa é a de _build_ e a segunda etapa é a construção da imagem final, a 
qual contém apenas 1 arquivo `.jar`. Por ser uma aplicação simples, a imagem 
é relativamente leve (aproximadamente 258 Mb).

Ambas as imagens são versões otimizadas (apline) que contém apenas as 
ferramentas necessárias para a construção das imagens e, na data de hoje 
(vide commit), não possuem vulnerabilidades.

As imagens podem ser conferidas no docker hub através dos seguintes links:

[Maven image](https://hub.docker.com/layers/library/maven/3.9.9-eclipse-temurin-21-alpine/images/sha256-dcee7bb2ea4976ead9a95662700dc786e1528ee2215d1e7967724cd0fee4cacc?context=explore).

[JRE image](https://hub.docker.com/layers/library/eclipse-temurin/21.0.5_11-jre-alpine/images/sha256-008d50b2730a72e475bfdfe651149d68180dc2689b808afb8b0bc669b3262a59?context=explore).

## Considerações finais

Esta foi a primeira vez dockerizando uma aplicação Java, não tenho certeza 
se as imagens base são as mais indicadas porém, possuem o mínimo de 
requisitos como baixa ou nenhuma vulnerabilidade e otimizadas para redução 
do tamanho final da imagem sem compreter a funcionalidade.

Aceito sugestões e feedbacks sobre o projeto (no que se refere à 
configuração), a API é simples e foi criada apenas para praticar a linguagem 
Java.
