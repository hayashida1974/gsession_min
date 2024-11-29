create table CMN_BACKUP_CONF
(
     BUC_INTERVAL    integer        not null,
     BUC_DOW         integer,
     BUC_WEEK_MONTH  integer,
     BUC_GENERATION  integer        not null,
     BUC_ZIPOUT      integer        not null,
     BUC_AUID        integer        not null,
     BUC_ADATE       timestamp     not null,
     BUC_EUID        integer        not null,
     BUC_EDATE       timestamp     not null
) ;
