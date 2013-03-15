------ HISTORICO DE MANUTENCAO DA ARQUIVO DE SCRIPT DE CRIACAO DA BASES ---------

--###########################################
-- DATA: 02/02/2013						    #
-- SCRIPTS DE CRIACAO DE BASE DE DADOS      #
-- AUTOR: BICCIO                            #
--###########################################

--###########################################
-- DATA: 20/02/2013						    #
-- MANUTENCAO: PADRONIZACAO DE CAMPOS       #
-- AUTOR: CARIOCA (BY BICCIO)               #
--###########################################

------------------------------------------------------------------------------------------------

--######################################################################
--#                        TABELAS CODEDATA                            #
--######################################################################

--CRIACAO DA TABELA DE TIPO DE USUARIO
--ESTA TABELA INFORMA QUAL E O TIPO DO USUARIO CADASTRADO DO SISTEMA
--NOME DO TIPO E DESCRICAO DO TIPO NAO PODEM SER BRANCOS
CREATE TABLE tipo_usuario(
       id_tipo_usuario INT NOT NULL,
       nm_tipo_usuario VARCHAR(100) NOT NULL,
       desc_tipo_usuario VARCHAR(200) NOT NULL,
       CONSTRAINT id_tipo_usuario_pk PRIMARY KEY(id_tipo_usuario),
       CONSTRAINT nm_tipo_usuario_check_size CHECK (CHAR_LENGTH(nm_tipo_usuario) > 0),
       CONSTRAINT desc_tipo_usuario_check_size CHECK (CHAR_LENGTH(desc_tipo_usuario) > 0)
);

--CRIACAO DA TABELA DE TIPO DE STATUS
--ESSA TABELA INFORMA QUAL E O TIPO DE STATUS
--NOME DO TIPO E DESCRICAO DO TIPO NAO PODEM SER BRANCOS
CREATE TABLE status_usuario(
	id_status_usuario INT NOT NULL,
	nm_status_usuario VARCHAR(100) NOT NULL,
    desc_status_usuario VARCHAR(200) NOT NULL,
    CONSTRAINT id_status_pk PRIMARY KEY(id_status_usuario),
    CONSTRAINT nm_status_check_size CHECK (CHAR_LENGTH(nm_status_usuario) > 0),
    CONSTRAINT desc_status_check_size CHECK (CHAR_LENGTH(desc_status_usuario) > 0)
);

--CRIACAO DA TABELA DE CANAL
--ESSA TABELA ARMAZENA OS TIPOS DE CANAIS QUE SERAO TRATADOS PELO SISTEMA
--ESSA TABELA E UM EXEMPLO DE TABELA CODEDATA
--NOME E DESCRICAO DO CANAL NAO PODE SER EM BRANCO
CREATE TABLE canal(
	id_canal INT NOT NULL,
	nm_canal VARCHAR(100) NOT NULL,
	desc_canal VARCHAR(200) NOT NULL,
	CONSTRAINT id_canal_pk PRIMARY KEY (id_canal),
	CONSTRAINT nm_canal_check_size CHECK(CHAR_LENGTH(nm_canal) > 0),
	CONSTRAINT desc_canal_check_size CHECK (CHAR_LENGTH(desc_canal) > 0)
);

--CRIACAO DA TABELA DE TIPO DE CULINARIA
--ESSA TABELA ARMAZENA OS TIPOS DE CULINARIA QUE O SISTEMA TRATARA
--ESSA TABELA E UM EXEMPLO DE CODEDATA
--NOME E DESCRICAO DO TIPO DE CULINARIA NAO PODE SER EM BRANCO
CREATE TABLE tipo_culinaria(
	id_tipo_culinaria INT NOT NULL,
	nm_tipo_culinaria VARCHAR(100) NOT NULL,
	desc_tipo_culinaria VARCHAR(200) NOT NULL,
	CONSTRAINT id_tipo_culinaria_pk PRIMARY KEY(id_tipo_culinaria),
	CONSTRAINT nm_tipo_culinaria_check_size CHECK(CHAR_LENGTH(nm_tipo_culinaria) > 0),
	CONSTRAINT desc_tipo_culinaria_check_size CHECK(CHAR_LENGTH(desc_tipo_culinaria) > 0)
);

