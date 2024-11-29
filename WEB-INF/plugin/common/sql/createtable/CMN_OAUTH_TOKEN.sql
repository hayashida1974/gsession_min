create table CMN_OAUTH_TOKEN
(
  COT_SID                integer       not null,
  COU_SID                integer       not null,
  COT_ACCOUNTID          varchar(300),
  COT_AUTH_RTOKEN        varchar(3000),
  COT_AUTH_ATOKEN        varchar(3000),
  COT_AUTH_ATOKEN_DATE   timestamp,
  primary key(COT_SID)
);