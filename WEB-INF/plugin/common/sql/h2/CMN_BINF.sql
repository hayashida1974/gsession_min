create table CMN_BINF
(
     BIN_SID            bigint         not null,
     BIN_FILE_NAME      varchar(255)    not null,
     BIN_FILE_PATH      varchar(30)    not null,
     BIN_FILE_EXTENSION varchar(20),
     BIN_FILE_SIZE      bigint         not null,
     BIN_FILEKBN        integer        not null,
     BIN_ADUSER         integer        not null,
     BIN_ADDATE         timestamp     not null,
     BIN_UPUSER         integer        not null,
     BIN_UPDATE         timestamp     not null,
     BIN_JKBN           integer        not null,
     primary key (BIN_SID)
);