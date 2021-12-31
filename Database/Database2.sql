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
    floor character varying(20),
    location character varying(255),
    active_from character varying(100),
    active_to character varying(100),
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
	CONSTRAINT Shop_check_serviceType CHECK (service_type IN ('food or drink', 'entertainment', 'retail')), -- PROSWRINO!!!
	CONSTRAINT Shop_check_activeFrom CHECK (active_from IN ('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday')),
	CONSTRAINT Shop_check_activeTo CHECK (active_to IN ('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday')),
	CONSTRAINT Shop_check_active CHECK (active IN ('Yes','No'))
	
);


-- Functions
DROP FUNCTION IF EXISTS all_malls;
DROP FUNCTION IF EXISTS insert_mall;
DROP FUNCTION IF EXISTS show_stores_of_mall;
DROP FUNCTION IF EXISTS delete_mall;
DROP FUNCTION IF EXISTS edit_mall;
DROP FUNCTION IF EXISTS get_mall_id_name;
DROP FUNCTION IF EXISTS Submit_New_Mall;
DROP FUNCTION IF EXISTS Delete_Shop;

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
DROP FUNCTION IF EXISTS Select_Mall_id;
DROP FUNCTION IF EXISTS Select_ServiceType;
DROP FUNCTION IF EXISTS Select_ContractID;

DROP FUNCTION IF EXISTS get_store_constraints;


-- Shopping_center

CREATE FUNCTION Select_Mall_id() RETURNS SETOF TEXT 
AS $$
SELECT DISTINCT(id) 
FROM shopping_center;
$$ 
LANGUAGE SQL;




CREATE FUNCTION Fill_Malls_List() RETURNS SETOF Shopping_center AS 
$$ 
SELECT * 
FROM Shopping_center ORDER BY id; 
$$ 
LANGUAGE SQL;

CREATE FUNCTION Submit_New_Mall(integer, character varying, character varying) RETURNS void AS
$$
INSERT INTO Shopping_center(id, name, address) 
VALUES ($1, $2, $3);
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION Select_Mall(integer) RETURNS TABLE ( shop_ID  integer, 
								shop_name character varying(255), 
								shopping_center_id integer,
								shopping_center_name character varying(255)) 
AS $$
SELECT S.id, S.shop_name, S.shopping_center_id ,SC.name AS shoppingCenterName
FROM Shop S JOIN Shopping_Center SC ON S.shopping_center_id=SC.id
WHERE SC.id=$1
$$ LANGUAGE SQL;

CREATE FUNCTION get_mall_id_name() RETURNS SETOF text AS
$$
SELECT CONCAT_WS(',', id, name) FROM shopping_center ORDER BY id;
$$
LANGUAGE SQL;

CREATE FUNCTION Delete_Mall(integer) RETURNS void AS
$$
DELETE FROM shopping_center * WHERE id = $1;
$$ 
LANGUAGE SQL;

CREATE FUNCTION edit_mall(integer, character varying, character varying) RETURNS void AS 
$$
UPDATE shopping_center SET name = $2, address = $3 WHERE id = $1;
$$
LANGUAGE SQL;

-- Shop

CREATE FUNCTION Select_ServiceType() RETURNS SETOF text 
AS $$
SELECT DISTINCT(service_type) 
FROM Shop;
$$ 
LANGUAGE SQL;

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

CREATE OR REPLACE FUNCTION Fill_Shops() RETURNS TABLE(shop_id integer,
						      shop_name character varying,
						      mall_id integer)
AS $$
SELECT S.id , S.shop_name , S.shopping_center_id
FROM Shop S ;
$$ LANGUAGE SQL;

CREATE FUNCTION all_stores() RETURNS SETOF text AS 
$$ 
SELECT CONCAT_WS(',', id, shop_name, shopping_center_id, floor, 
				 location, active_from, active_to, active, contract_id, 
				 service_type) 
				 FROM Shop ORDER BY id; 
$$ 
LANGUAGE SQL;

CREATE FUNCTION Delete_Shop(integer) RETURNS void AS 
$$
DELETE FROM shop * WHERE id = $1;
$$
LANGUAGE SQL;

CREATE FUNCTION edit_store(integer, character varying, integer, 
						   integer, character varying, character varying, 
						   character varying, boolean, integer, character varying)
RETURNS void AS 
$$
UPDATE shop SET shop_name = $2, shopping_center_id = $3, floor = $4, location = $5, 
				active_from = $6, active_to = $7, active = $8, contract_id = $9, service_type = $10
				WHERE id = $1;
$$
LANGUAGE SQL;


CREATE OR REPLACE FUNCTION Info_Shop(integer) RETURNS text AS 
$$
SELECT CONCAT_WS(',', S.id, S.shop_name, S.shopping_center_id, S.floor, 
				 S.location, S.active_from, S.active_to, S.active, S.contract_id, 
				 S.service_type, C.date_signed , C.date_active_from , C.date_active_to , C.billing_units
				 , C.company_id) 
