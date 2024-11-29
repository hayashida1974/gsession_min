create table WML_UIDL_TEMP
(
  WAC_SID       integer       not null,
  WUD_UID       varchar(1000) not null,
  primary key(WAC_SID, WUD_UID)
);