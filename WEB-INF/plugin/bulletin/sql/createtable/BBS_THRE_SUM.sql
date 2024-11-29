create table BBS_THRE_SUM
(
        BTI_SID        integer not null,
        BTS_WRT_CNT    integer,
        BTS_WRT_DATE   timestamp,
        BTS_AUID       integer not null,
        BTS_ADATE      timestamp not null,
        BTS_EUID       integer not null,
        BTS_EDATE      timestamp not null,
        BTS_SIZE       bigint not null,
        BTS_TEMPFLG    integer not null,
        primary key (BTI_SID)
) ;