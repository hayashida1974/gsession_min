 create table CMN_LOGIN_HISTORY
   (
     USR_SID        integer,
     CLH_TERMINAL   integer,
     CLH_IP         varchar(50),
     CLH_CAR        integer,
     CLH_UID        varchar(50),
     CLH_AUID       integer not null,
     CLH_ADATE      timestamp not null,
     CLH_EUID       integer not null,
     CLH_EDATE      timestamp not null
   );

