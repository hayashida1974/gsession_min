create table CMN_USR_PRI_SORT_CONF
(
        USR_SID             integer         not null,
        UPS_SORT_KEY1       integer         not null,
        UPS_SORT_ORDER1     integer         not null,
        UPS_SORT_KEY2       integer         not null,
        UPS_SORT_ORDER2     integer         not null,
        UPS_AUID            integer         not null,
        UPS_ADATE           timestamp       not null,
        UPS_EUID            integer         not null,
        UPS_EDATE           timestamp       not null,
        primary key (USR_SID)
) ;
