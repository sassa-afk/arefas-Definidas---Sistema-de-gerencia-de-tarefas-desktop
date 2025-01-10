👋 Olá , eu sou o Samuel Souto dos Santos / @sassa-afk 👀 ..
- Tenho interesse na área de desenvolvimento de software 🌱
- Atualmente estudo e criando projetos universitários em linguagens de programação introduzida em meu curso S.I, no qual me encontro no quinto período.
- 📫 Você consegue chegar até mim através do email samuelsouto21@gmail.com .

# Tarefas-Definidas - Sistema de gerencia de tarefas

 Tarefas Definidas é um sistema simples e eficiente para o gerenciamento de demandas e atividades entre dois tipos de usuários: administradores (adms) e agentes. O objetivo principal da aplicação é facilitar a definição, distribuição e execução de tarefas no dia a dia.

- Administradores (adms): são responsáveis pela criação, organização e atribuição de tarefas aos agentes, além de gerenciar os usuários do sistema. Seu acesso ocorre por meio de uma plataforma desktop, equipada com ferramentas completas e intuitivas para facilitar a gestão.
As principais funcionalidades disponíveis para os administradores incluem:
  - Gerenciamento de Usuários: criar novos usuários (administradores e agentes), editar dados cadastrais e redefinir senhas.
  - Criação de Tarefas: atribuir tarefas aos agentes com critérios claros e prioridades definidas para a execução, garantindo maior eficiência no fluxo de trabalho.

- Agentes: São responsáveis por receber as tarefas designadas pelos administradores, respondê-las e executá-las. Eles também podem acompanhar o andamento das tarefas e seus indicadores, adicionar observações durante a execução e finalizar os processos.
Os agentes terão acesso à plataforma móvel desenvolvida para dispositivos Android. Nela, poderão:
  - Alterar e editar informações de contato e a foto de perfil.
  - Acompanhar suas tarefas e registrar respostas.
  - Consultar informações sobre seus indicadores, como o número de tarefas em aberto, finalizadas, em atendimento, entre outros.

A aplicação foi desenvolvida nos ambientes de desenvolvimento IntelliJ IDEA e Visual Studio, utilizando Java para a interface das plataformas desktop e mobile.
O back-end foi implementado em Node.js, responsável pela construção de APIs (GET e POST), utilizando o token JWT para autenticação, garantindo um nível básico de segurança no acesso às APIs.
O armazenamento dos dados gerados pelos usuários foi realizado no banco de dados PostgreSQL.

---
**Breve preview**

- Painel de adm :<br>