FROM shop AS S INNER JOIN contract AS C ON S.contract_id = C.id 
WHERE S.id = $1
$$
LANGUAGE SQL;


-- Contract

CREATE OR REPLACE FUNCTION Select_ContractID() RETURNS SETOF text
AS $$
SELECT id 
FROM contract;
$$
LANGUAGE SQL;


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
UPDATE contract SET date_signed = $2, date_active_from = $3, 
					date_active_to = $4, company_id = $5, billing_units = $6
					WHERE id = $1;
$$
LANGUAGE SQL;

CREATE FUNCTION all_aggreements() RETURNS SETOF text AS 
$$
SELECT CONCAT_WS(',', id, date_signed, date_active_from, date_active_to, company_id, billing_units)
FROM contract ORDER BY id;
$$
LANGUAGE SQL;

-- Company

CREATE FUNCTION all_firms() RETURNS SETOF text AS
$$
SELECT CONCAT_WS(',', id, name, address, contact_person, contact_email, contact_phone, contact_mobile)
FROM company ORDER BY id;
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
UPDATE company SET name = $2, address = $3, contact_person = $4, 
				   contact_email = $5, contact_phone = $6, contact_mobile = $7
				   WHERE id = $1;
$$
LANGUAGE SQL;

CREATE FUNCTION get_firm_id_name() RETURNS SETOF text AS
$$
SELECT CONCAT_WS(',', id, name) FROM company ORDER BY id;
$$
LANGUAGE SQL;


--CONSTRAINT

CREATE FUNCTION get_store_constraints() RETURNS SETOF text AS 
$$
SELECT pg_get_constraintdef(oid) AS res
	  FROM   pg_catalog.pg_constraint
	  WHERE  contype  = 'c'                          
	  AND    conrelid = 'public.shop'::regclass  
	  AND    conname = 'shop_check_servicetype';
$$
LANGUAGE SQL;







-- Data

-- Shopping centers
SELECT Submit_New_Mall(1, 'Mediterranean Cosmos', '11th km National Road Thessaloniki-N.Moudania, 57001, Thessaloniki');
SELECT Submit_New_Mall(2, 'One Salonica', 'Kotta Roulia 10, 546 27, Thessaloniki');
SELECT Submit_New_Mall(3, 'The Mall Athens', 'Andrea Papandreou 35, 15122, Marousi');
SELECT Submit_NEW_Mall(999, 'The Mall Athens', 'Andrea Papandreou 35, 15122, Marousi');
SELECT Submit_NEW_Mall(998, 'The Mall Athens', 'Andrea Papandreou 355, 15122, Marousi');

-- Companies
SELECT insert_firm(9, 'Adidas', 'Tsimiski 47, 54623, Thessaloniki', 
				   'Georgios Georgiou', 'adidasgr@adidassupport.gr',
				   '2310435422', '6943345305');
SELECT insert_firm(4, 'Crocs', 'Egnatia 55, 54624, Thessaloniki',
				   'Iraklis Konsoulas', 'crocsgr@crocssupport.gr',
				   '23104433421', '6942072096');
SELECT insert_firm(22, 'Goodys', 'Leoforos Nikis 11, 54624, Thessaloniki',
				   'Athanasios Tzimas', 'getgood@goodys.gr',
				   '23109394839', '6923454321');
SELECT insert_firm(102, 'Village Cinemas', 'Mitropoleos 101, 54622, Thessaloniki',
				   'Ioannis Kontos', 'cine@villagesupport.gr',
				   '23102309390', '6943985487');
-- Contracts
SELECT insert_aggreement(10, '19/11/2018', '22/10/2019', '04/11/2020', 102, 'monthly');
SELECT insert_aggreement(22, '22/09/2009', '01/02/2010', '05/03/2020', 22, 'yearly');
SELECT insert_aggreement(3, '01/02/2020', '14/03/2020', '31/12/2021', 4, 'weekly');
SELECT insert_aggreement(99, '11/12/2015', '23/01/2016', '30/05/2025', 9, 'yearly');
-- Shops
SELECT insert_store(5, 'Adidas Cosmos 2A', 1, 2, 'A', 'Friday', 'Sunday', true, 99, 'retail');
SELECT insert_store(32, 'Crocs One Salonica 1C', 2, 1, 'C', 'Monday', 'Saturday', true, 3, 'retail');
SELECT insert_store(2, 'Goodys Mall Athens 3F', 3, 3, 'F', 'Tuesday', 'Sunday', false, 22, 'food or drink');
SELECT insert_store(9, 'Village Cinemas Cosmos 0A', 1, 0, 'A', 'Monday', 'Sunday', false, 10, 'entertainment');
