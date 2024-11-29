create table CMN_PLUGIN_ADMIN
(
  PCT_PID  varchar(10)  not null,
  GRP_SID  integer      not null,
  USR_SID  integer      not null,
  primary key (PCT_PID, GRP_SID, USR_SID)
) ;
