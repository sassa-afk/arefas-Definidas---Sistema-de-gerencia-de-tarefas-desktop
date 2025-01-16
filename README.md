👋 Olá , eu sou o Samuel Souto dos Santos / @sassa-afk 👀 ..
- Tenho interesse na área de desenvolvimento de software 🌱
- Atualmente estudo e crio projetos com intenção aplicar meus conhecimentos passados ao longo de minha experiência academica e no mercado de trabalho 
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

- Painel de adm (Desktop) :<br>

**Painel de login**
  ![of11](https://github.com/user-attachments/assets/af29be18-e477-423f-9edf-69a8e4d95172)

**Painel de perfil**
  ![Apex_22](https://github.com/user-attachments/assets/594ad26d-e786-40be-9a13-05f91f92f77c)

**Painel de acesso**
  ![33](https://github.com/user-attachments/assets/888304f1-9060-402d-8c23-65520e746a40)
**Painel de tarefas**
  ![Apex_44](https://github.com/user-attachments/assets/dd6b6c0e-607f-4951-9af0-f2ac39d73f19)

- Aplicação agente (APLICAÇÃO ANDROID MOBILE ) <br>
 
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
* Banco tarefasdefinir
![image](https://github.com/user-attachments/assets/1cf9901f-9d51-4c8b-8bf3-495286aa1ebc)
![image](https://github.com/user-attachments/assets/00061b33-f2b7-4234-a548-d4f011f9ebb5)

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
    descricao VARCHAR(400) NOT NULL , 
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
    
    data_tarefa_criada TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  

    tempo_estimado_fim_tarefa TIMESTAMP NOT NULL,  -- '2024-12-31 23:59:59' padrão de inserção
    
    prioridade VARCHAR(50) NOT NULL , 
    
    FOREIGN KEY (id_user_destino_tarefa_fk) REFERENCES acessos(user_id),  
    FOREIGN KEY (id_user_criado_tarefa_fk) REFERENCES acessos(user_id)    
);

CREATE TABLE tempo_tarefas (
    taref_id BIGSERIAL PRIMARY KEY,
    
    id_tarefa_fk BIGINT NOT NULL,   
    id_user_interacao_fk INT NOT NULL,
    
    tempo_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   
    status VARCHAR(100) NOT NULL,
    prioridade VARCHAR (150) NOT NULL,
    
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
***Codigo e funções Node js / ROTAS APIS /Serviço backend*** 
Foi adotada a arquitetura de API para possibilitar a comunicação entre as interfaces de agente e administrador. As APIs foram desenvolvidas utilizando JavaScript no ambiente Node.js, implementando os métodos POST e GET. A segurança das requisições foi garantida através da validação com assinatura digital JWT (JSON Web Token), gerando um token que assegura a autenticidade e a integridade das transações. Esse processo de validação oferece uma camada adicional de segurança nas operações de busca e inserção de dados no banco de dados do sistema.

Além disso, foi criada uma pasta pública dentro do diretório, responsável por gerenciar o envio de arquivos de imagens, como fotos de perfil. Essa funcionalidade foi implementada para ajudar na identificação visual dos usuários, facilitando a distinção entre agentes e administradores.

---
***Diagrama caso de clase  / aplicação java / desktop***

- Segue detalhes sobre as classes , funções , funcionalidade e relações de classes em java para a criação da aplicação desktop no qual o adm ira definir as taredas

  
  ![image](https://github.com/user-attachments/assets/aed775ce-ad7a-4fce-980a-463a0003c1ab)
- https://drive.google.com/file/d/1_GXcIcqJbEhsc9kr4W4dG6xo7ccff90r/view?usp=sharing

![image](https://github.com/user-attachments/assets/f5491c3b-8aa4-4c26-8dcb-cf28ed81ff72)

![image](https://github.com/user-attachments/assets/ba98d1b4-31ff-47f4-8284-eb7093e8ffa5)

---
***Diagrama de Arquitetura de Software***

![image](https://github.com/user-attachments/assets/aa525fd5-deca-4620-85d9-47116502b329)


---
***Diagrama caso de classe / mobile em java / android***
![image](https://github.com/user-attachments/assets/fa2287bb-78fc-4dd6-942c-cb5833ee34e9)

https://drive.google.com/file/d/17_dhg15zWB3SF4T2c4X0m8JFkCet1Hmg/view?usp=sharing
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

# Requisitos Funcionais

1. **Gerenciamento de Usuários (Administradores)**
   - O sistema deve permitir que os administradores criem, editem e excluam usuários, sejam administradores ou agentes.
   - O sistema deve permitir que administradores redefinam senhas de usuários.
   - O sistema deve permitir que administradores visualizem uma lista de todos os usuários cadastrados.

2. **Criação e Atribuição de Tarefas (Administradores)**
   - O sistema deve permitir que administradores criem tarefas com um título, descrição, prazo e critérios de prioridade.
   - O sistema deve permitir que administradores atribuam tarefas a agentes específicos.
   - O sistema deve permitir que administradores editem ou excluam tarefas criadas.

3. **Acompanhamento de Tarefas (Agentes)**
   - O sistema deve permitir que agentes visualizem suas tarefas atribuídas, incluindo título, descrição, prazo e status da tarefa.
   - O sistema deve permitir que agentes alterem o status das tarefas para "em andamento" ou "finalizada".
   - O sistema deve permitir que agentes adicionem observações ou atualizações durante a execução de tarefas.

4. **Consultas de Indicadores (Agentes)**
   - O sistema deve permitir que os agentes visualizem indicadores relacionados ao desempenho das tarefas, como número de tarefas em aberto, em andamento e finalizadas.

5. **Autenticação de Usuários**
   - O sistema deve exigir autenticação por meio de **JWT** (JSON Web Token) para garantir que apenas usuários autenticados possam acessar as APIs e plataformas.
   - O sistema deve garantir que, após o login, o token JWT seja utilizado para autenticar as requisições feitas aos endpoints da API.

6. **Envio de Arquivos**
   - O sistema deve permitir que agentes enviem fotos de perfil, com o armazenamento dessas imagens em um diretório público no servidor.

7. **Plataforma Desktop para Administradores**
   - O sistema deve oferecer uma plataforma desktop desenvolvida em **Java**, acessível por administradores, com funcionalidades de gerenciamento de usuários e tarefas.

8. **Plataforma Mobile para Agentes**
   - O sistema deve oferecer uma plataforma móvel compatível com **dispositivos Android**, permitindo que os agentes gerenciem suas tarefas de forma prática.

---

# Requisitos Não Funcionais

1. **Segurança**
   - O sistema deve garantir que todos os dados sensíveis, como senhas e tokens de autenticação, sejam armazenados e transmitidos de forma segura utilizando técnicas de criptografia adequadas.
   - O sistema deve garantir a integridade das requisições através de autenticação JWT para proteger os dados trocados entre o front-end e o back-end.

2. **Desempenho**
   - O sistema deve ser capaz de processar requisições de forma eficiente, com um tempo de resposta para a interface de administração e a interface de agente de, no máximo, **3 segundos** para ações críticas (como criação ou atribuição de tarefas).
   - O sistema deve ser escalável para suportar um número crescente de usuários simultâneos sem comprometer o desempenho.

3. **Compatibilidade**
   - A plataforma **desktop** deve ser compatível com as versões mais recentes do sistema operacional **Windows** e **Linux**.
   - A plataforma **mobile** deve ser compatível com dispositivos Android, nas versões **5.0 (Lollipop)** ou superiores.

4. **Usabilidade**
   - A interface deve ser intuitiva e de fácil uso, permitindo que tanto administradores quanto agentes realizem suas tarefas com o mínimo de treinamento.
   - O sistema deve fornecer mensagens claras de erro e sucesso para ações do usuário (ex.: criação de tarefas, login, etc.).

5. **Disponibilidade**
   - O sistema deve garantir alta disponibilidade, com **99%** de uptime, para garantir o funcionamento contínuo das plataformas de administradores e agentes.

6. **Manutenibilidade**
   - O código-fonte do sistema deve ser modular e bem documentado, de forma que seja fácil de manter, corrigir erros e adicionar novas funcionalidades no futuro.
   - O sistema deve permitir a fácil atualização de componentes de software, como APIs e interfaces, sem causar grandes interrupções.

7. **Armazenamento e Backup**
   - O sistema deve armazenar os dados no banco de dados **PostgreSQL** e garantir backups periódicos para proteger contra perda de dados.

---


>> **Implementações futuras**
>>  Ajusta e melhorias na aplicação serão detalhadas nos diretorios de cada cod da aplicação


