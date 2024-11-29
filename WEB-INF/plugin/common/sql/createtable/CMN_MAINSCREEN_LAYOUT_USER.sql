create table CMN_MAINSCREEN_LAYOUT_USER
(
  USR_SID          smallint          not null,
  MSU_DEFAULT_KBN  smallint          not null,
  MSU_AUID         integer           not null,
  MSU_ADATE        timestamp         not null,
  MSU_EUID         integer           not null,
  MSU_EDATE        timestamp         not null,
  primary key (USR_SID)
);