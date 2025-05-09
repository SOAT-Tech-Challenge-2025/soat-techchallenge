-- Tabela de Clientes
CREATE TABLE tb_cliente (
    id SERIAL PRIMARY KEY,
    nm_cliente VARCHAR(100),
    ds_email VARCHAR(100),
    nr_documento VARCHAR(20),
    dt_inclusao TIMESTAMP,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Categorias
CREATE TABLE tb_categoria_itens (
    id SERIAL PRIMARY KEY,
    nm_categoria VARCHAR(100),
    dt_inclusao TIMESTAMP,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Produtos
CREATE TABLE tb_produto (
    id SERIAL PRIMARY KEY,
    nm_produto VARCHAR(100),
    id_categoria INTEGER REFERENCES tb_categoria_itens(id),
    vl_unitario_produto NUMERIC(10, 2),
    tempo_de_preparo INTEGER, -- tempo em minutos
    dt_inclusao TIMESTAMP,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Carrinho de Pedido
CREATE TABLE tb_carrinho_pedido (
    id SERIAL PRIMARY KEY,
    vl_total_pedido NUMERIC(10, 2),
    minutagem INTEGER, -- pode ser o tempo total estimado
    id_cliente INTEGER REFERENCES tb_cliente(id),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de relação entre Carrinho e Produto
CREATE TABLE tb_carrinho_produto (
    id SERIAL PRIMARY KEY,
    id_produto INTEGER REFERENCES tb_produto(id),
    qt_item INTEGER,
    vl_qt_item NUMERIC(10, 2),
    FOREIGN KEY (id) REFERENCES tb_carrinho_pedido(id)
);

-- Tabela de Pagamentos
CREATE TABLE tb_pagamento (
   id SERIAL PRIMARY KEY,
   id_externo VARCHAR(100) UNIQUE,
   st_pagamento VARCHAR(50),
   vl_total_pedido NUMERIC(10, 2),
   codigo_qr TEXT,
   expiracao TIMESTAMP,
   dt_inclusao TIMESTAMP,
   timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Preparação
CREATE TABLE tb_preparacao (
    id SERIAL PRIMARY KEY,
    posicao_preparacao INTEGER,
    estimativa_de_pronto TIMESTAMP,
    tempo_de_preparacao INTEGER,
    st_preparacao VARCHAR(50),
    dt_inclusao TIMESTAMP,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);