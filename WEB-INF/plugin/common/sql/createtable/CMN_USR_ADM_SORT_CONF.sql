create table CMN_USR_ADM_SORT_CONF
(
        UAS_SORT_KBN        integer         not null,
        UAS_SORT_KEY1       integer         not null,
        UAS_SORT_ORDER1     integer         not null,
        UAS_SORT_KEY2       integer         not null,
        UAS_SORT_ORDER2     integer         not null,
        UAS_AUID            integer         not null,
        UAS_ADATE           timestamp       not null,
        UAS_EUID            integer         not null,
        UAS_EDATE           timestamp       not null
) ;
