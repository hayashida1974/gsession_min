create table WML_FILTER
(
  WFT_SID               integer not null,
  USR_SID               integer not null,
  WFT_NAME              varchar(100) not null,
  WFT_TYPE              integer not null,
  WAC_SID               integer,
  WFT_TEMPFILE          integer not null,
  WFT_ACTION_LABEL      integer not null,
  WLB_SID               integer,
  WFT_ACTION_READ       integer not null,
  WFT_ACTION_FORWARD    integer not null,
  WFT_ACTION_FWADDRESS  varchar(256),
  WFT_CONDITION         integer not null,
  WFT_ACTION_FOLDER       integer not null,
  primary key(WFT_SID)
);