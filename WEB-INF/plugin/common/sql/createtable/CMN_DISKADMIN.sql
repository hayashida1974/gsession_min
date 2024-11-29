create table CMN_DISKADMIN (
        DSK_TYPE       integer        not null,
        DSK_VALUE      integer        not null,
        DSK_AUID       integer        not null,
        DSK_ADATE      timestamp      not null,
        DSK_EUID       integer        not null,
        DSK_EDATE      timestamp      not null
);