create table USR_PCONF
(
        USR_SID         integer  not null,
        UPC_MAX_DSP     integer not null,
        UPC_AUID        integer not null,
        UPC_ADATE       timestamp not null,
        UPC_EUID        integer not null,
        UPC_EDATE       timestamp not null,
        primary key (
                USR_SID
        ) 
);