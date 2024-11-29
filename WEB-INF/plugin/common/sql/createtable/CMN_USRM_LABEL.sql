 create table CMN_USRM_LABEL
 (
   USR_SID    integer   not null,
   LAB_SID    integer   not null,
   USL_AUID   integer   not null,
   USL_ADATE  timestamp not null,
   USL_EUID   integer   not null,
   USL_EDATE  timestamp not null,
   primary key(USR_SID, LAB_SID)
   );