create table WML_MAIL_SENDPLAN
(
  WMD_MAILNUM        bigint      not null,
  WAC_SID            integer     not null,
  WSP_SENDKBN        integer     not null,
  WSP_SENDDATE       timestamp   not null,
  WSP_MAILTYPE       integer     not null,
  WSP_COMPRESS_FILE  integer,
  primary key(WMD_MAILNUM)
);
