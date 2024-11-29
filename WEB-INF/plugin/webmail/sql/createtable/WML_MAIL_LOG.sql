create table WML_MAIL_LOG
(
  WMD_MAILNUM   bigint          not null,
  WLG_TITLE     varchar(1000)   not null,
  WLG_DATE      timestamp,
  WLG_FROM      varchar(256),
  WLG_TEMPFLG   integer         not null,
  WLG_MAILTYPE  integer         not null,
  WAC_SID       integer         not null,
  primary key(WMD_MAILNUM)
);