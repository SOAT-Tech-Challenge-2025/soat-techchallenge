-- Tabela de Clientes
CREATE TABLE tb_cliente (
    id UUID PRIMARY KEY,
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
    dt_inclusao DATE,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Produtos
CREATE TABLE tb_produto (
    id SERIAL PRIMARY KEY,
    nm_produto VARCHAR(100),
    id_categoria INTEGER REFERENCES tb_categoria_itens(id),
    vl_unitario_produto NUMERIC(10, 2),
    tempo_de_preparo INTEGER, -- tempo em minutos
    dt_inclusao DATE,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Carrinho de Pedido
CREATE TABLE tb_carrinho_pedido (
    id VARCHAR(255) PRIMARY KEY,
    vl_total_pedido NUMERIC(10, 2),
    minutagem INTEGER,
    id_cliente VARCHAR(255),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de relação entre Carrinho e Produto
CREATE TABLE tb_carrinho_produto (
    id VARCHAR(255),
    id_produto INTEGER,
    qt_item INTEGER,
    vl_qt_item NUMERIC(10, 2),
    PRIMARY KEY (id, id_produto),
    FOREIGN KEY (id) REFERENCES tb_carrinho_pedido(id),
    FOREIGN KEY (id_produto) REFERENCES tb_produto(id)
);

-- Tabela de Pagamentos
CREATE TABLE tb_pagamento (
    id VARCHAR(255) PRIMARY KEY,
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
    id VARCHAR(255) PRIMARY KEY,
    posicao_preparacao INTEGER,
    estimativa_de_pronto TIMESTAMP,
    tempo_de_preparacao INTEGER,
    st_preparacao VARCHAR(50),
    dt_inclusao TIMESTAMP,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_gera_pedido (
    id VARCHAR(4) PRIMARY KEY,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION generate_order_id() RETURNS VARCHAR AS $$
   DECLARE
seq INTEGER;
       prefix CHAR;
       new_id VARCHAR;
BEGIN
SELECT CAST(substring(id from '[0-9]+$') AS INTEGER)
INTO seq
FROM tb_gera_pedido
ORDER BY id DESC LIMIT 1;

SELECT substring(id from '[A-Z]')
INTO prefix
FROM tb_gera_pedido
ORDER BY id DESC LIMIT 1;

IF seq IS NULL THEN
           seq := 1;
           prefix := 'A';
       ELSEIF seq < 999 THEN
           seq := seq + 1;
ELSE
           seq := 1;
           prefix := chr(ascii(prefix) + 1);
END IF;

       new_id := prefix || lpad(seq::text, 3, '0');

       -- Insert the new id into the table
INSERT INTO tb_gera_pedido(id /*, other fields*/) VALUES (new_id /*, values*/);

RETURN new_id;
END;
   $$ LANGUAGE plpgsql;