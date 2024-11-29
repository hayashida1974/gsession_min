create table BBS_USER
(
        USR_SID            integer not null,
        BUR_FOR_CNT        integer not null,
        BUR_THRE_CNT       integer not null,
        BUR_WRT_CNT        integer not null,
        BUR_NEW_CNT        integer not null,
        BUR_SML_NTF        integer not null,
        BUR_THRE_MAIN_CNT  integer not null,
        BUR_WRTLIST_ORDER  integer not null,
        BUR_THRE_IMAGE     integer not null,
        BUR_AUID           integer not null,
        BUR_ADATE          timestamp not null,
        BUR_EUID           integer not null,
        BUR_EDATE          timestamp not null,
        BUR_MAIN_CHKED_DSP integer not null,
        BUR_SUB_NEW_THRE   integer not null,
        BUR_SUB_FORUM      integer not null,
        BUR_SUB_UNCHK_THRE integer not null,
        BUR_TEMP_IMAGE integer not null,
        BUR_INI_POST_TYPE integer not null,
        primary key (USR_SID)
) ;