--CRIACAO DA TABELA DE TIPO DE STATUS DE RESTAURANTE
--ESSA TABELA INFORMA QUAL E O TIPO DE STATUS DO RESTAURANTE
--NOME DO TIPO E DESCRICAO DO TIPO NAO PODEM SER BRANCOS
CREATE TABLE status_restaurante(
	id_status_restaurante INT NOT NULL,
	nm_status_restaurante VARCHAR(100) NOT NULL,
    desc_status_restaurante VARCHAR(200) NOT NULL,
    CONSTRAINT id_status_restaurante_pk PRIMARY KEY(id_status_restaurante),
    CONSTRAINT nm_status_restaurante_check_size CHECK (CHAR_LENGTH(nm_status_restaurante) > 0),
    CONSTRAINT desc_status_restaurante_check_size CHECK (CHAR_LENGTH(desc_status_restaurante) > 0)
);

--CRIACAO DA TABELA DE EVENTOS
--ESSA TABELA ARMAZENARA OS TIPOS DE EVENTOS DO SISTEMA, COMO POR EXEMPLO, ALTERACAO DO CADASTRO, INCLUSAO DE USUARIO ...ETC.
--ID EVENTO E A CHAVE DA BASE
--NOME E DESCRICAO NAO PODEM SER EM BRANCOS OU NULOS
CREATE TABLE evento(
	id_evento INT NOT NULL,
	nm_evento VARCHAR(100) NOT NULL,
	desc_evento VARCHAR(200) NOT NULL,
	CONSTRAINT id_evento_pk PRIMARY KEY(id_evento),
	CONSTRAINT nm_evento_size CHECK (CHAR_LENGTH(nm_evento) > 0),
	CONSTRAINT desc_evento_size CHECK (CHAR_LENGTH(desc_evento) > 0)
);

--######################################################################
--#                        TABELAS NORMAIS                             #
--######################################################################

--CRIACAO DA TABELA DE PAISES
--A SIGLA DO PAIS ESTA DEFINIDA COMO UNICA
--A SIGLA DO PAIS TEM QUE TER TAMANHO IGUAL A 3
--A DESCRICAO DO PAIS NAO PODE SER EM BRANCO
CREATE TABLE pais(
	id_pais INT NOT NULL,
	sigla_pais CHAR(3) NOT NULL,
	nm_pais VARCHAR(50) NOT NULL,
	CONSTRAINT pais_pk PRIMARY KEY(id_pais),
	CONSTRAINT sigla_pais_unique UNIQUE(sigla_pais),
	CONSTRAINT sigla_pais_check_size CHECK (CHAR_LENGTH(sigla_pais) = 3),
	CONSTRAINT nm_pais_check_size CHECK (CHAR_LENGTH(nm_pais) > 0)
);

--CRIACAO DA TABELA DE ESTADOS
--FILHA DA TABELA DE PAES
--A SIGLA DO ESTADO ESTA DEFINIDA COMO UNICA
--A SIGLA DO ESTADO TEM QUE TER TAMANHO DE 2
--A DESCRICAO DO ESTADO NAO PODE SER BRANCA
CREATE TABLE estado(
	id_estado INT NOT NULL,
	sigla_estado CHAR(2) NOT NULL,
	nm_estado VARCHAR(50) NOT NULL,
	id_pais INT NOT NULL,
	CONSTRAINT estado_pk PRIMARY KEY(id_estado),
	CONSTRAINT sigla_estado_unique UNIQUE(sigla_estado),
	CONSTRAINT pais_fk FOREIGN KEY(id_pais) REFERENCES pais(id_pais),
	CONSTRAINT sigla_estado_check_size CHECK (CHAR_LENGTH(sigla_estado) = 2),
	CONSTRAINT nm_estado_check_size CHECK (CHAR_LENGTH(nm_estado) > 0)
);

