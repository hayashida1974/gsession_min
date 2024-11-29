 create table CMN_PCONF_EDIT
   (
     CPE_PCONF_KBN    integer not null,
     CPE_PASSWORD_KBN integer not null,
     CPE_AUID         integer not null,
     CPE_ADATE        timestamp not null,
     CPE_EUID         integer not null,
     CPE_EDATE        timestamp not null,
     CPE_OTPSEND_KBN  integer not null
   );
