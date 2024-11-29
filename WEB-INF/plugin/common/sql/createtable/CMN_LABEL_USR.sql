 create table CMN_LABEL_USR
 (
   LAB_SID   integer     not null,
   LUC_SID   integer     not null,
   LAB_NAME  varchar(30) not null,
   LAB_BIKO  varchar(300),
   LAB_AUID  integer     not null,
   LAB_ADATE timestamp   not null,
   LAB_EUID  integer     not null,
   LAB_EDATE timestamp   not null,
   LAB_SORT  integer     not null,
  primary key(LAB_SID)
  );
