
# Aplicação mobile android do agente

O sistema é desenvolvido para Android atraves da linguagem java, e o acesso ao aplicativo é controlado por meio de uma plataforma de administração na versão desktop. Quando um agente faz login na aplicação, ele poderá visualizar:

**Resalto também que a documentação enviada neste diretorio é a main principal**

- Menu Principal: O menu principal oferece acesso aos diferentes módulos da aplicação, incluindo a possibilidade de editar dados pessoais, como informações de contato, telefone, trocar a senha e atualizar a foto de perfil.

- Minhas Tarefas: O agente pode acessar suas tarefas com filtros específicos, responder a elas e atualizar os status de atendimento conforme necessário.

- Meus Indicadores: O agente poderá visualizar os indicadores de seu desempenho, como o número de tarefas abertas, em andamento, finalizadas e análises das avaliações de seu atendimento.

Acompanhe detalhes das paginas construidas. 

https://github.com/user-attachments/assets/bab28502-9f1d-46f2-9686-2c614993dcfc

https://github.com/user-attachments/assets/2f40b7ff-7619-4975-b3e2-11c0c892d27a

https://github.com/user-attachments/assets/ac98cf67-552c-493c-b6a2-03e8a249d500

https://github.com/user-attachments/assets/243236bc-d96a-466c-a9cc-fe24652e0234


----

**Diagrama de classe da aplicação**

