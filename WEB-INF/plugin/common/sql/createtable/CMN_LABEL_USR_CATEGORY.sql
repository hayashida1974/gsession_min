 create table CMN_LABEL_USR_CATEGORY
 (
   LUC_SID    integer     not null ,
   LUC_NAME   varchar(20) not null,
   LUC_BIKO   varchar(300),
   LUC_SORT   integer     not null,
   LUC_AUID   integer     not null,
   LUC_ADATE  timestamp   not null,
   LUC_EUID   integer     not null,
   LUC_EDATE  timestamp   not null,
   primary key(LUC_SID)
   );