create table BBS_WRITE_INF
(
        BWI_SID        integer not null,
        BFI_SID        integer not null,
        BTI_SID        integer not null,
        BWI_VALUE   text,
        BWI_VALUE_PLAIN  text,
        BWI_TYPE       integer not null,
        BWI_AUID       integer not null,
        BWI_ADATE      timestamp not null,
        BWI_EUID       integer not null,
        BWI_EDATE      timestamp not null,
        BWI_AGID       integer,
        BWI_EGID       integer,
        BWI_PARENT_FLG integer not null,
        primary key (BWI_SID)
) ;