create table CMN_LOG
(
        LOG_DATE        timestamp      not null,
        USR_SID         integer         not null,
        LOG_LEVEL       varchar(5)      not null,
        LOG_PLUGIN      varchar(20)     ,
        LOG_PLUGIN_NAME varchar(50)     ,
        LOG_PG_ID       varchar(100)    ,
        LOG_PG_NAME     varchar(300)    ,
        LOG_OP_CODE     varchar(10)     ,
        LOG_OP_VALUE    varchar(3000)   ,
        LOG_IP          varchar(40)     ,
        VER_VERSION     varchar(60)     ,
        LOG_CODE        varchar(100)
);