--CRIACAO DA TABELA DE CIDADES
--FILHA DA TABELA DE PAIS
--FILHA DA TABELA DE ESTADO
--A DESCRICAO DA CIDADE NAO PODE SER BRANCO
CREATE TABLE cidade(
	id_cidade INT NOT NULL,
	nm_cidade VARCHAR(50) NOT NULL,
	id_pais INT NOT NULL,
	id_estado INT NOT NULL,
	CONSTRAINT cidade_pk PRIMARY KEY(id_cidade),
	CONSTRAINT pais_fk FOREIGN KEY(id_pais) REFERENCES pais(id_pais),
	CONSTRAINT estado_fk FOREIGN KEY(id_estado) REFERENCES estado(id_estado),
	CONSTRAINT nm_cidade_check_size CHECK (CHAR_LENGTH(nm_cidade) > 0)
);

--CRIACAO DA TABELA DE USUARIO
--EMAIL E VALIDADO A PARTIR DE UMA EXPRESSAO REGULAR : http://pgdocptbr.sourceforge.net/pg80/functions-matching.html#FUNCTIONS-POSIX-REGEXP
--EMAIL DEVE SER UNICO
--TIPO DE USUARIO E CHAVE ESTRANGEIRA DA TABELA DE TIPO DE USUARIO
--STATUS DO USUARIO E CHAVE ESTRANGEIRA DA TABELA DE STATUS DO USUARIO
CREATE TABLE usuario(
       id_usuario SERIAL NOT NULL,
       email_usuario VARCHAR(100) NOT NULL,
       id_tipo_usuario INT NOT NULL,
       id_status_usuario INT NOT NULL,
       dt_inclusao_usuario TIMESTAMP,
       dt_ultima_alteracao_usuario TIMESTAMP,
       dt_ultima_alteracao_senha_usuario TIMESTAMP,
       flag_solicitacao_alteracao_senha_usuario BOOLEAN,
       CONSTRAINT usuario_pk PRIMARY KEY(id_usuario),
       CONSTRAINT email_check_format CHECK (email_usuario ~ '^(.+)@(.+)[.]([a-z]+)$'),
       CONSTRAINT email_unique UNIQUE (email_usuario),
       CONSTRAINT email_check_size CHECK (CHAR_LENGTH(email_usuario) <= 100),
       CONSTRAINT id_tipo_usuario_fk FOREIGN KEY(id_tipo_usuario) REFERENCES tipo_usuario(id_tipo_usuario),
       CONSTRAINT id_status_fk FOREIGN KEY(id_status_usuario) REFERENCES status_usuario(id_status_usuario)
);

--CRIACAO DA TABELA DE HISTORICO DO USUARIO
--AS CHAVES PARA A ESSE TABELA SERAO DATA DA HISTORICO (TIMESTAMP) E ID DO USUARIO
--EMAIL TEM QUE SEGUIR UM PADRAO DE EMAIL,
--ID STATUS USUARIO FAZ REFERENCIA COM A TABELA DE STATUS DE USUARIO
--ID TIPO DE USUARIO FAZ REFERENCIA COM A TABELA DE TIPO DE USUARIO
--ID TIPO DE EVENTO. FAZ REFERENCIA COM A TABELA DE EVENTOS
CREATE TABLE usuario_historico(
       id_usuario INT NOT NULL,
       dt_historico TIMESTAMP NOT NULL,
       email_usuario VARCHAR(100) NOT NULL,
       id_tipo_usuario INT NOT NULL,
       id_status_usuario INT NOT NULL,
       dt_inclusao_usuario TIMESTAMP,
       dt_ultima_alteracao_usuario TIMESTAMP,
       dt_ultima_alteracao_senha_usuario TIMESTAMP,
       flag_solicitacao_alteracao_senha_usuario BOOLEAN,
       id_evento INT NOT NULL,
       CONSTRAINT usuario_historico_pk PRIMARY KEY(id_usuario,dt_historico),
       CONSTRAINT email_historico_check_format CHECK (email_usuario ~ '^(.+)@(.+)[.]([a-z]+)$'),
       CONSTRAINT email_historico_check_size CHECK (CHAR_LENGTH(email_usuario) <= 100),
       CONSTRAINT id_tipo_usuario_historico_fk FOREIGN KEY(id_tipo_usuario) REFERENCES tipo_usuario(id_tipo_usuario),
       CONSTRAINT id_status_historico_fk FOREIGN KEY(id_status_usuario) REFERENCES status_usuario(id_status_usuario),
       CONSTRAINT id_evento_historico_fk FOREIGN KEY(id_evento) REFERENCES evento(id_evento)
);
 
