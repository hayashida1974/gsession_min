create table BBS_FOR_ADMIN
(
        BFI_SID        integer not null,
        USR_SID        integer not null,
        primary key (BFI_SID, USR_SID)
);