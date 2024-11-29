create table WML_DESTLIST
(
  WDL_SID    integer not null,
  WDL_NAME   varchar(100) not null,
  WDL_BIKO   varchar(1000),
  WDL_AUID   integer not null,
  WDL_ADATE  timestamp not null,
  WDL_EUID   integer not null,
  WDL_EDATE  timestamp not null,
  primary key(WDL_SID)
);