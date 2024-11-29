create table CMN_USR_LANG
(
        USR_SID        integer         not null,
        CUL_COUNTRY    varchar (20)    not null,
        CUL_AUID       integer         not null,
        CUL_ADATE      timestamp       not null,
        CUL_EUID       integer         not null,
        CUL_EDATE      timestamp       not null,
        primary key (USR_SID)
) ;
