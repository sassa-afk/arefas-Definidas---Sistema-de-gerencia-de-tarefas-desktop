const data = new Date() ; 
const horas = String(data.getHours()).padStart(2, '0');
const min = String(data.getMinutes()).padStart(2 , '0');
const seg = String(data.getSeconds()).padStart(2 , '0');
const dia  = String(data.getDay()).padStart(2 , '0');
const mes  = String(data.getMonth()).padStart(2 , '0');
const ano  = String(data.getYear()).padStart(2 , '0');
const horario = ` ${horas}:${min}:${seg} - ${dia}/${mes}/${ano}`;

// --------------------------------
require('dotenv').config();

const pool= require('./db');
const express = require('express');
const http = require('http');
const cors = require('cors');
const jwt = require('jsonwebtoken');
const path = require('path');



// ---------------------------
const app = express();

app.use(express.json()); 
app.use(express.urlencoded({ extended : true })); 

app.use(express.json({limit : '2mb'}), cors());



const server = http.createServer(app);

 
//=============================================================================
//=============================== PAINEL DE ADM ===============================
//=============================================================================

// ---------------- auth -----------------


function auth(req, res, next) {
    console.log(`_____________ LOGS DE AUT (${horario})  ________________`);
    const token = req.headers['x-access-token'];
    const { id } = req.query;
     console.log(`>> Validando autenticação \n> ID : ${id} \n> token : ${token}`);
    if (!token || !id) {
        console.log('> erro na parametragem de token ou id');
            console.log("__________________________________");
        return res.status(400).json({ error: 'parametros ausentes de token ou id' });
    }
    jwt.verify(token, process.env.SECRET, (err, decoded) => {
        if (err) {
            console.log("erro " + err); 
            console.log("__________________________________");
            return res.status(401).json({ error: `erro na validação do token ${err}` });
        }
         const valor_id_aut  =  decoded.usuario
         if(valor_id_aut == id ){
            console.log(`> chave e id autenticado com sucesso`);
            console.log("__________________________________"); 
            next();
         }else{
            console.log(`> ID não vinculado ao token inserido`);
            console.log("__________________________________");
            return res.status(403).json({ error: 'ID não autorizado' });
         }  
    });
}



 

// -------------------------------
//------------- APIS -------------


// Dados do user logado para todo painel 
app.get("/dadosUser" , auth ,(req , res)=>{
    const id = req.query.id;

    console.log(`_____________ LOGS DADOS Usuario ${horario})  ________________`);
    console.log(`>> Validando acesso \n> ID : ${id} \n `);

    if(!id){

        console.log("> parametros invalidos");
        console.log("__________________________________");

        return res.status(400).json({message :'parametros invalidos '});
    } 

    pool.query(`SELECT DISTINCT Usuario.id, Usuario.nome, Usuario.email, Usuario.data_criacao, Usuario.telefone , Usuario.cargo , Usuario.sobrenome
         FROM acessos Acesso 
         RIGHT JOIN usuarios Usuario ON Acesso.usuario_id_fk = Usuario.id  
         WHERE Usuario.id = $1;`,[id],(error , result) =>{
        if(error){

            console.log("> Erro "+error);
            console.log("__________________________________");

            return res.status(500).json({error : 'erro na execução '+error});
        }

        if(result.rows.length > 0  ){
        console.log("> chamada realizada com sucesso "+result.rows[0]);
        console.log("__________________________________");
            // return res.json({mesage : result.rows[0]});
            return res.json({mesage : result.rows});
        }

    });
});


//-------------------------------------------
// Painel de login

app.post("/auth", async (req , res) => {
 
    const user = req.body.nome_usuario ;
    const senha =  req.body.senha;

    if(user === null || senha === null ){
        console.log("> sem passagem  de parametro NO AUTH");
        return res.status(400).json({aut:false , message: 400 });
    }

    pool.query('SELECT user_id , ativo FROM acessos  WHERE nome_usuario = $1 and senha = $2 and tipo_acesso = $3',[user,senha,'adm'],(error , result) => {
    
    if(error){
        console.log('erro na auth do usuario $1'+error , [user,senha]);
        return res.status(500).json({aut:false , message:500});
    }
    
    if(result.rows.length === 0 ){
        console.log("> NO AUTH "+user+" "+senha);
        return res.status(401).json({aut:false , message: 401 });
    }
    console.log("> AUTH OK usuario id "+result.rows[0].user_id);


    if(result.rows[0].ativo == true ){
        try{

            const playload = {usuario : result.rows[0].user_id } ; 
            const token = jwt.sign( playload , process.env.SECRET  );

            console.log("> gerado token : "+token);
            return res.json({ "auth":token  , "user_id": result.rows[0].user_id , "ativo": result.rows[0].ativo });
  

        }catch(error){
            console.log("> Erro no servidor "+error);
            return res.json({message : 500});
        }
    }else{
            console.log(`> Usuario ${user} sem permisão de acesso `);
            // return res.status(503).json({ "auth":false   , message : 503  });
            return res.json({ "auth":"" , "user_id": result.rows[0].user_id , "ativo": result.rows[0].ativo });
    }

     });
});

//-------------------------------------------
// Uso geral painel de acesso e agente


// foto perfil 
// const path = require('path');

