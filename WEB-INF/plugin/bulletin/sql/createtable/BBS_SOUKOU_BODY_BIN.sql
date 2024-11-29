create table BBS_SOUKOU_BODY_BIN
(
        BSK_SID        integer not null,
        BSB_FILE_SID integer not null,
        BIN_SID        bigint not null,
        primary key (BSK_SID, BSB_FILE_SID)
) ;