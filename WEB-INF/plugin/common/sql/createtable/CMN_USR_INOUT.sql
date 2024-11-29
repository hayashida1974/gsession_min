create table CMN_USR_INOUT
(
        UIO_SID        integer         not null,
        UIO_STATUS     integer         not null,
        UIO_AUID       integer         not null,
        UIO_ADATE      timestamp       not null,
        UIO_EUID       integer         not null,
        UIO_EDATE      timestamp       not null,
        UIO_BIKO       varchar(30),
        primary key (UIO_SID)
) ;
