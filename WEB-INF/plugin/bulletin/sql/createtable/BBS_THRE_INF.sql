create table BBS_THRE_INF
(
        BTI_SID        integer not null,
        BFI_SID        integer not null,
        BTI_TITLE      varchar(150) not null,
        BTI_AUID       integer not null,
        BTI_ADATE      timestamp not null,
        BTI_EUID       integer not null,
        BTI_EDATE      timestamp not null,
        BTI_LIMIT      integer not null,
        BTI_LIMIT_FR_DATE timestamp,
        BTI_LIMIT_DATE timestamp,
        BTI_AGID       integer,
        BTI_EGID       integer,
        BTI_IMPORTANCE       integer,
        primary key (BTI_SID)
) ;