app.get( "/fotoPerfil",  async (req, res) => {
    console.log(`_____________ Foto perfil ${horario}__________`);
        const caminhoPadrao = path.join(__dirname, 'public/fotos_perfil/padrao.jpeg');

    const caminho_foto = req.query.caminho_foto;
        if (!caminho_foto) {
        console.log(">> erro, passagem de parametro ");
        // const caminhoPadrao = path.join(__dirname, 'public/fotos_perfil/eu27.jpeg');
        console.log("Caminho do arquivo padrão:", caminhoPadrao);
        return res.sendFile(caminhoPadrao);
    }

    pool.query('SELECT foto FROM usuarios WHERE id = $1',[caminho_foto] , ( err , result )=>{

        if(err){
             console.log(`-  erro na execução da consulta : ${err}`);
            return res.sendFile(caminhoPadrao);
        }

        if(result.rows.length === 0 ){
             console.log(`- Consulta sem dados `);
            return res.sendFile(caminhoPadrao);
        }

        console.log('>> '+result.rows[0].foto);
        const fotoPath = result.rows[0].foto; 
        const novocaminho =  path.join(__dirname, fotoPath ) ;  
        // return res.json({message : fotoPath });
         return res.sendFile( novocaminho) ; 



    });
});

app.post("/trocarSenha", auth, async (req, res) => {
    const user_id = req.body.user_id;
    const senha = req.body.senha ;
    console.log("startado troca");
    if (!user_id || !senha) {

        if(!user_id){console.log("user invalido ");}
        if(!senha){console.log("senha invalida");}
        console.log(">>>> erro de parametro > "+user_id+" > "+senha );
        return res.status(400).json({ message: 'Parâmetros vazios' });
    }

    pool.query('UPDATE acessos SET senha = $1 WHERE user_id = $2', [senha, user_id], (error, result) => {
        if (error) {
            console.log(">>>> erro error "+error);
            return res.status(500).json({ message: ' Erro na execução do processo: ' + error });
        }

        if (result.rowCount === 0) {
            console.log(">>>> user não localizado ");
            return res.status(401).json({ message: ' Usuário ou senha invalidos ' });
        }

        return res.json({ message: ' senha editada com sucesso' });
    });
});


//-------------------------------------------
// Painel agente
// pesquisas de filtro agente  / GET
  
app.get("/filtroGeralAgente" , auth ,(req , res)=>{

    const tipo_acesso  = req.query.tipo_acesso ;

    if(!tipo_acesso ){
        pool.query( 'SELECT Acesso.nome_usuario, Usuario.id, Usuario.nome, Acesso.ativo, Usuario.email, Usuario.data_criacao ' +
                'FROM acessos Acesso RIGHT JOIN usuarios Usuario ON Acesso.usuario_id_fk = Usuario.id;',  (err,result) => {
            if(err){
                console.log("__________________________________");
                console.log('> erro api get filtroGeralAgente '+err);
                console.log("__________________________________");
                return res.status(500);
            }
           if (result.rows.length === 0) {
                 return res.status(404).json({ message: 'Usuário não encontrado' });
            }
             return res.json({ message: result.rows});
        });

    }else{
        pool.query( 'SELECT Acesso.nome_usuario, Usuario.id, Usuario.nome, Acesso.ativo, Usuario.email, Usuario.data_criacao ' +
                'FROM acessos Acesso RIGHT JOIN usuarios Usuario ON Acesso.usuario_id_fk = Usuario.id WHERE tipo_acesso = $1;' , [tipo_acesso] ,  (err,result) => {
            if(err){
                console.log("__________________________________");
                console.log('> erro api get filtroGeralAgente '+err);
                console.log("__________________________________");
                return res.status(500);
            }
           if (result.rows.length === 0) {
                 return res.status(404).json({ message: 'Usuário não encontrado' });
            }
             return res.json({ message: result.rows});
        });
    }
});

// 3 192.168.0.114:3000/filterIdAgente?id=1&user_id=1  (retorna agentes com id indicado)
app.get('/filterIdAgente', auth, (req, res) => {
    const user_id = req.query.user_id;
    const tipo_acesso = req.query.tipo_acesso; 


    if (isNaN(Number(user_id))) {
        return res.status(400).json({ message: 'Insira um valor inteiro' });
    }

    if (!user_id) {
        return res.status(400).json({ message: 'Parametros da requisição invalido' });
    }
    if(!tipo_acesso){
        pool.query(
            'SELECT Acesso.nome_usuario, Usuario.id, Usuario.nome, Acesso.ativo, Usuario.email,  Usuario.data_criacao , Usuario.sobrenome  ' +
            'FROM acessos Acesso RIGHT JOIN usuarios Usuario ON Acesso.usuario_id_fk = Usuario.id WHERE Usuario.id = $1',
            [user_id],
            (err, result) => {
                if (err) {
                    console.log("__________________________________");
                    console.log('> erro api get filterIdAgente ' + err);
                    console.log("__________________________________");
                    return res.status(500).json({ message: 'Erro no servidor' });
                }

                if (result.rows.length === 0) {
                    return res.status(404).json({ message: 'Usuário não encontrado' });
                }

                console.log(result.rows);
                return res.json({ message: result.rows });
            }
          );
    }else{

    }
}); 
 
 // 2  192.168.0.114:3000/filterNomeAgentes?id=1&nome=Samuel (retorna agentes com o nome)

app.get("/filterNomeAgente", auth ,  (req , res )=>{
    const nome = req.query.nome ; 
    if(!nome ){
        return res.status(400).json({mesage : "Erro na passagem de parametros"});
    }

    pool.query(`SELECT Acesso.nome_usuario, Usuario.id, Usuario.nome, Acesso.ativo, Usuario.email, Usuario.data_criacao 
                FROM acessos Acesso 
                RIGHT JOIN usuarios Usuario ON Acesso.usuario_id_fk = Usuario.id 
                WHERE Usuario.nome = '${nome}';` ,(err , result)=> {
        if(err){
            console.log(`\n>> ${horario} - Erro na execução da pesquisa do agente por nome : ${nome} : ${err}`);
            return res.status(500).json({mesge:err});
        }
        if(result.rows.length> 0 ){
            console.log(`\n >> ${horario} - Pesquisa de agente pelo nome ${nome} executada com sucesso, agente localizado`);
            return res.json({ message : result.rows });
        }
        else{
            console.log(`\n >> ${horario} - Pesquisa de agente pelo nome ${nome} executada com sucesso, agente não localizado`);
            return res.json({messate : null });
        }   

    });

});
//-------------------------------------------


