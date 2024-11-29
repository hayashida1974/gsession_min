create table WML_AUTODELETE_LOG
(
  WAL_DEL_KBN            integer           not null,
  WAL_DEL_YEAR           integer,
  WAL_DEL_MONTH          integer,
  WAL_DEL_DAY            integer,
  WAL_AUID               integer           not null,
  WAL_ADATE              timestamp        not null,
  WAL_EUID               integer           not null,
  WAL_EDATE              timestamp        not null      
);