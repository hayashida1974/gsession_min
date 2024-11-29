create table RNG_CHANNEL_TEMPLATE
(
  RCT_SID		integer		not null,
  USR_SID		integer		not null,
  RCT_NAME      varchar(20)	not null,
  RCT_AUID		integer		not null,
  RCT_ADATE		timestamp      not null,
  RCT_EUID		integer		not null,
  RCT_EDATE		timestamp      not null,
  RCT_JKBN		integer not null default 0,
  RCT_VER		integer not null default 0,
  primary key (RCT_SID, USR_SID)
) ;