// 192.168.0.114:3000/filterIdAgenteCriado?id=1&user (filtra agente com id indicado, usado para retornar dados dos agentes que teram tarefas)
app.get('/filterIdAgenteCriado', auth, (req, res) => {
    const user_id = req.query.user_id;

    if (isNaN(Number(user_id))) {
        return res.status(400).json({ message: 'Insira um valor inteiro' });
    }

    if (!user_id) {
        return res.status(400).json({ message: 'Parametros da requisição invalido' });
    }

    pool.query(
        'SELECT Acesso.nome_usuario, Usuario.id, Usuario.nome, Acesso.ativo, Usuario.email,  Usuario.data_criacao , Usuario.sobrenome ,'+
        'Acesso.nome_usuario ,Acesso.ativo , Acesso.tipo_acesso , Usuario.cargo , Usuario.telefone ' +
        'FROM acessos Acesso RIGHT JOIN usuarios Usuario ON Acesso.usuario_id_fk = Usuario.id WHERE Usuario.id = $1',
        [user_id],
        (err, result) => {
            if (err) {
                console.log("__________________________________");
                console.log('> erro api get filterIdAgente ' + err);
                console.log("__________________________________");
                return res.status(500).json({ mesage: 'Erro no servidor' });
            }

            if (result.rows.length === 0) {
                return res.status(404).json({ mesage: 'Usuário não encontrado' });
            }

            console.log(result.rows);
            return res.json({ message: result.rows });
        }
    );
}); 



// 4 192.168.0.114:3000/filterLoginAgente?id=1&nome_usuario=adm retorna agentes com o login
app.get("/filterLoginAgentes",auth,(req , res )=>{
    const nome_usuario = req.query.nome_usuario; 
    if(!nome_usuario ){
        return res.status(400).json({mesage : "Erro na passagem de parametros"});
    }
    pool.query(`SELECT Acesso.nome_usuario, Usuario.id, Usuario.nome, Acesso.ativo, Usuario.email, Usuario.data_criacao 
            FROM acessos Acesso 
            RIGHT JOIN usuarios Usuario ON Acesso.usuario_id_fk = Usuario.id 
            WHERE Acesso.nome_usuario = '${nome_usuario}';` ,(err , result )=>{
        if(err){
            console.log(`\n>> ${horario} - Erro na execução da pesquisa do agente por login : ${nome_usuario} : ${err}`);
            return res.status(500).json({mesge:err});
        }
         if(result.rows.length> 0 ){
            console.log(`\n >> ${horario} - Pesquisa de agente pelo login : ${nome_usuario} executada com sucesso, agente localizado`);
            return res.json({ message : result.rows });
        } else{
            console.log(`\n >> ${horario} - Pesquisa de agente pelo login  ${nome_usuario} executada com sucesso, agente não localizado`);
            return res.json({messate : null });
        } 



    });


});

 
// Inserir usuario / POST  /

//  192.168.0.114:3000/insertAcesso?id=(numero acesso)
// {
//     "nome": "João",
//     "sobrenome": "Silva",
//     "email": "joao.silva@example.com",
//     "telefone": "11987654321",
//     "cargo": "Desenvolvedor",
//     "usuario": "joaosilva",
//     "senha": "senhaSegura123",
//     "status": true,
//     "tipoAcesso": "adm"
// }

app.post('/insertAcesso', auth, async (req, res) => {

    console.log(`_____________ INSERÇÃO DE ACESSO ${horario}__________`);

    const { nome, email, telefone, cargo, sobrenome, usuario, senha, status, tipoAcesso } = req.body;
    const caminho_foto = "public/fotos_perfil/padrao.jpeg";

 
    if (!nome || !email || !telefone || !cargo || !sobrenome || !usuario || !senha || typeof status === 'undefined' || !tipoAcesso) {
        console.log(`Parâmetros inválidos: ${nome}, ${email}, ${telefone}, ${cargo}, ${sobrenome}, ${usuario}, ${senha}, ${status}, ${tipoAcesso}`);
        return res.status(400).json({ error: 'Parâmetros inválidos, preencha todos os campos obrigatórios!' });
    }

    pool.query('SELECT user_id FROM acessos WHERE nome_usuario = $1', [usuario], (erro, retorno) => {
        if (erro) {
            return res.status(500).json({ message: `Erro na execução do processo: ${erro.message}` });
        }

        if (retorno.rows.length !== 0) {
            console.log(`Usuário ${usuario} encontrado, user_id: ${retorno.rows[0].user_id} o mesmo não será registrado`);
            return res.json({ message: ` Usuário ${usuario} já registrado no sistema. Utilize outro login.` });
        }

        pool.query(
            `INSERT INTO usuarios (nome, email, telefone, cargo, sobrenome, foto) VALUES ($1, $2, $3, $4, $5, $6)`,
            [nome, email, telefone, cargo, sobrenome, caminho_foto],
            (err) => {
                if (err) {
                    console.error('Erro na execução do processo:', err);
                    return res.status(500).json({ message: 'Erro na execução do processo: ' + err.message });
                }

                pool.query(
                    'SELECT id FROM usuarios WHERE nome = $1 AND sobrenome = $2 AND email = $3 AND telefone = $4 AND id = (SELECT MAX(id) FROM usuarios);',
                    [nome, sobrenome, email, telefone],
                    (err2, result2) => {
                        if (err2) {
                            console.log("Erro na segunda requisição:", err2);
                            return res.status(500).json({ message: `Erro na segunda requisição: ${err2.message}` });
                        }

                        if (result2.rows.length === 0) {
                            return res.status(404).json({ message: 'Usuário não encontrado após a inserção.' });
                        }

                        const idGerado = result2.rows[0].id;
                        console.log(`>>>>>> id gerado: ${idGerado}`);

                        pool.query(
                            'INSERT INTO acessos (user_id, nome_usuario, senha, usuario_id_fk, ativo, tipo_acesso) VALUES ($1, $2, $3, $4, $5, $6);',
                            [idGerado, usuario, senha, idGerado, status, tipoAcesso],
                            (err3) => {
                                if (err3) {
                                    pool.query('DELETE FROM usuarios WHERE id = $1;', [idGerado], (err3, result3) => {
                                        if (err3) {
                                            console.log("Erro na exclusão:", err3);
                                            return res.status(500).json({ error: err3 });
                                        }
                                        console.log("Erro na execução do código, usuário gerado excluído");
                                        try {
                                            return res.json({ message: `Erro na execução do código, usuário gerado excluído` });
                                        } catch (error) {
                                            console.log("Erro:", error);
                                            return res.status(500);
                                        }
                                    });
                                    return res.status(500).json({ message: `Erro na execução do processo de inserção em acessos: ${err3}` });
                                }

                                console.log('INSERT INTO acessos (user_id, nome_usuario, senha, usuario_id_fk, ativo, tipo_acesso) VALUES ($1, $2, $3, $4, $5, $6);',
                                    [idGerado, usuario, senha, idGerado, status, tipoAcesso]);
                                console.log("- registro realizado com sucesos ");

                                return res.json({ message: 'Processo realizado com sucesso' });
                            }
                        );
                    }
                );
            }
        );
    });
});


