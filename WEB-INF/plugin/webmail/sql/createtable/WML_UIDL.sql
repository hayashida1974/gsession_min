create table WML_UIDL
(
  WAC_SID       integer       not null,
  WUD_ACCOUNT   varchar(500)  not null,
  WUD_UID       varchar(1000) not null,
  primary key(WAC_SID, WUD_ACCOUNT, WUD_UID)
);