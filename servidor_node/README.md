**Serviço de api Back End**

A aplicação utiliza uma estrutura baseada em APIs RESTful, com comunicação por meio do protocolo HTTP. A autenticação será realizada com JWT e os métodos POST e GET, desenvolvidos em Node.js.

A aplicação contará com as seguintes dependências e configurações:

![image](https://github.com/user-attachments/assets/36f28ec2-d317-4bc6-ab1a-c4f841df6043)

Além disso, será utilizado o serviço do Docker Compose para gerenciar a interação com o banco de dados PostgreSQL, permitindo adicionar e retornar dados entre as interfaces de maneira eficiente.

----
***Regra de Negócio***

Quando um administrador ou agente fizer login na aplicação, será chamada uma API POST de autenticação. Essa API verificará os dados de acesso fornecidos pelo usuário e, em seguida, gerará um token de autenticação. Esse token será necessário para que administradores e agentes possam acessar outras APIs, incluindo aquelas que utilizam métodos POST e requerem autorização (auth).

Ademais as aplicações também utilizar os metodos multer para realizar a transferencia de arquivos para adicionar imagens


----
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