// atualizar dados 
app.post("/updateAcesso", auth, async (req, res) => {
    console.log(`_____________ SOLICITADO UPDATE DE USUARIO AS ${horario}__________`);

    const coluna = req.body.coluna;
    const valor = req.body.valor;
    const user_id = req.body.user_id;

    if (!coluna || !valor || !user_id) {
        console.log(`Erro na passagem de parametro ${coluna}, ${valor} e ${user_id}`);
        return res.status(400).json({ message: "erro na passagem de parametro" });
    }
    
    console.log(">>> solicitado processo na coluna " + coluna);

    if (coluna === 'nome' || coluna === 'sobrenome' || coluna === 'email' || coluna === 'telefone' || coluna === 'cargo') {
        pool.query(`UPDATE usuarios SET ${coluna} = $1 WHERE id = $2`, [valor, user_id], (err, result) => {
            if (err) {
                console.log("Erro ao executar o processo no dba " + err + " na tabela usuarios");
                return res.status(500).json({ mesage: err });
            }

            console.log(`Edição da ${coluna} no usuario cod ${user_id} realizada com sucesso`);
            return res.json({ message: "Edição realizada com sucesso" });
        });
    } 
    else if (coluna === "status") {
        let status_bool = false  ; 
        if(valor === "ativo" ){
            status_bool = true ;
        }else{
            status_bool = false ; 
        }
        pool.query(`UPDATE acessos SET ativo = $1 WHERE user_id = $2`, [status_bool, user_id], (err, result) => {
            if (err) {
                console.log("Erro ao executar o processo no dba " + err + " na tabela usuarios");
                return res.status(500).json({ mesage: err });
            }
            console.log(`Edição do status no usuario cod ${user_id} realizada com sucesso`);
            return res.json({ message: "Edição realizada com sucesso" });
        });
    } else {
        return res.status(400).json({ message: `Não foi identificado a coluna ${coluna} na tabela de usuarios ou acessos` });
    }
});

 
//-------------------------------------------
//-------------------------------------------
//-------------------------------------------

// Painel Tarefas 

app.get("/agentesTarefas" , auth , (req , res )=>{

    const ativo = req.query.ativo ; 
    const tipo_acesso = req.query.tipo_acesso;

    console.log("1 - "+ativo+" \n 2 - "+tipo_acesso);

    if(!tipo_acesso || !ativo ){
        console.log(">> sem parametros "+tipo_acesso+" / "+ativo);
        return res.status(400).json({ mesage : "Parametros invalidos"});
    }
    pool.query( `SELECT Usuario.id, Usuario.nome, Acesso.ativo , Usuario.sobrenome
    FROM usuarios Usuario
    INNER JOIN acessos Acesso ON Acesso.usuario_id_fk = Usuario.id
    WHERE Acesso.ativo = $1 and Acesso.tipo_acesso = $2 ;`  , [ativo , tipo_acesso ] , (err , result )=>{
        if(err){
            console.log(">> erro "+err);
            return res.status(500).json({ message : err});
        }
        console.log(">> consulta realizada com sucesso ");
        return res.json({mesage : result.rows});
    });
});


// {
//     "id_user_destino" : 47 , 
//     "id_user_gera_tarefa":91,
//      "titulo" : "Teste tarefa 1" , 
//     "tempo_estimado_os" : "2024-12-31 23:59:59" ,
//     "prioridade"  : "Baixa" 

// } 

