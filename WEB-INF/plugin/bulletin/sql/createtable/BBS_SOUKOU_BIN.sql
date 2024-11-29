create table BBS_SOUKOU_BIN
(
        BSK_SID        integer not null,
        BIN_SID        bigint not null,
        primary key (BSK_SID, BIN_SID)
) ;