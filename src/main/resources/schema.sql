CREATE SEQUENCE cliente_id_seq;
CREATE TABLE clientes (
   id varchar(4) NOT NULL DEFAULT lpad(nextval('cliente_id_seq')::varchar, 4, '0'),
   nombre VARCHAR (50),
   edad INT NOT NULL,
   salario NUMERIC(6, 2)
);

