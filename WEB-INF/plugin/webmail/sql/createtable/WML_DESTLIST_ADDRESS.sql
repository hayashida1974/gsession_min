create table WML_DESTLIST_ADDRESS
(
  WDL_SID    integer not null,
  WDA_TYPE   integer not null,
  WDA_SID    integer not null,
  WDA_ADRNO  integer not null,
  primary key(WDL_SID, WDA_TYPE, WDA_SID, WDA_ADRNO)
);