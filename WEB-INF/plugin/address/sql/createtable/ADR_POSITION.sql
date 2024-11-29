create table ADR_POSITION
(
  APS_SID         integer  not null,
  APS_NAME        varchar(30)  not null,
  APS_AUID        integer  not null,
  APS_ADATE       timestamp  not null,
  APS_EUID        integer  not null,
  APS_EDATE       timestamp  not null,
  APS_BIKO        varchar(300),
  APS_SORT        integer not null,
primary key(APS_SID)
);