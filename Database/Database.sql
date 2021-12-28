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
    company_name character varying(255),
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
    date_signed date,
    date_active_from date,
    date_active_to date,
    company_id integer,
    billing_units character varying(25),
    CONSTRAINT Contract_pkey PRIMARY KEY (id),
    CONSTRAINT Contract_fkey_Company_id FOREIGN KEY (company_id)
        REFERENCES public.Company (id)
        ON UPDATE NO ACTION
        ON DELETE CASCADE -- Na to ksanakoitaksw
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
    date_issued date,
    date_paid date,
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
    active_from date,
    active_to date,
    active date,
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

-- Shopping_center
CREATE OR REPLACE FUNCTION all_malls() RETURNS SETOF text AS 
$$ 
SELECT CONCAT_WS(',', id, name, address) 
FROM Shopping_center; 
$$ 
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION insert_mall(integer, character varying, character varying) RETURNS void AS
$$
INSERT INTO Shopping_center(id, name, address) 
VALUES ($1, $2, $3);
$$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION show_stores_of_mall(integer) RETURNS text AS
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

CREATE OR REPLACE FUNCTION delete_mall(integer) RETURNS void AS
$$
DELETE FROM shopping_center * WHERE id = $1;
$$ 
LANGUAGE SQL;

-- Shop
CREATE OR REPLACE FUNCTION all_stores() RETURNS SETOF text AS 
$$ 
SELECT CONCAT_WS(',', id, shop_name, shopping_center_id, floor, 
				 location, active_from, active_to, active, contract_id, 
				 service_type) 
				 FROM Shop; 
$$ 
LANGUAGE SQL;
