const {Pool} = require('pg');

const pool = new Pool({

	user: 'postgres',
	host: 'localhost',
	database: 'tarefasdefinidas',
	password:'adm',
	port:5433,


});

module.exports = pool ;
