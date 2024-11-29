create table CMN_PLUGIN_CONTROL
(
  PCT_PID  varchar(10)  not null,
  PCT_KBN  integer      not null,
  PCT_TYPE integer      not null default 0,
  primary key (PCT_PID)
) ;
