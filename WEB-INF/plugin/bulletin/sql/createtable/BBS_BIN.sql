create table BBS_BIN
(
        BWI_SID        integer not null,
        BIN_SID        bigint not null,
        primary key (BWI_SID, BIN_SID)
) ;