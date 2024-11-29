create table CMN_MAINSCREEN_CONF
(
        USR_SID       integer         not null,
        MSC_ID        varchar(150)    not null,
        MSC_POSITION  integer         not null,
        MSC_ORDER     integer         not null,
        MSC_AUID      integer         not null,
        MSC_ADATE     timestamp       not null,
        MSC_EUID      integer         not null,
        MSC_EDATE     timestamp       not null,
        primary key(USR_SID, MSC_ID)
) ;