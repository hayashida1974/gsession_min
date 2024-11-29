create table CMN_MY_GROUP
(
        USR_SID          integer      not null,
        MGP_SID          integer      not null,
        MGP_NAME         varchar(20),
        MGP_MEMO         varchar(1000),
        MGP_AUID         integer      not null,
        MGP_ADATE        timestamp       not null,
        MGP_EUID         integer      not null,
        MGP_EDATE        timestamp       not null,
        primary key (USR_SID, MGP_SID)
);