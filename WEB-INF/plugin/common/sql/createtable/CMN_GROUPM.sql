create table CMN_GROUPM
(
        GRP_SID         integer      not null,
        GRP_ID         varchar(50)      not null,
        GRP_NAME        varchar(50),
        GRP_NAME_KN     varchar(75),
        GRP_COMMENT     varchar(1000),
        GRP_AUID        integer      not null,
        GRP_ADATE       timestamp       not null,
        GRP_EUID        integer      not null,
        GRP_EDATE       timestamp       not null,
        GRP_SORT        integer      not null,
        GRP_JKBN        integer      not null,
        primary key (GRP_SID)
) ;