app.post("/addTarefas" , auth , (req , res)=>{

    console.log(`_____________ LOGS add tarefas  ${horario})  ________________`); 

    const {id_user_destino , id_user_gera_tarefa , status , titulo , tempo_estimado_os , prioridade , descricao  } = req.body ;
  
    if(!id_user_destino || !id_user_gera_tarefa || !status || !titulo || !tempo_estimado_os || !prioridade  ){
        console.log("Erro na execução do processo pois há dados invalidos");
        return res.status(400).json({ message: 'Parâmetros vazios' });
    }
        
    pool.query(` INSERT INTO tarefas (  id_user_destino_tarefa_fk,id_user_criado_tarefa_fk,status,
        titulo,tempo_estimado_fim_tarefa,prioridade,descricao ) VALUES ( $1 , $2 , $3 , $4 , $5 , $6 , $7 ) ;` ,
     [  id_user_destino , id_user_gera_tarefa , status , titulo  , tempo_estimado_os , prioridade , descricao ] ,
     (err , result )=>{
        if(err){

            console.log("Erro na execução do processo parte 1 erro "+err);
            return res.status(400).json({message : "Erro na execução da inserção "+err });

        }

            console.log(">  Inserção na tabela chamado com sucesso ");

            pool.query(`SELECT taref_id FROM tarefas 
                    WHERE titulo = $1 
                    AND id_user_destino_tarefa_fk = $2 
                    AND id_user_criado_tarefa_fk = $3 
                    AND tempo_estimado_fim_tarefa = $4;` , 
                [titulo , id_user_destino ,  id_user_gera_tarefa , tempo_estimado_os  ]  ,
                (err2 , result2) => {

                    if(err2){
                        console.log("Erro na execução do processo parte 2 erro "+err2);
                        return res.status(400).json({messagem : " Erro ao localizar id aberto do chamado : "+err2 });

                    }

                    if(result2 === 0 ){
                        return res.json({messagem : "nada encontrado"});

                    }

                    const numero_chamado  = result2.rows[0].taref_id ;

                    pool.query(`INSERT INTO tempo_tarefas( id_tarefa_fk , id_user_interacao_fk , status , prioridade ) 
                    VALUES ( $1 , $2 , $3 , $4 );` ,
                    [ numero_chamado , id_user_gera_tarefa , status , prioridade ] ,
                    (err3 , result3) => {
                        if(err3){

                            console.log("> Erro na execução do processo parte 3 "+err3);
                            return res.status(400).json({ messagem : "Erro na execução do processo parte 3 "+err3});

                        }
                            return res.json({messagem : "Tarefa regis com sucesso "});

                     });
 
            });

    });
 
});

//========================================================================================
 // filtra todas as tarefas 
app.get("/filtroTodasTarefas" , auth , (req , res )=>{

    console.log(`_____________ LOGS filtrar todas tarefas  ${horario})  ________________`); 
   
    const id_agete_tarefa = req.query.id_agete_tarefa ; 

    pool.query(
        `SELECT taref_id ,  titulo , status ,  data_tarefa_criada , prioridade FROM tarefas WHERE  id_user_destino_tarefa_fk = $1 ORDER BY taref_id  DESC  ` , 
        [id_agete_tarefa] ,
        (err ,result )=> {
            if(err){
                                    console.log("> erro"+err);

                return res.status().json({message : "> Erro na execução do chmado :"+err});
            }

            if(result.rows.length == 0 ){

                console.log("> Não identificado consulta , tabela sem dados");
                return res.json({message : "" });
            }

            console.log("> executado com sucesso");
            return res.json({ message : result.rows });
 

    });
});

// filtra o numero da tarefa
app.get("/fiterNumeroTarefas",auth , (req, res)=>{
    console.log(`_____________ LOGS filtrar numero de tarefa  ${horario})  ________________`); 

    const id_user_destino_tarefa_fk = req.query.id_user_destino_tarefa_fk ; 
    const taref_id = req.query.taref_id ; 

        console.log("--------- "+id_user_destino_tarefa_fk+"  "+taref_id);

    if(!taref_id || !id_user_destino_tarefa_fk){
        console.log("> Campos de id invalido");
        return res.json({message : "Requisição sem parametros " });
    }

    pool.query(`SELECT taref_id ,  titulo , status ,  data_tarefa_criada , prioridade 
        FROM tarefas WHERE taref_id = $1 and   id_user_destino_tarefa_fk = $2`,
        [taref_id , id_user_destino_tarefa_fk ] ,
        (err , result )=>{

            if(err){
                console.log("> erro interno ao realizar o processo : "+err);
                return res.status(400).json({ message : "Erro interno "+err });
            }

            if(result.rows.length === 0 ){
                console.log("Sem retorno nas tabelas ");
                return res.json({message : ""});
            }

            return res.json({message : result.rows});

        });
});

// filtra tarefas por status
// SELECT taref_id ,  titulo , status ,  data_tarefa_criada , prioridade FROM tarefas WHERE status  = $1 ;

app.get("/filterStatusTarefa" , auth , (req,res)=>{

    console.log(`_____________ LOGS filtrar status da tarefa  ${horario}  ________________`); 
    
    const status = req.query.status;
    const id_user_destino_tarefa_fk = req.query.id_user_destino_tarefa_fk ; 

    if(!status || !id_user_destino_tarefa_fk ){

        console.log("> Erro na passagem de parametros ");
        return res.status(400).json({message : "Erro na passagem de parametro"});
    }

    pool.query("SELECT taref_id ,  titulo , status ,  data_tarefa_criada ,"+
        " prioridade FROM tarefas WHERE status  = $1 and id_user_destino_tarefa_fk = $2;",
               [status , id_user_destino_tarefa_fk ],
               (err , result )=>{
                    if(err){
                        console.log("> erro interno na execução do sql : "+err);
                        return res.status(400).json({message : err });
                    }

                    if(result.rows.length === 0 ){
                        console.log("> Não há dados deste status na tabela");
                        return res.json({message : ""}); 
                    }

                    console.log("Executado consuta com sucesso ");

                    return res.json({message : result.rows});
               });
});


