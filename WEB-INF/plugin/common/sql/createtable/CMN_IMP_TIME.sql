create table CMN_IMP_TIME
(
        CIT_USR_FLG       integer not null default 0,
        CIT_USR_TIME_FLG  integer not null default 0,
        CIT_USR_TIME      timestamp not null,
        CIT_GRP_FLG       integer not null default 0,
        CIT_GRP_TIME_FLG  integer not null default 0,
        CIT_GRP_TIME      timestamp not null,
        CIT_BEG_FLG       integer not null default 0,
        CIT_BEG_TIME_FLG  integer not null default 0,
        CIT_BEG_TIME      timestamp not null
);