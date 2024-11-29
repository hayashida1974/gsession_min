create table CMN_USRM
(
        USR_SID         integer         not null,
        USR_LGID        varchar(256)     not null,
        USR_PSWD        varchar(352)     not null,
        USR_JKBN        integer         not null,
        USR_AUID        integer         not null,
        USR_ADATE       timestamp       not null,
        USR_EUID        integer         not null,
        USR_EDATE       timestamp       not null,
        USR_PSWD_KBN    integer         not null,
        USR_PSWD_EDATE  timestamp       not null,
        USR_UKO_FLG     integer         not null,
        primary key (USR_SID)
);