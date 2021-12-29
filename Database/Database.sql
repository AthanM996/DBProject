-- SCHEMA: public

CREATE SCHEMA IF NOT EXISTS public;


DROP TABLE IF EXISTS public.Shopping_center CASCADE;

CREATE TABLE public.Shopping_center
(
    id integer NOT NULL,
    name character varying(255),
    address character varying(255),
    CONSTRAINT Shopping_center_pkey PRIMARY KEY (id)
);


DROP TABLE IF EXISTS public.Company CASCADE;

CREATE TABLE public.Company
(
    id integer NOT NULL,
    name character varying(255),
    address character varying(255),
    contact_person character varying(100),
    contact_email character varying(100),
    contact_phone character varying(15),
    contact_mobile character varying(15),
    CONSTRAINT Company_pkey PRIMARY KEY (id)
);


DROP TABLE IF EXISTS public.Contract CASCADE;

CREATE TABLE public.Contract
(
    id integer NOT NULL,
    date_signed character varying(10),
    date_active_from character varying(10),
    date_active_to character varying(10),
    company_id integer,
    billing_units character varying(25),
    CONSTRAINT Contract_pkey PRIMARY KEY (id),
    CONSTRAINT Contract_fkey_Company_id FOREIGN KEY (company_id)
        REFERENCES public.Company (id)
        ON UPDATE NO ACTION
        ON DELETE CASCADE, -- Na to ksanakoitaksw
	CONSTRAINT Contract_check_billingUnits CHECK (billing_units IN ('weekly', 'monthly', 'yearly'))
);


DROP TABLE IF EXISTS public.Invoice;