--CRIACAO DA TABELA DE ENDERECO DO USUARIO
--ESSA TABELA E FILHA DA TABELA DE USUARIO. PARA EXISTIR UM ENDERECO, PRECISA EXISTIR UM USUARIO
--TEM CHAVES ESTRANGEIRAS QUE FAZEM RELACIONAMENTO COM O PAIS, ESTADO E CIDADE.
CREATE TABLE endereco_usuario(
       id_usuario INT NOT NULL REFERENCES usuario(id_usuario),
       CEP_prefixo CHAR(5) NOT NULL,
       CEP_sulfixo CHAR(3) NOT NULL,
       logradouro VARCHAR(100) NOT NULL,
       numero VARCHAR(20) ,
       bairro VARCHAR(80) NOT NULL,
       id_pais INT NOT NULL,
       id_estado INT NOT NULL,
       id_cidade INT NOT NULL,
       CONSTRAINT usuario_endereco_pk PRIMARY KEY(id_usuario),
       CONSTRAINT id_pais_fk FOREIGN KEY(id_pais) REFERENCES pais(id_pais),
       CONSTRAINT id_estado_fk FOREIGN KEY(id_estado) REFERENCES estado(id_estado),
       CONSTRAINT id_cidade_fk FOREIGN KEY(id_cidade) REFERENCES cidade(id_cidade),
       CONSTRAINT cep_prefixo_check_size CHECK(CHAR_LENGTH(CEP_prefixo) = 5),
       CONSTRAINT cep_sulfixo_check_size CHECK(CHAR_LENGTH(CEP_sulfixo) = 3),
       CONSTRAINT logradouro_check_size CHECK(CHAR_LENGTH(logradouro) > 0),
       CONSTRAINT bairro_check_size CHECK(CHAR_LENGTH(bairro) > 0)
);

--CRIACAO DA TABELA DE DADOS BASICO DO CONSUMIDOR
--PARA ESSA TABELA EXISTIR, PRECISA EXISTIR UM USUARIO (ENTIDADE FRACA DA TABELA USUARIO)
--CHAVE PRIMARIA FAZ REFERENCIA A TABELA DE USUARIO
--SEXO TEM QUE TER TAMANHO DE UMA POSICAO
CREATE TABLE dados_basico_consumidor(
	id_usuario INT NOT NULL REFERENCES usuario(id_usuario),
	nome_usuario VARCHAR(100),
	sexo CHAR(1),
	flag_interesse_restaurante_favorito BOOLEAN,
	flag_interesse_info_promocao BOOLEAN,
	flag_interesse_info_dishup BOOLEAN,
	CONSTRAINT usuario_dado_basico_pk PRIMARY KEY(id_usuario),
	CONSTRAINT sexo_check_size CHECK (CHAR_LENGTH(sexo) = 1)
);

--CRIACAO DA TABELA DE SENHA DO USUARIO
--PARA ESSA TABELA EXISTIR, PRECISA EXISTIR UM USUARIO (ENTIDADE FRACA DA TABELA USUARIO)
--CHAVE PRIMARIA FAZ REFERENCIA A TABELA DE USUARIOS
--SENHA NAO PODE SER EM BRANCO
CREATE TABLE autenticacao_usuario(
	id_usuario INT NOT NULL REFERENCES usuario(id_usuario),
	assinatura_usuario VARCHAR(200) NOT NULL,
	CONSTRAINT autenticacao_usuario_pk PRIMARY KEY(id_usuario),
	CONSTRAINT assinatura_check_size CHECK (CHAR_LENGTH(assinatura_usuario) > 0)
);