app.get("/filterPrioridadesTarefas" , auth , (req , res)=> {

    console.log(`_____________ LOGS filtrar status da tarefa  ${horario}  ________________`); 

    const prioridade = req.query.prioridade ; 
    const id_agente = req.query.id_agente; 

    if( !prioridade || !id_agente) {
        console.log("> Falta de parametros");
        return res.json({ message : "Falha na passage de parametros "});
    }
    // SELECT taref_id ,  titulo , status ,  data_tarefa_criada , prioridade FROM tarefas WHERE prioridade  =   and id_user_destino_tarefa_fk = $2  ORDER BY taref_id  ASC
    pool.query("SELECT taref_id ,  titulo , status ,  data_tarefa_criada , prioridade FROM tarefas WHERE prioridade  = $1"+
        "  and id_user_destino_tarefa_fk = $2  ORDER BY taref_id  DESC  ;" , /*ORDER BY taref_id  DESC */
        [prioridade , id_agente] , 
        (err , result )=>{
            if(err){
                console.log("> Erro interno na execução do comando sql "+err);
                return res.json({message : err});
            }

            if(result.rows.length === 0 ){
                console.log("> Tabela sem dados para retorno");
                return res.json({message : ""});
            }

            console.log("> Comando executado com sucesso");
            return res.json({ message : result.rows});

    })
});

 
app.get("/dadosTarefas",auth , ( req , res )=>{

    console.log(`_____________ LOGS dados tarefas  ${horario})  ________________`); 

    const id_tarefa = req.query.id_tarefa ;

    if(!id_tarefa){
        console.log(">> Erro, sem passagem de parametro na chaada da api");
        return res.status(400).json({message : "Chamada sem parametros"});
    }

    pool.query(
        "SELECT * FROM tarefas WHERE taref_id = $1"  ,    
        [id_tarefa],
        (err , result ) =>{ 
            if(err){
                console.log(">> Erro interno sql "+err);
                return res.json({err})
            }

            console.log(">> Consulta realizada com sucesso ");
            return res.json({message : result.rows[0]});

        });
});

app.get("/dadosComentariosTarefas",auth,(req,res)=>{

    const id_tarefa = req.query.id_tarefa;

    console.log(`_____________ LOGS dados comentario tarefa ${id_tarefa} as ${horario})  ________________`); 

    if(!id_tarefa){
        console.log(">> Requisição sem passagem de parametros");
        return res.status(400).json({message : "Requisição sem parametros"});
    }
    pool.query(
        `SELECT Usuario.nome ,  Usuario.sobrenome , Comentarios.comentario , Comentarios.data_comentario , Comentarios.id_user_comentado
        FROM usuarios Usuario  
        INNER JOIN comentarios Comentarios ON Comentarios.id_user_comentado = Usuario.id
        WHERE Comentarios.taref_id_fk= $1;` , [id_tarefa], (err , result)=>{
            if(err){
                console.log(">> Erro interno no comando sql "+err);
                return res.status(500).json({message : "Não foi identificado comentarios nesta tarefa" });
            } 
            if(result.rows.length === 0 ){
                console.log("Não foi identificaodo comentarios para está tarefa");
                return res.json({message : " Tarefa sem comentarios" })
            }
            console.log(">> API executada com sucesso ");
            return res.json({message : result.rows});

        });
});

 

app.post("/addComentarios" ,  auth , async  (req , res ) =>{
    console.log(`_____________ Adicionar comentarios nas tarefas ${horario}__________`);

    const { id_tarefa , id_user_comentado , comentario , status , prioridade  } = req.body;

    if( !id_tarefa || !id_user_comentado || !comentario || !status || !prioridade ){
        console.log(">> parametros invalidos");
        return res.status(400).json({message : "parametros invalidos"});
    }

    pool.query(
        `INSERT INTO comentarios (taref_id_fk , id_user_comentado , comentario )
        VALUES ($1 , $2 , $3) ;`,
        [ id_tarefa , id_user_comentado  , comentario ],
        (err, result )=>{
            if(err){
                console.log(`>> Erro interno sql ${err} , inserir dados na tabela comentarios`);
                return res.status(500).json({message : err });
            }
            console.log(">> Isersão na tabela comentario realizada com sucesso ");
            pool.query("INSERT INTO tempo_tarefas ( id_tarefa_fk , id_user_interacao_fk , status , prioridade) VALUES ( $1 , $2 , $3 , $4) " ,
            [ id_tarefa , id_user_comentado , status , prioridade , ] ,
            (err2 , result2) =>{
                if(err2){
                    console.log(`>> Erro interno sql ${err} inserir dados na tabela tempo_tarefas`);
                    return res.status(500).json({message : err2});
                }

                console.log(">> Inserções nas tabelas realizadas com sucesso");
                return res.json({message : "Comentario adicionado com sucesso"});


            });

    });
});


app.post("/salvarFinalTarefa",auth,async(req , res )=>{
    console.log(`_____________ edita final nas tarefas ${horario}__________`);

    const {status , id_tarefa} = req.body ;

    if(!status || !id_tarefa){
        console.log(">> Req sem passagem de parametros");
        return res.status(400).json({ message : "sem parametros" });
    }

    pool.query(`UPDATE tarefas SET status = $1 WHERE taref_id = $2 `,[status,id_tarefa],(err , result)=>{

        if(err){

            console.log(`>> Erro interno : ${err}`);
            return res.status(500).json({message : err });
        }

        console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Execução realizada com sucesso >>> "+status);
        return res.json({message : "Tarefa salva com sucesso "});

    });
});

