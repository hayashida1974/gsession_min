create table CMN_LANG
(
        LNG_SID       integer      not null,
        LNG_NAME      varchar (20) not null,
        LNG_CODE      varchar (20) not null,
        LNG_COUNTRY   varchar (20) not null,
        primary key (LNG_SID)
) ;