CREATE TABLE public.Invoice
(
    id integer NOT NULL,
    contract_id integer,
    issued_by_id integer,
    invoice_amount double precision,
    fee double precision,
    tax double precision,
    total_amount double precision,
    time_created timestamp without time zone,
    date_issued character varying(10),
    date_paid character varying(10),
    CONSTRAINT Invoice_pkey PRIMARY KEY (id),
    CONSTRAINT Invoice_fkey_Company_id FOREIGN KEY (issued_by_id)
        REFERENCES public.Company (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT Invoice_fkey_Contract_id FOREIGN KEY (contract_id)
        REFERENCES public.Contract (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


DROP TABLE IF EXISTS public.Shop;

CREATE TABLE public.Shop
(
    id integer NOT NULL,
    shop_name character varying(255),
    shopping_center_id integer,
    floor integer,
    location character varying(255),
    active_from character varying(10),
    active_to character varying(10),
    active boolean,
    contract_id integer,
	service_type character varying(100),
    CONSTRAINT Shop_pkey PRIMARY KEY (id),
    CONSTRAINT Shop_fkey_Contract_id FOREIGN KEY (contract_id)
        REFERENCES public.Contract (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT Shop_fkey_ShoppingCenter_id FOREIGN KEY (shopping_center_id)
        REFERENCES public.Shopping_center (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
	CONSTRAINT Shop_check_serviceType CHECK (service_type IN ('food or drink', 'entertaintment', 'retail')) -- PROSWRINO!!!
);


-- Functions
DROP FUNCTION IF EXISTS all_malls;
DROP FUNCTION IF EXISTS insert_mall;
DROP FUNCTION IF EXISTS show_stores_of_mall;
DROP FUNCTION IF EXISTS delete_mall;
DROP FUNCTION IF EXISTS edit_mall;
DROP FUNCTION IF EXISTS get_mall_id_name;

DROP FUNCTION IF EXISTS all_stores;
DROP FUNCTION IF EXISTS insert_store;
DROP FUNCTION IF EXISTS delete_store;
DROP FUNCTION IF EXISTS edit_store;

DROP FUNCTION IF EXISTS insert_aggreement;
DROP FUNCTION IF EXISTS delete_aggreement;
DROP FUNCTION IF EXISTS edit_aggreement;
DROP FUNCTION IF EXISTS all_aggreements;

DROP FUNCTION IF EXISTS all_firms;
DROP FUNCTION IF EXISTS insert_firm;
DROP FUNCTION IF EXISTS delete_firm;
DROP FUNCTION IF EXISTS edit_firm;
DROP FUNCTION IF EXISTS get_firm_id_name;

-- Shopping_center
CREATE FUNCTION all_malls() RETURNS SETOF text AS 
$$ 
SELECT CONCAT_WS(',', id, name, address) 
FROM Shopping_center; 
$$ 
LANGUAGE SQL;

CREATE FUNCTION insert_mall(integer, character varying, character varying) RETURNS void AS
$$
INSERT INTO Shopping_center(id, name, address) 
VALUES ($1, $2, $3);
$$
LANGUAGE SQL;

CREATE FUNCTION show_stores_of_mall(integer) RETURNS text AS
$$
SELECT CONCAT_WS(',', shop.id, shop.shop_name, shop.shopping_center_id, shop.floor, 
				 shop.location, shop.active_from, shop.active_to, shop.active, shop.contract_id, 
				 shop.service_type) 
				 FROM shop 
				 INNER JOIN shopping_center sc 
				 ON (shop.shopping_center_id = sc.id) 
				 WHERE shop.shopping_center_id = sc.id AND sc.id = $1;
$$ 
LANGUAGE SQL;

CREATE FUNCTION get_mall_id_name() RETURNS SETOF text AS
$$
SELECT CONCAT_WS(',', id, name) FROM shopping_center;
$$
LANGUAGE SQL;

CREATE FUNCTION delete_mall(integer) RETURNS void AS
$$
DELETE FROM shopping_center * WHERE id = $1;
$$ 
LANGUAGE SQL;

CREATE FUNCTION edit_mall(integer, character varying, character varying) RETURNS void AS 
$$
UPDATE shopping_center SET id = $1, name = $2, address = $3 WHERE id = $1;
$$
LANGUAGE SQL;

-- Shop

CREATE FUNCTION insert_store(integer, character varying, integer, 
							 integer, character varying, character varying, 
							 character varying, boolean, integer, character varying) 
RETURNS void AS 
$$
INSERT INTO Shop(id, shop_name, shopping_center_id, floor, location, 
				 active_from, active_to, active, contract_id, service_type)
VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10);
$$
LANGUAGE SQL;

CREATE FUNCTION all_stores() RETURNS SETOF text AS 
$$ 
SELECT CONCAT_WS(',', id, shop_name, shopping_center_id, floor, 
				 location, active_from, active_to, active, contract_id, 
				 service_type) 
				 FROM Shop; 
$$ 
LANGUAGE SQL;

CREATE FUNCTION delete_store(integer) RETURNS void AS 
$$
DELETE FROM shop * WHERE id = $1;
$$
LANGUAGE SQL;

CREATE FUNCTION edit_store(integer, character varying, integer, 
						   integer, character varying, character varying, 
						   character varying, boolean, integer, character varying)
RETURNS void AS 
$$
UPDATE shop SET id = $1, shop_name = $2, shopping_center_id = $3, floor = $4, location = $5, 
				active_from = $6, active_to = $7, active = $8, contract_id = $9, service_type = $10;
$$
LANGUAGE SQL;


-- Contract

CREATE FUNCTION insert_aggreement(integer, character varying, character varying, 
								  character varying, integer, character varying)
RETURNS void AS 
$$
INSERT INTO contract(id, date_signed, date_active_from, date_active_to, company_id, billing_units)
VALUES ($1, $2, $3, $4, $5, $6);
$$
LANGUAGE SQL;

CREATE FUNCTION delete_aggreement(integer) RETURNS void AS
$$
DELETE FROM contract * WHERE id = $1;
$$
LANGUAGE SQL;

CREATE FUNCTION edit_aggreement(integer, character varying, character varying, 
								  character varying, integer, character varying)
RETURNS void AS 
$$
UPDATE contract SET id = $1, date_signed = $2, date_active_from = $3, 
					date_active_to = $4, company_id = $5, billing_units = $6
$$
LANGUAGE SQL;

CREATE FUNCTION all_aggreements() RETURNS SETOF text AS 
$$
SELECT CONCAT_WS(',', id, date_signed, date_active_from, date_active_to, company_id, billing_units)
FROM contract;
$$
LANGUAGE SQL;

-- Company

CREATE FUNCTION all_firms() RETURNS SETOF text AS
$$
SELECT CONCAT_WS(',', id, name, address, contact_person, contact_email, contact_phone, contact_mobile)
FROM company;
$$
LANGUAGE SQL;

CREATE FUNCTION insert_firm(integer, character varying, character varying,
							character varying, character varying, 
							character varying, character varying)
RETURNS void AS
$$
INSERT INTO company(id, name, address, contact_person, contact_email, contact_phone, contact_mobile)
VALUES ($1, $2, $3, $4, $5, $6, $7);
$$
LANGUAGE SQL;

CREATE FUNCTION delete_firm(integer) RETURNS void AS
$$
DELETE FROM company * WHERE id = $1;
$$
LANGUAGE SQL;

CREATE FUNCTION edit_firm(integer, character varying, character varying,
							character varying, character varying, 
							character varying, character varying)
RETURNS void AS
$$
UPDATE company SET id = $1, name = $2, address = $3, contact_person = $4, 
				   contact_email = $5, contact_phone = $6, contact_mobile = $7;
$$
LANGUAGE SQL;

CREATE FUNCTION get_firm_id_name() RETURNS SETOF text AS
$$
SELECT CONCAT_WS(',', id, name) FROM company;
$$
LANGUAGE SQL;