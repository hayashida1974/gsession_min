create table CMN_OAUTH
(
  COU_SID                integer       not null,
  COU_PROVIDER           integer       not null,
  COU_AUTH_ID            varchar(1000) not null,
  COU_AUTH_SECRET        varchar(1000) not null,
  COU_BIKO               varchar(1000),
  COU_AUID               integer       not null,
  COU_ADATE              timestamp     not null,
  COU_EUID               integer       not null,
  COU_EDATE              timestamp     not null,
  primary key(COU_SID)
);