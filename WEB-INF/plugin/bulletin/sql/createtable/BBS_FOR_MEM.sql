create table BBS_FOR_MEM
(
        BFI_SID        integer not null,
        USR_SID        integer not null,
        GRP_SID        integer not null,
        BFM_AUTH       integer not null,
        primary key (BFI_SID, USR_SID, GRP_SID)
) ;