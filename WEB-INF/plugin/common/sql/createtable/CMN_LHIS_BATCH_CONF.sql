 create table CMN_LHIS_BATCH_CONF
   (
     CBC_ADL_KBN      integer not null,
     CBC_ADL_YEAR     integer,
     CBC_ADL_MONTH    integer,
     CBC_AUID         integer not null,
     CBC_ADATE        timestamp not null,
     CBC_EUID         integer not null,
     CBC_EDATE        timestamp not null
   );

