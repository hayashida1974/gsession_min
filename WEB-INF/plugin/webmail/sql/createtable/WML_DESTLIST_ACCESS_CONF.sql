create table WML_DESTLIST_ACCESS_CONF
(
  WDL_SID      integer not null,
  WLA_KBN      integer not null,
  WLA_USR_SID  integer not null,
  WLA_AUTH     integer not null,
  primary key(WDL_SID, WLA_KBN, WLA_USR_SID)
);