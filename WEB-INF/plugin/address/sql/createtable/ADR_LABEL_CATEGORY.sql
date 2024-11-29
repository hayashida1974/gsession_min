    create table ADR_LABEL_CATEGORY
  (
   ALC_SID     integer       not null,
   ALC_NAME    varchar(20)   not null,
   ALC_BIKO    varchar(300),
   ALC_SORT    integer       not null,
   ALC_AUID    integer       not null,
   ALC_ADATE   timestamp     not null,
   ALC_EUID    integer       not null,
   ALC_EDATE   timestamp     not null,
   primary key (ALC_SID))
   ;