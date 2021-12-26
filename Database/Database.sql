-- SCHEMA: public

DROP SCHEMA IF EXISTS public CASCADE;

CREATE SCHEMA IF NOT EXISTS public
    AUTHORIZATION postgres;

COMMENT ON SCHEMA public
    IS 'standard public schema';

GRANT ALL ON SCHEMA public TO PUBLIC;

GRANT ALL ON SCHEMA public TO postgres;

-- Table: public.Shopping_center

DROP TABLE IF EXISTS public.Shopping_center;

CREATE TABLE IF NOT EXISTS public.Shopping_center
(
    id integer NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    address character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT Shopping_center_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.Shopping_center
    OWNER to postgres;
	
-- Table: public.Company

DROP TABLE IF EXISTS public.Company;

CREATE TABLE IF NOT EXISTS public.Company
(
    id integer NOT NULL,
    company_name character varying(255) COLLATE pg_catalog."default",
    address character varying(255) COLLATE pg_catalog."default",
    contact_person character varying(100) COLLATE pg_catalog."default",
    contact_email character varying(100) COLLATE pg_catalog."default",
    contact_phone character varying(15) COLLATE pg_catalog."default",
    contact_mobile character varying(15) COLLATE pg_catalog."default",
    details text COLLATE pg_catalog."default",
    CONSTRAINT Company_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.Company
    OWNER to postgres;
	
-- Table: public.Contract

DROP TABLE IF EXISTS public.Contract;

CREATE TABLE IF NOT EXISTS public.Contract
(
    id integer NOT NULL,
    contract_details text COLLATE pg_catalog."default",
    date_signed date,
    date_active_from date,
    date_active_to date,
    provider_id integer,
    billing_units character varying(25) COLLATE pg_catalog."default",
    CONSTRAINT Contract_pkey PRIMARY KEY (id),
    CONSTRAINT Contract_fkey_Company_id FOREIGN KEY (provider_id)
        REFERENCES public.Company (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.Contract
    OWNER to postgres;
-- Index: fki_Contract_fkey

DROP INDEX IF EXISTS public.fki_Contract_fkey;

CREATE INDEX IF NOT EXISTS fki_Contract_fkey
    ON public.Contract USING btree
    (provider_id ASC NULLS LAST)
    TABLESPACE pg_default;
	
-- Table: public.Invoice

DROP TABLE IF EXISTS public.Invoice;

CREATE TABLE IF NOT EXISTS public.Invoice
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
        REFERENCES public.Company (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT Invoice_fkey_Contract_id FOREIGN KEY (contract_id)
        REFERENCES public.Contract (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.Invoice
    OWNER to postgres;
-- Index: fki_C

DROP INDEX IF EXISTS public.fki_C;

CREATE INDEX IF NOT EXISTS fki_C
    ON public.Invoice USING btree
    (issued_by_id ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: fki_Contract_fkey_Company_id

DROP INDEX IF EXISTS public.fki_Contract_fkey_Company_id;

CREATE INDEX IF NOT EXISTS fki_Contract_fkey_Company_id
    ON public.Invoice USING btree
    (issued_by_id ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: fki_F

DROP INDEX IF EXISTS public.fki_F;

CREATE INDEX IF NOT EXISTS fki_F
    ON public.Invoice USING btree
    (contract_id ASC NULLS LAST)
    TABLESPACE pg_default;
	
-- Table: public.Shop

DROP TABLE IF EXISTS public.Shop;

CREATE TABLE IF NOT EXISTS public.Shop
(
    id integer NOT NULL,
    shop_name character varying(255) COLLATE pg_catalog."default",
    shopping_center_id integer,
    floor character varying(20) COLLATE pg_catalog."default",
    location character varying(255) COLLATE pg_catalog."default",
    description text COLLATE pg_catalog."default",
    active_from date,
    active_to date,
    active date,
    contract_id integer,
    CONSTRAINT Shop_pkey PRIMARY KEY (id),
    CONSTRAINT Shop_fkey_Contract_id FOREIGN KEY (contract_id)
        REFERENCES public.Contract (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT Shop_fkey_ShoppingCenter_id FOREIGN KEY (shopping_center_id)
        REFERENCES public.Shopping_center (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.Shop
    OWNER to postgres;
-- Index: fki_Shop_fkey_Contract_id

DROP INDEX IF EXISTS public.fki_Shop_fkey_Contract_id;

CREATE INDEX IF NOT EXISTS fki_Shop_fkey_Contract_id
    ON public.Shop USING btree
    (contract_id ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: fki_Shop_fkey_ShoppingCenter_id

DROP INDEX IF EXISTS public.fki_Shop_fkey_ShoppingCenter_id;

CREATE INDEX IF NOT EXISTS fki_Shop_fkey_ShoppingCenter_id
    ON public.Shop USING btree
    (shopping_center_id ASC NULLS LAST)
    TABLESPACE pg_default;
	
-- Table: public.Service

DROP TABLE IF EXISTS public.Service;

CREATE TABLE IF NOT EXISTS public.Service
(
    id integer NOT NULL,
    contract_id integer,
    service_type character varying(255) COLLATE pg_catalog."default",
    details text COLLATE pg_catalog."default",
    shopping_center_id integer,
    shop_id integer,
    CONSTRAINT Service_pkey PRIMARY KEY (id),
    CONSTRAINT Service_fkey_Contract_id FOREIGN KEY (contract_id)
        REFERENCES public."Contract" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT Service_fkey_Shop_id FOREIGN KEY (shop_id)
        REFERENCES public.Shop (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT Service_fkey_ShoppingCenter_id FOREIGN KEY (shopping_center_id)
        REFERENCES public.Shopping_center (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.Service
    OWNER to postgres;
-- Index: fki_Service_fkey_Contract_id

DROP INDEX IF EXISTS public.fki_Service_fkey_Contract_id;

CREATE INDEX IF NOT EXISTS fki_Service_fkey_Contract_id
    ON public.Service USING btree
    (contract_id ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: fki_Service_fkey_Shop_id

DROP INDEX IF EXISTS public.fki_Service_fkey_Shop_id;

CREATE INDEX IF NOT EXISTS fki_Service_fkey_Shop_id
    ON public.Service USING btree
    (shop_id ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: fki_Service_fkey_ShoppingCenter_id

DROP INDEX IF EXISTS public.fki_Service_fkey_ShoppingCenter_id;

CREATE INDEX IF NOT EXISTS fki_Service_fkey_ShoppingCenter_id
    ON public.Service USING btree
    (shopping_center_id ASC NULLS LAST)
    TABLESPACE pg_default;