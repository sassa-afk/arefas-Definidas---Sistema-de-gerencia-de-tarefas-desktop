
**Aplicação mobile android do agente**

O sistema é desenvolvido para Android atraves da linguagem java, e o acesso ao aplicativo é controlado por meio de uma plataforma de administração na versão desktop. Quando um agente faz login na aplicação, ele poderá visualizar:

- Menu Principal: O menu principal oferece acesso aos diferentes módulos da aplicação, incluindo a possibilidade de editar dados pessoais, como informações de contato, telefone, trocar a senha e atualizar a foto de perfil.

- Minhas Tarefas: O agente pode acessar suas tarefas com filtros específicos, responder a elas e atualizar os status de atendimento conforme necessário.

- Meus Indicadores: O agente poderá visualizar os indicadores de seu desempenho, como o número de tarefas abertas, em andamento, finalizadas e análises das avaliações de seu atendimento.

Acompanhe detalhes das paginas construidas. 

https://github.com/user-attachments/assets/bab28502-9f1d-46f2-9686-2c614993dcfc

https://github.com/user-attachments/assets/2f40b7ff-7619-4975-b3e2-11c0c892d27a

https://github.com/user-attachments/assets/ac98cf67-552c-493c-b6a2-03e8a249d500

https://github.com/user-attachments/assets/243236bc-d96a-466c-a9cc-fe24652e0234


----

**Androi Manifest XML**
AndroidManifest.xml - Documentação
Este arquivo AndroidManifest.xml descreve a configuração e as permissões essenciais para a aplicação Android. Abaixo está uma explicação detalhada dos elementos presentes no manifesto.

# AndroidManifest.xml  

Este arquivo `AndroidManifest.xml` descreve a configuração e as permissões essenciais para a aplicação Android. Abaixo está uma explicação detalhada dos elementos presentes no manifesto.

##  Declaração do Manifesto

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
xmlns:android: Define o namespace padrão para todos os elementos do manifesto.
xmlns:tools: Declara o namespace para a ferramenta de desenvolvimento, usada para metadados adicionais, como a versão do SDK ou alterações específicas do compilador.
2. Permissões


<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
Essas permissões solicitam acesso a recursos críticos do sistema:

android.permission.INTERNET: Permite que a aplicação acesse a internet.
android.permission.READ_EXTERNAL_STORAGE: Permite a leitura de dados no armazenamento externo.
android.permission.WRITE_EXTERNAL_STORAGE: Permite a gravação de dados no armazenamento externo.
3. Configuração do Aplicativo

r
<application
    android:allowBackup="true"
    android:dataExtractionRules="@xml/data_extraction_rules"
    android:icon="@mipmap/ic_launcher"
    android:label="@string/app_name"
    android:networkSecurityConfig="@xml/network_security_config"
    android:roundIcon="@mipmap/ic_launcher_round"
    android:supportsRtl="true"
    android:theme="@style/Theme.TarefasAgente"
    tools:targetApi="31">
android:allowBackup: Define se os dados do aplicativo podem ser copiados para um backup.
android:dataExtractionRules: Define as regras de extração de dados do aplicativo a partir de um arquivo XML.
android:icon: Define o ícone do aplicativo.
android:label: Define o nome do aplicativo.
android:networkSecurityConfig: Define a configuração de segurança da rede para proteger a comunicação de dados.
android:roundIcon: Define o ícone redondo do aplicativo.
android:supportsRtl: Indica se o aplicativo oferece suporte para idiomas da direita para a esquerda (RTL).
android:theme: Define o tema do aplicativo, especificando um estilo personalizado.
tools:targetApi: Indica a versão alvo da API para o aplicativo.
4. Declaração de Atividades
As atividades são os componentes principais da aplicação e podem ser configuradas da seguinte forma:

Atividades não exportadas:


<activity
    android:name=".TarefasP5"
    android:exported="false" />
<activity
    android:name=".Fragmento2"
    android:exported="false" />
<activity
    android:name=".Fragmento1"
    android:exported="false" />
<activity
    android:name=".TarefasP4"
    android:exported="false" />
<activity
    android:name=".tarefasP2"
    android:exported="false" />
<activity
    android:name=".HeaderActivity"
    android:exported="false" />
<activity
    android:name=".tarefasP1"
    android:exported="false"
    android:screenOrientation="portrait" />
Essas atividades são configuradas para não serem exportadas (não acessíveis de fora do aplicativo) com android:exported="false". Algumas delas também têm configurações de orientação de tela, como no caso de .tarefasP1, que está fixado na orientação portrait.

Atividade Principal:


<activity
    android:name=".MainActivity"
    android:exported="true"
    android:screenOrientation="portrait">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
A MainActivity é a principal atividade da aplicação, configurada para ser a primeira a ser executada ao iniciar o aplicativo. O intent-filter especifica que é a atividade principal (MAIN) e que deve ser incluída na tela inicial do dispositivo (LAUNCHER).

5. Observações
Atividades não exportadas: A configuração android:exported="false" significa que essas atividades não podem ser acessadas diretamente por outros aplicativos ou componentes externos.
Segurança e configurações de rede: O arquivo de configuração de segurança de rede (network_security_config) deve garantir que a comunicação entre a aplicação e a internet siga as melhores práticas de segurança.
Orientação da tela: Algumas atividades, como .tarefasP1, estão configuradas para sempre serem exibidas em orientação retrato.


---

Este formato pode ser copiado diretamente para o repositório no GitHub, mantendo a documentação estruturada e organizada.
