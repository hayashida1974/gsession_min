create table CMN_POSITION
(
        POS_SID    integer       not null,
        POS_CODE   varchar(15)   not null,
        POS_NAME   varchar(30)   not null,
        POS_BIKO   varchar(300)  not null,
        POS_SORT   integer       not null,
        POS_AUID   integer       not null,
        POS_ADATE  timestamp     not null,
        POS_EUID   integer       not null,
        POS_EDATE  timestamp     not null,
        primary key (POS_SID)
) ;