--CRIACAO DA TABELA DE LOG DE AUTENTICACAO
--TODO LOGIN SERA ARMAZENADO PARA ESTATISTICA DO SITE
--ID DO USUARIO E TIMESTAMP DA AUTENTICACAO SERAO CHAVES PRIMARIAS DA TABELA
--TIPO DE CANAL FAZ REFERENCIA A TABELA DE CANAL
CREATE TABLE log_autenticacao(
	id_usuario INT NOT NULL REFERENCES usuario(id_usuario),
	dt_autenticacao TIMESTAMP NOT NULL,
	id_canal INT NOT NULL,
	CONSTRAINT usuario_autenticacao_pk PRIMARY KEY (id_usuario, dt_autenticacao),
	CONSTRAINT id_canal_fk FOREIGN KEY (id_canal) REFERENCES canal(id_canal)
);

--CRIACAO DA TABELA DE CULINARIA DE INTERESSE DE DETERMINADO USUARIO
--TABELA DE RELACIONAMENTO ENTRE USUARIO E TIPO DE CULINARIA (RELACIONAMENTO N X M)
CREATE TABLE culinaria_interesse_consumidor(
	id_usuario INT NOT NULL REFERENCES usuario(id_usuario),
	id_tipo_culinaria INT NOT NULL REFERENCES tipo_culinaria(id_tipo_culinaria),
	CONSTRAINT culinaria_interesse_usuario_pk PRIMARY KEY (id_usuario, id_tipo_culinaria)
);

--CRIACAO DA TABELA DE RESTAURANTES
--ID DO RESTAURANTE E A CHAVE PRIMARIA DA TABELA
--CNPJ/FILIAL/CONTROLE SAO UNICOS DENTRO DO SISTEMA
--RAZAO SOCIAL NAO DEVE SER BRANCO
--DDD TELEFONE TEM QUE TER 3 POSICOES
--TELEFONE TEM QUE TER NO MINIMO 8 POSICOES
--STATUS RESTAURANTE FAZ RELACIONAMENTO COM A TABELA STATUS RESTAURANTE
CREATE TABLE restaurante(
	id_restaurante SERIAL NOT NULL,
	CNPJ_numero CHAR(8) NOT NULL,
	CNPJ_filial CHAR(4) NOT NULL,
	CNPJ_controle CHAR(2) NOT NULL,
	razao_social VARCHAR(100) NOT NULL,
	site VARCHAR(100),
	DDD_telefone CHAR(3) NOT NULL,
	telefone CHAR(9) NOT NULL,
	dt_inclusao TIMESTAMP,
	dt_ultima_alteracao TIMESTAMP,
	dt_ativacao TIMESTAMP,
	flag_interesse_info_dishup BOOLEAN,
	id_status_restaurante INT NOT NULL,
	CONSTRAINT restaurante_pk PRIMARY KEY(id_restaurante),
	CONSTRAINT cnpj_unique UNIQUE (CNPJ_numero, CNPJ_filial, CNPJ_controle),
	CONSTRAINT razao_social_size CHECK(CHAR_LENGTH(razao_social) > 0),
	CONSTRAINT ddd_telefone_size CHECK(CHAR_LENGTH(DDD_telefone) = 3),
	CONSTRAINT telefone_size CHECK(CHAR_LENGTH(telefone) >= 8),
	CONSTRAINT status_restaurante_fk FOREIGN KEY(id_status_restaurante) REFERENCES status_restaurante(id_status_restaurante) 
);

