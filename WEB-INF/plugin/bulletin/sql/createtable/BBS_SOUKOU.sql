create table BBS_SOUKOU
(
		BSK_SID				integer    not null,
		BFI_SID				integer    not null,
		BTI_SID				integer    not null,
		BSK_SOUKOU_TYPE		integer    not null,
		BSK_TITLE      		varchar(150),
		BSK_IMPORTANCE		integer,
		BSK_TYPE   		integer    not null,
		BSK_VALUE   		text,
		BSK_VALUE_PLAIN		text,
		BSK_LIMIT			integer not null,
		BSK_LIMIT_FR_DATE	timestamp,
		BSK_LIMIT_DATE		timestamp,
		BSK_AGID			integer,
		BSK_AUID			integer not null,
		BSK_ADATE			timestamp not null,
		BSK_EUID			integer not null,
		BSK_EDATE			timestamp not null,
		primary key (BSK_SID)
) ;