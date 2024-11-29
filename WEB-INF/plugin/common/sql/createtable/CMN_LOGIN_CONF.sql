create table CMN_LOGIN_CONF
(
  LLC_LOCKOUT_KBN  integer    not null,
  LLC_FAIL_CNT     integer,
  LLC_FAIL_AGE     integer,
  LLC_LOCK_AGE     integer,
  LLC_AUID         integer    not null,
  LLC_ADATE        timestamp not null,
  LLC_EUID         integer    not null,
  LLC_EDATE        timestamp not null
) ;
