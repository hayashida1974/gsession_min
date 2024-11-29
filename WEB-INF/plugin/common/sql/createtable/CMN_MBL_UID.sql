 create table CMN_MBL_UID
   (
     USR_SID     integer not null,
     CMU_UID_1   varchar(50),
     CMU_UID_2   varchar(50),
     CMU_UID_3   varchar(50),
     CMU_AUID    integer not null,
     CMU_ADATE   timestamp not null,
     CMU_EUID    integer not null,
     CMU_EDATE   timestamp not null,
     primary key (USR_SID)
   );