![image](https://github.com/user-attachments/assets/fa2287bb-78fc-4dd6-942c-cb5833ee34e9)

----

**Layout XML das Paginas**

![image](https://github.com/user-attachments/assets/f8aa48e4-5cd2-4ccd-a94b-f8c79fbf6b83)

----

**Androi Manifest XML**
AndroidManifest.xml - Documentação
Este arquivo AndroidManifest.xml descreve a configuração e as permissões essenciais para a aplicação Android. Abaixo está uma explicação detalhada dos elementos presentes no manifesto.

# AndroidManifest.xml  

Este arquivo `AndroidManifest.xml` descreve a configuração e as permissões essenciais para a aplicação Android. Abaixo está uma explicação detalhada dos elementos presentes no manifesto.

- 1 . Declaração do Manifesto

```
xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
xmlns:android: Define o namespace padrão para todos os elementos do manifesto.
xmlns:tools: Declara o namespace para a ferramenta de desenvolvimento, usada para metadados adicionais, como a versão do SDK ou alterações específicas do compilador.
```
- 2 . Permissões
  
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
Essas permissões solicitam acesso a recursos críticos do sistema:

android.permission.INTERNET: Permite que a aplicação acesse a internet.
android.permission.READ_EXTERNAL_STORAGE: Permite a leitura de dados no armazenamento externo.
android.permission.WRITE_EXTERNAL_STORAGE: Permite a gravação de dados no armazenamento externo.

- 3 . Configuração do Aplicativo

```
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
```

```
android:allowBackup: Define se os dados do aplicativo podem ser copiados para um backup.
android:dataExtractionRules: Define as regras de extração de dados do aplicativo a partir de um arquivo XML.
android:icon: Define o ícone do aplicativo.
android:label: Define o nome do aplicativo.
android:networkSecurityConfig: Define a configuração de segurança da rede para proteger a comunicação de dados.
android:roundIcon: Define o ícone redondo do aplicativo.
android:supportsRtl: Indica se o aplicativo oferece suporte para idiomas da direita para a esquerda (RTL).
android:theme: Define o tema do aplicativo, especificando um estilo personalizado.
tools:targetApi: Indica a versão alvo da API para o aplicativo.
```

- 4 . Declaração de Atividades

As atividades são os componentes principais da aplicação e podem ser configuradas da seguinte forma:

Atividades não exportadas:

```
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
```

Essas atividades são configuradas para não serem exportadas (não acessíveis de fora do aplicativo) com android:exported="false". Algumas delas também têm configurações de orientação de tela, como no caso de .tarefasP1, que está fixado na orientação portrait.

Atividade Principal:

```
<activity
    android:name=".MainActivity"
    android:exported="true"
    android:screenOrientation="portrait">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

A MainActivity é a principal atividade da aplicação, configurada para ser a primeira a ser executada ao iniciar o aplicativo. O intent-filter especifica que é a atividade principal (MAIN) e que deve ser incluída na tela inicial do dispositivo (LAUNCHER).

- 5 . Observações

Atividades não exportadas: A configuração android:exported="false" significa que essas atividades não podem ser acessadas diretamente por outros aplicativos ou componentes externos.
Segurança e configurações de rede: O arquivo de configuração de segurança de rede (network_security_config) deve garantir que a comunicação entre a aplicação e a internet siga as melhores práticas de segurança.
Orientação da tela: Algumas atividades, como .tarefasP1, estão configuradas para sempre serem exibidas em orientação retrato.

---

# network-security-config.xml - Documentação

Este arquivo `network-security-config.xml` define as configurações de segurança de rede para o aplicativo Android, especificando como ele deve tratar o tráfego de rede, especialmente em relação ao tráfego sem criptografia (cleartext).

## Estrutura do Arquivo

```
xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">192.168.0.114</domain>
        <domain includeSubdomains="true">192.168.100.245</domain>
    </domain-config>
</network-security-config>
```

**OBS:** Os ips apontados são os dominios de endereçamento das apis para poderem se comunicar com o serviço web e testar as funcionalidades e requisições do sistema

**Descrição dos Elementos**

<network-security-config>

Este é o elemento raiz do arquivo e define as configurações de segurança de rede do aplicativo Android. Ele pode conter configurações específicas para domínios de rede que o aplicativo acessa.

<domain-config>
    
Este elemento é usado para configurar as regras de segurança para um ou mais domínios. Atribui-se o atributo cleartextTrafficPermitted="true", que permite o tráfego de rede sem criptografia (cleartext) para os domínios especificados. Isso é útil quando o aplicativo precisa acessar servidores locais ou de desenvolvimento sem criptografia HTTPS.

cleartextTrafficPermitted="true": Permite que o tráfego não criptografado (HTTP) seja permitido para os domínios especificados.
<domain>
Este elemento define um domínio específico para o qual as regras de segurança se aplicam. O atributo includeSubdomains="true" indica que as regras de segurança devem ser aplicadas também aos subdomínios do domínio especificado.

includeSubdomains="true": Aplica as configurações de segurança também aos subdomínios do domínio.

**Domínios Configurados**

192.168.0.114: Um domínio local, representado pelo IP 192.168.0.114, que terá permissão para tráfego de rede sem criptografia (HTTP).
192.168.100.245: Outro domínio local, representado pelo IP 192.168.100.245, que também terá permissão para tráfego de rede sem criptografia.

**Observações**

O uso de tráfego de rede sem criptografia (cleartextTrafficPermitted="true") é permitido apenas para os domínios específicos definidos no arquivo. Isso pode ser útil para testes em ambientes locais ou quando não é possível configurar HTTPS.
Certifique-se de que a configuração de tráfego sem criptografia seja usada apenas para domínios confiáveis e conhecidos, pois ela pode expor os dados transmitidos a interceptações.

Aplicação conta também com o uso de bibliotecas extras e dependecias extras para sua construção sendo elas :

```
plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.tarefasagente"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tarefasagente"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation ("com.squareup.picasso:picasso:2.71828")

    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    implementation("org.json:json:20210307")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


 }

```

Suas configuções estão disponiveis no arquivo buid do projeto 

![image](https://github.com/user-attachments/assets/1ebe025b-fb81-485a-aad3-a879ddefe8a1)


Chamo atenção para algumas cruciais que possibilitam a comunicação entre a aplicação e o servidor api node 

**JSON**

- Versão: 20210307
- Descrição: Biblioteca para trabalhar com JSON em Java. Ela fornece um conjunto de ferramentas simples para parseamento e criação de objetos JSON.
- Depedencia :
```
    implementation("org.json:json:20210307")
```

**Glide**
- Versão: 4.12.0
- Descrição: Glide é uma poderosa biblioteca de carregamento de imagens, que também suporta animações e transformações de imagens, além de fornecer recursos como cache em memória e disco.
- Depedencia :
```
    implementation("com.github.bumptech.glide:glide:4.12.0")
```
- Dependência do Processador de Anotação:

```
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
```

----

- **Melhorias futuras que serão aplicadas**
  
  - 1 - Aplicar na classe processo gerais validação de envio de documentos com extenção png
  - 2 - Melhorar dados de estaticas da função indicadores podendo filtrar por datas especificas com do dia x ate y e trazer informações robustas como media de atendimento de tarefas , tempo de media ou alguma especifica
  - 3 - Adicionar a opção de dentro da tarefa do agente a possibilidade de enviar arquivos e imagens para anexar junto a comentarios no chamado
  - 4 - Aplicar o serviço websockt para apontar notificações de novas tarefas com prioridades urgente e alta
     
  
----

