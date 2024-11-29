CREATE ALIAS IF NOT EXISTS FTL_INIT FOR "org.h2.fulltext.FullTextLucene.init";
CALL FTL_INIT();

create table WML_MAIL_BODY
(
  WMD_MAILNUM  bigint   not null,
  WMB_BODY     text,
  WMB_CHARSET  varchar(50),
  WAC_SID      integer  not null,
  primary key(WMD_MAILNUM)
);

CALL FTL_CREATE_INDEX('PUBLIC', 'WML_MAIL_BODY', 'WMB_BODY');