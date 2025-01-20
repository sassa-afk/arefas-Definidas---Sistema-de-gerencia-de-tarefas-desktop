**Serviço de api Back End**

Os processos passados abaixo fora desenvolvidos no S.I UbuntoLinux .

A aplicação utiliza uma estrutura baseada em APIs RESTful, com comunicação por meio do protocolo HTTP. A autenticação será realizada com JWT e os métodos POST e GET, desenvolvidos em Node.js.

A aplicação contará com as seguintes dependências e configurações:

![image](https://github.com/user-attachments/assets/36f28ec2-d317-4bc6-ab1a-c4f841df6043)

Além disso, será utilizado o serviço do Docker Compose para gerenciar a interação com o banco de dados PostgreSQL, permitindo adicionar e retornar dados entre as interfaces de maneira eficiente.

----
***Regra de Negócio***

Quando um administrador ou agente fizer login na aplicação, será chamada uma API POST de autenticação. Essa API verificará os dados de acesso fornecidos pelo usuário e, em seguida, gerará um token de autenticação. Esse token será necessário para que administradores e agentes possam acessar outras APIs, incluindo aquelas que utilizam métodos POST e requerem autorização (auth).

Ademais as aplicações também utilizar os metodos multer para realizar a transferencia de arquivos para adicionar imagens

O ambiente node será composto por 4 arquivos 

- db.js (Composto por dados de conexão do dba postgreas e modulo de conexao )
- docker-compose.yml ( Configuração dos contêineres Docker e serviços atraves da extenção YML  , apos sua configuração é nescessario executar o serviço / docker-compose up -d  )
- server.js ( Codigos das apis e serviços utilizados para chamada dos metodos GET e POST  )

  **OBS :** o arquivo server.js tem o apontamento ao ip 192.168.0.114 que é o ip local da minha maquina conecatado a minha rede sem fio  para que o serviço seja acessivo por outros dispositivos e concluir os teste.
  mas ideia principal é esconder este endereço no dotenv@16.4.5 dentro do arquivo .env ocuto
- .env (Arquivo onde fica escondido a palavra de segurança utilizado para assinatura digiral do tokem )
  
![image](https://github.com/user-attachments/assets/39fed4b2-3679-4132-b730-dc641cfa3c38)

----
**Instalar dos serviços**

- **Instalar Docker Compose:**
 
 sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

 sudo chmod +x /usr/local/bin/docker-compose

 docker-compose --version


- **Instação do serviço npm e inicialização do node**

 npm install 

 npm init -y

- **Instação das depedencias**

 npm install cors <br>

 npm install dotenv <br>

 npm install express <br>

 npm install http <br>

 npm install jsonwebtoken <br>

 npm install multer <br>

 npm install path <br>

 npm install pg <br>

 npm install pool <br>

 npm install socket.io <br>

- **Ativar o serviço**

Apos construir o codigo , completo, inicie o serviço com node server
  
----

**Resumo e motivo do uso das Dependências**
 - cors: Habilita o uso de CORS (Cross-Origin Resource Sharing).
 - dotenv: Gerencia variáveis de ambiente em arquivos .env.
 - express: Framework web minimalista para Node.js.
 - http: Permite criar servidores HTTP.
 - jsonwebtoken: Gera e valida tokens JWT para autenticação.
 - multer: Lida com uploads de arquivos.
 - path: Gerencia caminhos de arquivos e diretórios.
 - pg: Cliente PostgreSQL para Node.js.

----

- **Melhorias futuras que serão aplicadas**
  
  - 1 - Aplicar paradigmar em orientação objeto na construções das APIs 
    + separar as apis em 3 classe , Agente/ADM/FUNCAOGERAL/AUDITORIA

  - 2 - Aplicar serviço de mensageria por email atraves do Nodemailer para possibilitar envio de credenciais de email e detalhes para os emais dos agentes

  - 3 - Melhorar auditoria de ações de conexão e solicitações dos serviços da api

  - 4 - Aplicar conexão o serviço de socket IO para monitoramento de acessos dos usuários e novas notificações principalmente de tarefas, principalmente as urgêntes 
  
  ----



