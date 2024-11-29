create table CMN_PLUGIN_PARAM
(
         CUP_PID         varchar(10)      not null,
         CPP_NUM         integer      not null,
         CPP_NAME        varchar(100)     not null,
         CPP_VALUE       varchar(1000),
         primary key (CUP_PID, CPP_NUM)
);