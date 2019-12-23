------------------------------------ SEQUENCES ------------------------------------

CREATE SEQUENCE public.customer_seq
    INCREMENT 50
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.customer_seq
    OWNER TO digivox;

CREATE SEQUENCE public.item_seq
    INCREMENT 50
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.item_seq
    OWNER TO digivox;

CREATE SEQUENCE public.item_type_seq
    INCREMENT 50
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.item_type_seq
    OWNER TO digivox;

CREATE SEQUENCE public.rent_seq
    INCREMENT 50
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.rent_seq
    OWNER TO digivox;

------------------------------------ CREATE TABLES ------------------------------------

CREATE TABLE public.item_type
(
    id bigint NOT NULL DEFAULT nextval('item_type_seq'::regclass),
    type character varying(255) COLLATE pg_catalog."default" NOT NULL,
    deleted boolean,
    CONSTRAINT item_type_pkey PRIMARY KEY (id),
    CONSTRAINT uk_j7gbm3euoipfv0jsyxy3r9oj9 UNIQUE (type)
);

ALTER TABLE public.item_type
    OWNER to digivox;

CREATE TABLE public.item
(
    id bigint NOT NULL DEFAULT nextval('item_seq'::regclass),
    is_available boolean,
    deleted boolean,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    price numeric(19,2) NOT NULL,
    type_id bigint NOT NULL,
    CONSTRAINT item_pkey PRIMARY KEY (id),
    CONSTRAINT fkg9lymegmlbvqtjrwpvkcdo5gr FOREIGN KEY (type_id)
        REFERENCES public.item_type (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE public.item
    OWNER to digivox;

CREATE TABLE public.customer
(
    id bigint NOT NULL DEFAULT nextval('customer_seq'::regclass),
    cpf character varying(255) COLLATE pg_catalog."default" NOT NULL,
    deleted boolean,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT customer_pkey PRIMARY KEY (id),
    CONSTRAINT uk_cwtgtb16nebxu54elskdjei66 UNIQUE (cpf)
    ,
    CONSTRAINT uk_dwk6cx0afu8bs9o4t536v1j5v UNIQUE (email)

);

ALTER TABLE public.customer
    OWNER to digivox;

CREATE TABLE public.rent
(
    id bigint NOT NULL DEFAULT nextval('rent_seq'::regclass),
    customer_id bigint NOT NULL,
    deleted boolean,
    rent_date timestamp without time zone NOT NULL,
    rent_situation character varying(255) COLLATE pg_catalog."default" NOT NULL,
    return_date timestamp without time zone NOT NULL,
    CONSTRAINT rent_pkey PRIMARY KEY (id),
    CONSTRAINT fkf8d1kir04yd0rmvkklr7uvrgq FOREIGN KEY (customer_id)
        REFERENCES public.customer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE public.rent
    OWNER to digivox;

CREATE TABLE public.rent_items_used
(
    rent_id bigint NOT NULL,
    items_used_id bigint NOT NULL,
    CONSTRAINT rent_items_used_pkey PRIMARY KEY (rent_id, items_used_id),
    CONSTRAINT fk1w5jyhjow5to8o5jobya3ejoy FOREIGN KEY (items_used_id)
        REFERENCES public.item (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkruhfsdpt0nyih7gd9x5sv0jya FOREIGN KEY (rent_id)
        REFERENCES public.rent (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE public.rent_items_used
    OWNER to digivox;