--CRIACAO DA TABELA DE ENDERECO DO RESTAURANTE
--ESSA TABELA E FILHA DA TABELA DE RESTAURANTE. PARA EXISTIR UM ENDERECO, PRECISA EXISTIR UM RESTAURANTE
--TEM CHAVES ESTRANGEIRAS QUE FAZEM RELACIONAMENTO COM O PAIS, ESTADO E CIDADE.
--ESSA TABELA POSSIBILITA UM RESTAURANTE TER MAIS DO QUE UM ENDERECO (NO CASOD E UMA REDE DE RESTAURANTES)
CREATE TABLE endereco_restaurante(
       id_restaurante INT NOT NULL REFERENCES restaurante(id_restaurante),
       id_endereco_restaurante INT NOT NULL,
       CEP_prefixo CHAR(5) NOT NULL,
       CEP_sulfixo CHAR(3) NOT NULL,
       logradouro VARCHAR(100) NOT NULL,
       numero VARCHAR(20) ,
       bairro VARCHAR(80) NOT NULL,
       id_pais INT NOT NULL,
       id_estado INT NOT NULL,
       id_cidade INT NOT NULL,
       CONSTRAINT restaurante_endereco_pk PRIMARY KEY(id_restaurante, id_endereco_restaurante),
       CONSTRAINT id_pais_fk FOREIGN KEY(id_pais) REFERENCES pais(id_pais),
       CONSTRAINT id_estado_fk FOREIGN KEY(id_estado) REFERENCES estado(id_estado),
       CONSTRAINT id_cidade_fk FOREIGN KEY(id_cidade) REFERENCES cidade(id_cidade),
       CONSTRAINT cep_prefixo_check_size CHECK(CHAR_LENGTH(CEP_prefixo) = 5),
       CONSTRAINT cep_sulfixo_check_size CHECK(CHAR_LENGTH(CEP_sulfixo) = 3),
       CONSTRAINT logradouro_check_size CHECK(CHAR_LENGTH(logradouro) > 0),
       CONSTRAINT bairro_check_size CHECK(CHAR_LENGTH(bairro) > 0)
);

--CRIACAO DA TABELA DE RESTAURANTES FAVORITOS DE DETERMINADO USUARIO
--TABELA DE RELACIONAMENTO N X M
--ID DO USUARIO E ID DO RESTAURANTE SAO CHAVES PARA ESSA TABELA
CREATE TABLE restaurante_favorito_consumidor(
	id_usuario INT NOT NULL REFERENCES usuario(id_usuario),
	id_restaurante INT NOT NULL REFERENCES restaurante(id_restaurante),
	CONSTRAINT restaurante_favorito_usuario_pk PRIMARY KEY(id_usuario,id_restaurante)
);

--CRIACAO DA TABELA DE GRUPO DE USUARIOS POR RESTAURANTE
--ESSA TABELA INFORMA QUAIS SAO OS USUARIOS QUE PODEM DAR MANUTENCAO NOS DADOS DO RESTAURANTE
--UM GRUPO PODE TER MAIS DO QUE UM RESTAURANTE E UM RESTAURANTE PODE TER DIVERSOS USUARIOS, PELO MENOS UM SENDO MASTER
--UM RESTAURANTE SÓ EXISTE EM UM UNICO GRUPO
CREATE TABLE grupo_restaurante_usuario(
	id_grupo INT NOT NULL,
	id_restaurante INT NOT NULL REFERENCES restaurante(id_restaurante),
	id_usuario INT NOT NULL REFERENCES usuario(id_usuario),
	CONSTRAINT grupo_pk PRIMARY KEY(id_grupo,id_usuario,id_restaurante)
);

--CRIACAO DA TABELA DE TIPO DE CULINARIA QUE DETERMINADO RESTAURANTE TRABALHA
--ESSA TABELA E UMA TABELA N X M (ASSOCIATIVA)
CREATE TABLE tipo_culinaria_restaurante(
	id_restaurante INT NOT NULL REFERENCES restaurante(id_restaurante),
	id_tipo_culinaria INT NOT NULL REFERENCES tipo_culinaria(id_tipo_culinaria),
	CONSTRAINT tipo_culinaria_restaurante_pk PRIMARY KEY (id_restaurante,id_tipo_culinaria)
);
