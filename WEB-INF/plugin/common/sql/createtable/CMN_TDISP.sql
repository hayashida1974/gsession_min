create table CMN_TDISP
(
    USR_SID         integer         not null,
    TDP_PID         varchar(10)     not null,
    TDP_ORDER       integer         not null,
    TDP_AUID        integer         not null,
    TDP_ADATE       timestamp       not null,
    TDP_EUID        integer         not null,
    TDP_EDATE       timestamp       not null,
    primary key (
      USR_SID, TDP_PID
    ) 
) ;