create table language_fr
(
    id            UUID          not null default gen_random_uuid(),
    fr            varchar(512)  not null unique,
    en            varchar(1024) not null,
    gender  varchar(3) not null,     -- la, le, les, l',uni,-
    pos     varchar(1024),           -- noun, verb, adv, adj, etc
    pronunciation varchar(1024),
    gender2 varchar(1)  default '-', -- m,f,-
    src     varchar(51) default '',

    cre_ts        TIMESTAMPTZ   NOT NULL,
    upd_ts        TIMESTAMPTZ            DEFAULT now() ON UPDATE now(),

    primary key (id)
);