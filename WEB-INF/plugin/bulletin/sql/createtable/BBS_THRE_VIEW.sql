create table BBS_THRE_VIEW
(
        BTI_SID        integer not null,
        USR_SID        integer not null,
        BFI_SID        integer not null,
        BIV_VIEW_KBN   integer not null,
        BIV_AUID       integer not null,
        BIV_ADATE      timestamp not null,
        BIV_EUID       integer not null,
        BIV_EDATE      timestamp not null,
        primary key (BTI_SID, USR_SID)
) ;