create table BBS_FOR_SUM
(
        BFI_SID        integer not null,
        BFS_THRE_CNT   integer,
        BFS_WRT_CNT    integer,
        BFS_WRT_DATE   timestamp,
        BFS_AUID       integer not null,
        BFS_ADATE      timestamp not null,
        BFS_EUID       integer not null,
        BFS_EDATE      timestamp not null,
        BFS_SIZE       bigint not null,
        primary key (BFI_SID)
) ;