app.get("/indicadiresTaredas",auth ,(req , res)=>{
    console.log(`_____________  indicadiresTaredas nas tarefas ${horario}__________`);

    const id_agente  = req.query.id_agente  ; 
    
    if(!id_agente){
        console.log(`>> sem parametros`);
        return res.status(400).json({message : "sem parametros"});
    }

    pool.query(`WITH all_statuses AS (
                
                SELECT 'Concluido' AS status UNION ALL
                SELECT 'Aberto' UNION ALL
                SELECT 'Validando' UNION ALL
                SELECT 'Atendendo'
                
                )
                
                SELECT 
                
                    a.status, 
                    COALESCE(COUNT(t.status), 0) AS total
                
                FROM all_statuses a
                
                LEFT JOIN tarefas t
                
                    ON a.status = t.status 
                    AND t.id_user_destino_tarefa_fk = $1
                
                GROUP BY a.status;` , 
        [id_agente] , 
        ( err , result )=>{
            if(err){
                console.log(`>> Erro interno : ${err}`);
                return res.status(500).json({ message : err });
            }
                console.log(`>> Consuta realizada com sucesso `);

            return res.json({message : result.rows });

        });
});



// ------------------------------------------------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------------------------------------------------

const multer = require('multer');

const armazenamentoFoto = multer.diskStorage({

  destination: (req, file, cb) => {

    cb ( null, './public/fotos_perfil/');
  }, 
});

const upload = multer({

  storage: armazenamentoFoto , 

  limits: { fileSize: 100000000 } , 

  fileFilter(req, file, cb) {
    if (!file.originalname.match(/\.(jpg|jpeg|png)$/)) {
      cb(new Error('Somente arquivos png , jpg ou jpeg !'));  
    } else {
      cb(null, true); 
    }
  }
});

app.post('/uploadFotos', auth, upload.single('imagem'), (req, res) => {
    console.log(`_____________ Upload de fotos ${horario}__________`);


    const { nome_foto } = req.body;

    if (!req.file) {
        
        console.log("+ Nenhum arquivo enviado");
        return res.status(400).json({ message: "Nenhum arquivo foi enviado" });
    }

    if (!nome_foto) {
       
        console.log("+ Sem parâmetros");
        return res.status(400).json({ message: "Parâmetros inválidos: 'nome_foto' é obrigatório" });
    }

    const extensao = req.file.originalname.split('.').pop();
    const finalNome = `${nome_foto}.${extensao}`;
    const caminhoAntigo = req.file.path;
    const caminhoNovo = `${req.file.destination}/${finalNome}`;


    console.log(`============ ${extensao}`);

    console.log(`============ ${finalNome}`);

    console.log(`============ ${caminhoAntigo}`);

    console.log(`============ ${caminhoAntigo}`);


    const fs = require('fs');
    fs.rename(caminhoAntigo, caminhoNovo, (err) => {
        if (err) {
            console.log(`+ Erro ao renomear o arquivo: ${err}`);
            return res.status(500).json({ message: "Erro ao salvar o arquivo com o nome especificado" });
        }


        //public/fotos_perfil/
        const caminho = `public/fotos_perfil/${nome_foto}.${extensao}` ;
        pool.query(
            ` UPDATE usuarios 
             SET foto = $1 
             WHERE id = $2 ;` , 
             [ caminho , nome_foto ] ,
             (err , restult )=>{

            if(err){
                console.log(`> Erro interno ${err} `);
                return res.status(500).jsoin({ message : err });
            }

            return res.json({ message : `Processo executado com sucesso` });

        });
        
    });
});

 


//=============================================================================
//=============================== PAINEL DE agente ============================
//=============================================================================
//=============================================================================
//=============================================================================
//=============================================================================
// ---------------- auth -----------------

 

app.post("/authAgente", async (req , res) => {

        console.log(`_____________ Login agente ${horario}__________`);

 
    const user = req.body.nome_usuario ;
    const senha =  req.body.senha;

    if(user === null || senha === null || !user || !senha ){
        console.log("> sem passagem  de parametro NO AUTH");
        return res.status(400).json({aut:false , message: 400 });
    }

    pool.query('SELECT user_id , ativo FROM acessos  WHERE nome_usuario = $1 and senha = $2 and tipo_acesso = $3',[user,senha,'agente'],(error , result) => {
    
    if(error){
        console.log('erro na auth do usuario $1'+error , [user,senha]);
        return res.status(500).json({aut:false , message:500});
    }
    
    if(result.rows.length === 0 ){
        console.log("> NO AUTH "+user+" "+senha);
         return res.json({ "auth": ""  , "user_id": 0 , "ativo": false });

    }
    console.log("> AUTH OK usuario id "+result.rows[0].user_id);

    if(result.rows[0].ativo === true ){
        try{

            const playload = {usuario : result.rows[0].user_id } ; 
            const token = jwt.sign( playload , process.env.SECRET  );

            console.log("> gerado token : "+token);
            return res.json({ "auth":token  , "user_id": result.rows[0].user_id , "ativo": result.rows[0].ativo });
  

        }catch(error){
            console.log("> Erro no servidor "+error);
            return res.json({message : 500});
        }
    }else{
            console.log(`> Usuario ${user} sem permisão de acesso `);
            // return res.status(503).json({ "auth":false   , message : 503  });
            return res.json({ "auth":""  , "user_id": result.rows[0].user_id , "ativo": result.rows[0].ativo });
    }


     });
});

