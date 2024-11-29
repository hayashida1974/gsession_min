create table CMN_MAINSCREEN_LAYOUT
(
  USR_SID          integer           not null,
  MSC_POSITION     smallint          not null,
  MSL_AUID         integer           not null,
  MSL_ADATE        timestamp         not null,
  MSL_EUID         integer           not null,
  MSL_EDATE        timestamp         not null,
  primary key (USR_SID, MSC_POSITION)
);