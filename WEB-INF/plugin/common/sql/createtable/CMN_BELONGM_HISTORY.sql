create table CMN_BELONGM_HISTORY
(
        GRP_SID       integer      not null,
        USR_SID       integer      not null,
        BEG_DATE      timestamp    not null,
        BEG_AUID      integer      not null,
        BEG_ADATE     timestamp    not null,
        BEG_EUID      integer      not null,
        BEG_EDATE     timestamp    not null,
        BEG_DEFGRP    integer      not null,
        BEG_GRPKBN    integer,
        primary key (GRP_SID,USR_SID,BEG_DATE)) ;