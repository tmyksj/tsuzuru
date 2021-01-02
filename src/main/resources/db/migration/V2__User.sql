create table em_user_role
(
    value              varchar(255) not null,
    created_date       timestamp(6) not null default 0,
    last_modified_date timestamp(6) not null default 0,
    primary key (value)
) engine = InnoDB
  default charset utf8mb4;

create table tbl_user
(
    uuid               varchar(36)  not null,
    name               varchar(255) not null,
    password_encrypted varchar(255) not null,
    created_date       timestamp(6) not null default 0,
    last_modified_date timestamp(6) not null default 0,
    primary key (uuid),
    unique key (name)
) engine = InnoDB
  default charset utf8mb4;

create table tbl_user_role
(
    tbl_user_uuid      varchar(36)  not null,
    em_user_role_value varchar(255) not null,
    created_date       timestamp(6) not null default 0,
    last_modified_date timestamp(6) not null default 0,
    primary key (tbl_user_uuid, em_user_role_value),
    foreign key (tbl_user_uuid) references tbl_user (uuid) on delete cascade on update cascade,
    foreign key (em_user_role_value) references em_user_role (value) on delete cascade on update cascade
) engine = InnoDB
  default charset utf8mb4;
