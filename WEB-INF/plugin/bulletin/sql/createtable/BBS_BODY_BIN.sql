create table BBS_BODY_BIN
(
        BWI_SID        integer not null,
        BBB_FILE_SID integer not null,
        BIN_SID        bigint not null,
        primary key (BWI_SID, BBB_FILE_SID)
) ;