app.get("/filtroTarefasAgente", auth, (req, res) => {
    console.log(`_____________ Consulta tarefas +${horario}__________`);

    const id_agente = req.query.id_agente;
    const status = req.query.status;
    const prioridade = req.query.prioridade;

    if (!id_agente || !status || !prioridade) {
        console.log(">> Parametros vazios");
        return res.status(400).json({ message: "Parâmetros vazios ou inválidos" });
    }

    let consulta = "";
    const params = [id_agente];

    if (status === 'Todos' && prioridade !== 'Todos') {
        consulta = `
            SELECT  
                 Tarefa.taref_id, 
                 Tarefa.id_user_criado_tarefa_fk, 
                 Tarefa.status, 
                 Tarefa.titulo, 
                 Tarefa.data_tarefa_criada,
                 Tarefa.tempo_estimado_fim_tarefa,
                 Tarefa.prioridade, 
                 Usuarios.nome, 
                 Usuarios.sobrenome ,
                 Tarefa.descricao             
            FROM 
                tarefas Tarefa
            INNER JOIN
                usuarios Usuarios ON Usuarios.id = Tarefa.id_user_criado_tarefa_fk
            WHERE 
                 Tarefa.id_user_destino_tarefa_fk = $1 
                 AND Tarefa.status IS NOT NULL 
                 AND Tarefa.prioridade = $2;
        `;
        params.push(prioridade);
    } 
    else if (status !== 'Todos' && prioridade === 'Todos') {
        consulta = `
            SELECT  
                 Tarefa.taref_id, 
                 Tarefa.id_user_criado_tarefa_fk, 
                 Tarefa.status, 
                 Tarefa.titulo, 
                 Tarefa.data_tarefa_criada,
                 Tarefa.tempo_estimado_fim_tarefa,
                 Tarefa.prioridade, 
                 Usuarios.nome, 
                 Usuarios.sobrenome ,
                 Tarefa.descricao         
            FROM 
                tarefas Tarefa
            INNER JOIN
                usuarios Usuarios ON Usuarios.id = Tarefa.id_user_criado_tarefa_fk
            WHERE 
                 Tarefa.id_user_destino_tarefa_fk = $1 
                 AND Tarefa.status = $2;
        `;
        params.push(status);
    } 
    else if (status !== 'Todos' && prioridade !== 'Todos') {
        consulta = `
            SELECT  
                 Tarefa.taref_id, 
                 Tarefa.id_user_criado_tarefa_fk, 
                 Tarefa.status, 
                 Tarefa.titulo, 
                 Tarefa.data_tarefa_criada,
                 Tarefa.tempo_estimado_fim_tarefa,
                 Tarefa.prioridade, 
                 Usuarios.nome, 
                 Usuarios.sobrenome ,
                 Tarefa.descricao        
            FROM 
                tarefas Tarefa
            INNER JOIN
                usuarios Usuarios ON Usuarios.id = Tarefa.id_user_criado_tarefa_fk
            WHERE 
                 Tarefa.id_user_destino_tarefa_fk = $1 
                 AND Tarefa.status = $2 
                 AND Tarefa.prioridade = $3;
        `;
        params.push(status, prioridade);
    } 
    else {
        consulta = `
            SELECT  
                 Tarefa.taref_id, 
                 Tarefa.id_user_criado_tarefa_fk, 
                 Tarefa.status, 
                 Tarefa.titulo, 
                 Tarefa.data_tarefa_criada,
                 Tarefa.tempo_estimado_fim_tarefa,
                 Tarefa.prioridade, 
                 Usuarios.nome, 
                 Usuarios.sobrenome    ,
                 Tarefa.descricao        
            FROM 
                tarefas Tarefa
            INNER JOIN
                usuarios Usuarios ON Usuarios.id = Tarefa.id_user_criado_tarefa_fk
            WHERE 
                 Tarefa.id_user_destino_tarefa_fk = $1;
        `;
    }

    pool.query(consulta, params, (err, result) => {
        if (err) {
            console.log(`>> Erro intero ${err}`);
            return res.status(500).json({ message: `Erro interno: ${err}` });
        }

        if (result.rows.length === 0) {
            console.log(`>> Sem consulta`);            
            return res.status(404).json({ message: "" });
        }
            console.log(`>> Consulta realizada com sucesso`);
        return res.json({ message: result.rows });
    });
});

app.get("/tarefasUnica",auth,(req , res )=>{



        console.log(`_____________ Consulta numero tarefa ${horario}__________`);

     // const id = req.query.id ;
     const tarefa = req.query.tarefa ; 

     if(!tarefa){
        console.log("err sem parametros");
        return res.status(404).json({message : ""});
     }

    pool.query( `
            SELECT  
                 Tarefa.taref_id, 
                 Tarefa.id_user_criado_tarefa_fk, 
                 Tarefa.status, 
                 Tarefa.titulo, 
                 Tarefa.data_tarefa_criada,
                 Tarefa.tempo_estimado_fim_tarefa,
                 Tarefa.prioridade, 
                 Usuarios.nome, 
                 Usuarios.sobrenome ,
                 Tarefa.descricao        
            FROM 
                tarefas Tarefa
            INNER JOIN
                usuarios Usuarios ON Usuarios.id = Tarefa.id_user_criado_tarefa_fk
            WHERE 
                 Tarefa.taref_id = $1 
                 
        `

     , [tarefa] , (err , result )=>{

        if(err){
            console.log(`>> erro acesso ${err}`);
            return status(500).res.json({message : `erro interno ${err} `});
        }

        if(result.rows.length == 0 ){

            console.log('>> dados sem retorno');
            return status(400).res.json({message : "" });

        }

        return res.json({message : result.rows[0]});
     });
});

app.get("/dadosAgenteLogado" , auth , (req , res )=>{

    const id_agente = req.query.id_agente ;

    pool.query(`SELECT nome , sobrenome , telefone , cargo , email  FROM usuarios WHERE id = $1` , [id_agente] , (err , result )=>{
        if(err){
            return res.json({ message : "erro "+err});
        }
        return res.json({message : result.rows[0]});
    });

});


// ------------------------------------------------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------------------------------------------------

server.listen(3000  , '192.168.0.114' ,  ()=>{
	console.log("porta sendo lista");
}).on( 'error' , (error)=>{
	console.log('error ',error);
});


// ----------------------------------