**Painel de login**
  ![of11](https://github.com/user-attachments/assets/af29be18-e477-423f-9edf-69a8e4d95172)

**Painel de perfil**
  ![Apex_22](https://github.com/user-attachments/assets/594ad26d-e786-40be-9a13-05f91f92f77c)

**Painel de acesso**
  ![33](https://github.com/user-attachments/assets/888304f1-9060-402d-8c23-65520e746a40)
**Painel de tarefas**
  ![Apex_44](https://github.com/user-attachments/assets/dd6b6c0e-607f-4951-9af0-f2ac39d73f19)

- Aplicação agente <br>
 
https://github.com/user-attachments/assets/bab28502-9f1d-46f2-9686-2c614993dcfc

https://github.com/user-attachments/assets/2f40b7ff-7619-4975-b3e2-11c0c892d27a

https://github.com/user-attachments/assets/ac98cf67-552c-493c-b6a2-03e8a249d500

https://github.com/user-attachments/assets/243236bc-d96a-466c-a9cc-fe24652e0234





---

**Tecnologia Utilizada**

- Conteiner Docker para subir imagens do node js e dba postgrea 
- Imagem Server Node.js: Configurada para hospedar o backend da aplicação.
- Imagem DBA PostgreSQL: Configurada para armazenar e gerenciar os dados da aplicação.
*Dependências so servidor Web Node.js*
- pool: Gerencia as conexões com o banco de dados PostgreSQL.
- express: Framework para criar e gerenciar rotas e APIs RESTful.
- http: Biblioteca nativa usada para criar e gerenciar servidores HTTP.
- cors: Permite controlar o compartilhamento de recursos entre origens diferentes.
- jwt: Implementa autenticação com JSON Web Tokens para maior segurança.
- path: Gerencia caminhos de arquivos e diretórios.
- dotenv : Para configuração de variáveis de ambiente de forma segura.
*Java**
- BIblioteca javax swing e awt  : Para construção da plataforma desktop
- Biblioteca  org.json e java.net : Receber e enviar dados da api e converter dados json
- Biblioteca java androidx ,java android e XML : Para construção da aplicação mobile

---
<br>

**Diagrama dba**
 
![image](https://github.com/user-attachments/assets/ba20dbb1-473e-40f7-b91d-cf9576e85219)

 ---
 
**Diagrama de Relacionamento dba**

![image](https://github.com/user-attachments/assets/1cf9901f-9d51-4c8b-8bf3-495286aa1ebc)

---


**Diagrama de caso de uso**


![image](https://github.com/user-attachments/assets/03ccbf71-4dc6-4ee2-99c5-b9ced4c9d992)

> https://viewer.diagrams.net/index.html?tags=%7B%7D&lightbox=1&target=blank&highlight=00003B&edit=_blank&layers=1&nav=1#G1mJipDggwUCi1kfUWMAOTMFpVqiVJMflz#%7B%22pageId%22%3A%22Vmw0A1_i2rNyL6fyl7KQ%22%7D

> OBS :
> Aplicação será composta por 2 usuaários um de adm (gerenciador) e outro agente(executador das tarefas destinada ao mesmo).  

 ---
**Codigos SQL utilizados**

CREATE TABLE acessos (
    user_id INT PRIMARY KEY,
    nome_usuario VARCHAR(100) NOT NULL UNIQUE, 
    senha VARCHAR(100) NOT NULL,
    usuario_id_fk INT,
    ativo BOOLEAN NOT NULL,
    tipo_acesso VARCHAR(50) NOT NULL,
    FOREIGN KEY (usuario_id_fk) REFERENCES usuarios(id)
);
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,                 
    nome VARCHAR(100) NOT NULL,           
    email VARCHAR(100) NOT NULL,           
    data_criacao  DATE DEFAULT CURRENT_DATE,      
    telefone VARCHAR(15),                  
    cargo VARCHAR(50),                    
    sobrenome VARCHAR(100),               
    foto VARCHAR(255)                    
);
CREATE TABLE tarefas (
    taref_id BIGSERIAL PRIMARY KEY,   
    
    id_user_destino_tarefa_fk INT NOT NULL, 
    id_user_criado_tarefa_fk INT NOT NULL, 
    
    status VARCHAR(100) NOT NULL,  
    titulo VARCHAR(300) NOT NULL,  
    
    data_tarefa_criada DATE DEFAULT CURRENT_DATE,  
    tempo_estimado_fim_tarefa TIMESTAMP NOT NULL,  -- '2024-12-31 23:59:59' padrão de inserção
    
    FOREIGN KEY (id_user_destino_tarefa_fk) REFERENCES acessos(user_id),  
    FOREIGN KEY (id_user_criado_tarefa_fk) REFERENCES acessos(user_id)    
);
CREATE TABLE tempo_tarefas (
    taref_id BIGSERIAL PRIMARY KEY,
    
    id_tarefa_fk BIGINT NOT NULL,   
    id_user_interacao_fk INT NOT NULL,
    
    tempo_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   
    status VARCHAR(100) NOT NULL,
    
    FOREIGN KEY (id_tarefa_fk) REFERENCES tarefas(taref_id),   
    FOREIGN KEY (id_user_interacao_fk) REFERENCES acessos(user_id)
);
CREATE TABLE comentarios (
    id BIGSERIAL PRIMARY KEY,   
    taref_id_fk BIGINT NOT NULL,  
    id_user_comentado INT NOT NULL,  
    comentario TEXT NOT NULL, 
    
    data_comentario TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   
    FOREIGN KEY (taref_id_fk) REFERENCES tarefas(taref_id),  
    FOREIGN KEY (id_user_comentado) REFERENCES acessos(user_id)  
);

---
***Codigo e funções Node js / ROTAS APIS*** 

Até o momento foram criado dados de verificação e funcionalidades refrente a comunicação de apis do servidor ao painel adm 
>**function (req, res, next)**
>- funçao js de autenticação para validar token os nas apis GET e POST 
>**post /auth**
>> - Usado para autenticar acessos adms atraves do painel desktop
>>  - O confirmando usuario e senha e retornando um token de acesso gerado apos validação das crenciais. 
>> BOdy {<br>
>>    "nome_usuario": "adm",<br>
>>   "senha": "adm"<br>
>> }<br>
Retorno :  
>> {<br>
>  "auth": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c3Vhcmlv",<br>
>>    "user_id": 46,<br>
>>   "ativo": true<br>
>>  }<br> 
>**get /dadosUser**
>> - Usado para retornar dados do agente autenticado no painel
>> - O mesmo é aplicado para identificar quem está logado principalmente meu perfil
>> Passagem de parametro : http:link_api/dadosuser/id: http://LINK_API/dadosUser?id=46
>> Header : x-access-token : token_gerado_apos_autenticar
Retorno :
>>{
>> "mesage": [{
>>"id": 46,<br>
>>"nome": "Samuel",<br>
>>"email": "samuelsouto21@gmail.com",<br>
>>"data_criacao": "2024-11-11T13:11:00.422Z",<br>
>>"telefone": "34998424800",<br>
>>"cargo": "Desenvolvedor",<br>
>>"sobrenome": "Solto"<br>
>>}
>>]}
>
>**GET /fotoPerfil**
>>Usado para retornar a foto de perfil de um usuário autenticado entodos os modulos e paineis .
>>Se o usuário não possuir uma foto definida ou ocorrer erro, retorna uma imagem padrão.
>>Parâmetros de Query:
>> caminho_foto: ID do usuário (exemplo: /fotoPerfil?caminho_foto=46).
>>
>>Header: x-access-token: Token gerado após autenticação.
>>
>>Retorno:
>>Sucesso (200): A imagem de perfil do usuário autenticado.
>>
>>Erro (400/500): A imagem padrão como fallback.
> **POST /trocarSenha**  
>> - Usado para trocar a senha do proprio acesso e outros acessos , usado em todos os modulos e paineis .  
>> - O sistema valida se os parâmetros necessários foram fornecidos antes de proceder com a atualização da senha.  
>> Header:  
>> - `x-access-token`: Token gerado após autenticação do usuário.  
>> Parâmetros no Body:  
>> {  
>>  `"user_id"`: "ID do usuário que deseja trocar a senha",  
>>  `"senha"`: "Nova senha para o usuário"  
>> }  
>> Retorno:  
>> - Sucesso (`200`):  
>> `{ "message": "senha editada com sucesso" }`  
>> - Erro (`400`):  
>> `{ "message": "Parâmetros vazios" }`  
>> - Erro (`500`):  
>> `{ "message": "Erro na execução do processo: <detalhes do erro>" }`  
>> - Erro (`401`):  
>> `{ "message": "Usuário ou senha inválidos" }`
>>
>**GET /filtroGeralAgente**   
>> - Usado para retornar informações gerais sobre os usuários e seus acessos atraves do painel de agente .  
>> - Realiza uma consulta no banco de dados para buscar dados do usuário e se o acesso está ativo.  
>>   Header:  
>> - `x-access-token`: Token gerado após autenticação do usuário.  
>>   Parâmetros de Consulta:  
>> - Nenhum.
>> Retorno:  
>> - Sucesso (`200`):  
>>    `{  
>>        "message": [  
>>            {  
>>                "nome_usuario": "nome_do_usuario",  
>>                "id": "ID_do_usuario",  
>>                "nome": "Nome do usuário",  
>>                "ativo": "Status de ativo (true/false)",  
>>                "email": "Email do usuário",  
>>                "data_criacao": "Data de criação da conta do usuário"  
>>            },  
>>            ...  lista
>>        ]  
>>    }`
>> - Erro (`500`):  
>>    `{ "message": "Erro na execução do processo" }`  
>> - Erro (`404`):  
>>    `{ "message": "Usuário não encontrado" }`
> **GET /filterIdAgenteCriado**  
>> - Usado para filtrar informações de um usuário específico com base no `user_id sua funcionalidade está ativa atraves do painel de agente .    
>> - A API valida se o parâmetro `user_id` foi fornecido corretamente e retorna os dados do usuário.  
>> Parâmetros de Consulta:  
>> - `user_id`: ID do usuário que será buscado (tipo `INT`).
>> Header:  
>> - `x-access-token`: Token gerado após autenticação do usuário.  
>> Retorno:  
>> - Sucesso (`200`): <br>  
>>    `{  <br>
>>        "message": [  <br>
>>            { <br> 
>>                "nome_usuario": "nome_do_usuario",  <br>
>>                "id": "ID_do_usuario",  <br>
>>                "nome": "Nome do usuário",  <br>
>>                "ativo": "Status de ativo (true/false)",  <br> 
>>                "email": "Email do usuário",  <br>
>>                "data_criacao": "Data de criação da conta do usuário",  <br>
>>                "sobrenome": "Sobrenome do usuário",  <br>
>>                "tipo_acesso": "Tipo de acesso do usuário", <br>  
>>                "cargo": "Cargo do usuário",  <br>
>>                "telefone": "Telefone do usuário" <br> 
>>            }  <br>
>>        ]  <br>
>>    }`<br>
>> - Erro (`400`):  
>>    `{ "message": "Insira um valor inteiro" }`  
>> - Erro (`400`):  
>>    `{ "message": "Parametros da requisição invalido" }`  
>> - Erro (`500`):  
>>    `{ "message": "Erro no servidor" }`  
>> - Erro (`404`):  
>>    `{ "message": "Usuário não encontrado" }`
>>
> **GET /filterLoginAgentes**  
>> - Usado para filtrar informações de um agente com base no `nome_usuario`.  
>> - A API valida se o parâmetro `nome_usuario` foi fornecido corretamente e retorna os dados do agente.  
>> Parâmetros de Consulta:  
>> - `nome_usuario`: Nome de usuário para realizar a busca, função ativa no painel de agente .
>> Header:  
>> - `x-access-token`: Token gerado após autenticação do usuário.
>> Retorno:  
>> - Sucesso (`200`):  <br>
>>    `{  <br>
>>        "message": [ <br>  
>>            {  <br>
>>                "nome_usuario": "nome_do_usuario", <br>  
>>                "id": "ID_do_usuario",  <br>
>>                "nome": "Nome do usuário",  <br>
>>                "ativo": "Status de ativo (true/false)", <br>  
>>                "email": "Email do usuário",  <br>
>>                "data_criacao": "Data de criação da conta do usuário" <br>  
>>            }  <br>
>>        ]  <br>
>>    }`<br>
>> - Erro (`400`):  
>>    `{ "message": "Erro na passagem de parametros" }`  
>> - Erro (`500`):  
>>    `{ "message": "Erro no servidor" }`  
>> - Caso não encontre dados para o usuário:  
>>    `{ "message": null }`
 > **POST /insertAcesso**  
>> - Usado para inserir um novo acesso de usuário no sistema usual no painel de acessos .<br>  
>> - A API valida os parâmetros fornecidos e verifica se o nome de usuário já está registrado antes de inserir o novo usuário no banco de dados.<br>  
>> Parâmetros no Body:<br>  
>> <br>  
>>    `"nome"`: "Nome do usuário",<br>  
>>    `"sobrenome"`: "Sobrenome do usuário",<br>  
>>    `"email"`: "Email do usuário",<br>  
>>    `"telefone"`: "Telefone do usuário",<br>  
>>    `"cargo"`: "Cargo do usuário",<br>  
>>    `"usuario"`: "Nome de usuário para login",<br>  
>>    `"senha"`: "Senha para o usuário",<br>  
>>    `"status"`: "Status do usuário (true/false)",<br>  
>>    `"tipoAcesso"`: "Tipo de acesso (ex: 'adm')"<br>  
>>  }<br>  
>> Header:<br>  
>> - `x-access-token`: Token gerado após autenticação do usuário. <br>  
>> Retorno: <br>  
>> - Sucesso (`200`): <br>  
>>    `{ <br>  
>>        "message": "Processo realizado com sucesso" <br>  
>>    }` <br>  
>> - Erro (`400`): <br>  
>>    `{ <br>  
>>        "error": "Parâmetros inválidos, preencha todos os campos obrigatórios!" <br>  
>>    }` <br>  
>> - Erro (`500`): <br>  
>>    `{ <br>  
>>        "message": "Erro na execução do processo: <detalhes do erro>" <br>  
>>    }` <br>  
>> - Caso o usuário já esteja registrado: <br>  
>>    `{ <br>  
>>        "message": "Usuário <nome_usuario> já registrado no sistema. Utilize outro login." <br>  
>>    }`  
>**POST /updateAcesso**  
>> - Usado para atualizar informações de um usuário ou seu status de acesso. <br>  
>> - A API valida os parâmetros fornecidos e realiza a atualização nas tabelas `usuarios` ou `acessos`, dependendo da coluna a ser alterada. <br>  
>> Parâmetros no Body:  
>> { <br>  
>>    `"coluna"`: "Nome da coluna a ser atualizada" (ex: 'nome', 'sobrenome', 'email', 'telefone', 'cargo', 'status'), <br>  
>>    `"valor"`: "Novo valor para a coluna especificada", <br>  
>>    `"user_id"`: "ID do usuário a ser atualizado" <br>  
>> } <br>  
>> Header: <br>  
>> - `x-access-token`: Token gerado após autenticação do usuário. <br>  
>> Retorno: <br>  
>> - Sucesso (`200`): <br>  
>>    `{ <br>  
>>        "message": "Edição realizada com sucesso" <br>  
>>    }` <br>  
>> - Erro (`400`): <br>  
>>    `{ <br>  
>>        "message": "erro na passagem de parametro" <br>  
>>    }` <br>  
>> - Erro (`500`): <br>  
>>    `{ <br>  
>>        "message": "Erro ao executar o processo no dba <detalhes do erro>" <br>  
>>    }` <br>  
>> - Caso a coluna não seja reconhecida: <br>  
>>    `{ 
>>        "message": "Não foi identificado a coluna <coluna> na tabela de usuarios ou acessos" <br>  
>>    }`
>>   
>**GET /agentesTarefas**  
>> - Usado para filtrar agentes com base no status de atividade (`ativo`) e tipo de acesso (`tipo_acesso`).  
>> - A API valida se ambos os parâmetros foram fornecidos e retorna os dados dos agentes correspondentes.  
>> Parâmetros de Consulta:  
>> - `ativo`: Status de atividade do agente (ex: true/false).  
>> - `tipo_acesso`: Tipo de acesso do agente (ex: 'admin', 'user').  
>> Header:  
>> - `x-access-token`: Token gerado após autenticação do usuário.  
>> Retorno:  
>> - Sucesso (`200`):  
>>    `{  
>>        "message": [  
>>            {  
>>                "id": "ID do usuário",  
>>                "nome": "Nome do usuário",  
>>                "ativo": "Status de ativo (true/false)",  
>>                "sobrenome": "Sobrenome do usuário"  
>>            }  
>>        ]  
>>    }`  
>> - Erro (`400`):  
>>    `{  
>>        "message": "Parametros invalidos"  
>>    }`  
>> - Erro (`500`):  
>>    `{  
>>        "message": "Erro no servidor"  
>>    }`


---
***Diagrama caso de clase  / aplicação java / desktop***

- Segue detalhes sobre as classes , funções , funcionalidade e relações de classes em java para a criação da aplicação desktop no qual o adm ira definir as taredas

  
  ![image](https://github.com/user-attachments/assets/aed775ce-ad7a-4fce-980a-463a0003c1ab)
- https://drive.google.com/file/d/1_GXcIcqJbEhsc9kr4W4dG6xo7ccff90r/view?usp=sharing

![image](https://github.com/user-attachments/assets/f5491c3b-8aa4-4c26-8dcb-cf28ed81ff72)

![image](https://github.com/user-attachments/assets/ba98d1b4-31ff-47f4-8284-eb7093e8ffa5)

---

***Diagrama caso de classe / mobile em java / android***

- Aqui será adicionado no futuro detalhes sobre as classes , funções , funcionalidade e relações de classes em java para a criação da aplicação mobile onde o usuario agente ira responder e executar as tarefas .

---

## **Requisitos Funcionais**

### **Geral**
- Todos os acessos ao sistema passarão por autenticação com **usuário** e **senha**.
- Os usuários poderão:
  - Responder tarefas, visualizar execuções e acompanhar seu progresso.
  - Atualizar dados do perfil, incluindo senha, telefone, e-mail e foto de identificação.
- Apenas acessos **ativos** poderão realizar login. Acessos **desativados** serão bloqueados.

### **Acesso de Usuário Administrador (Desktop)**
- Apenas usuários com perfil **administrador** poderão acessar o painel desktop.
- O painel desktop será dividido em módulos:
  1. **Perfil:**
     - Permite ao administrador editar suas informações pessoais, como foto, dados de cadastro e senha.
  2. **Acessos:**
     - Gerencia todos os usuários (administradores e agentes):
       - Filtrar usuários por tipo de acesso.
       - Visualizar, editar ou excluir acessos.
       - Inserir novos acessos (administradores ou agentes).
  3. **Tarefas:**
     - Permite ao administrador:
       - Acompanhar o progresso das tarefas atribuídas aos agentes.
       - Criar novas tarefas e demandas para os agentes.
       - Gerar relatórios sobre o status das tarefas.

---

## **Requisitos Não Funcionais**

### **Geral**
- O sistema utilizará um **servidor web** centralizado, desenvolvido em **Node.js** com APIs RESTful.
- Após autenticação, os usuários receberão um **token de acesso** vinculado à sessão, utilizado para operações de edição e consulta via APIs.
- Todos os dados gerados no sistema serão armazenados em um **banco de dados relacional**.

### **Acesso de Usuário Administrador (Desktop)**
- A aplicação desktop foi desenvolvida em **Java** para garantir segurança e exclusividade:
  - Apenas máquinas autorizadas poderão acessar o serviço.
  - Operações de visualização e edição de dados serão realizadas exclusivamente via chamadas às APIs.
- O sistema desktop terá acesso direto às APIs para operações críticas, como criação e atualização de campos no banco de dados.

---

## **Observações**
1. **Painel Mobile (Agente - Android):**
   - Requisitos funcionais e não funcionais para o painel de agentes ainda estão em desenvolvimento.
   - Quando definidos, serão incluídos nesta documentação.

2. **Servidor e Banco de Dados:**
   - Todas as funcionalidades dependem de integrações com as APIs centralizadas.
   - O banco de dados foi projetado para armazenar dados de forma eficiente e segura, suportando alto volume de acessos simultâneos.

---


>> **Implementações futuras**
>> - Sera adicionado rotas e funcionalidades para criações dos chamados
>> - As apis de filtro de acessos teram funcionalidade para retornar acessos especificos de usuarios e agentes (GET /filtroGeralAgente ,  GET/filterIdAgenteCriado ,  GET /filterLoginAgentes)
>> - Usuarios desativados não poderam atualizar dados com status falso
>> - Adicionar demais rotas para a aplicação mobile 

