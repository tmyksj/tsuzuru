create table em_user_scope
(
    value              varchar(255) not null,
    created_date       timestamp(6) not null default 0,
    last_modified_date timestamp(6) not null default 0,
    primary key (value)
) engine = InnoDB
  default charset utf8mb4;

alter table tbl_user
    add column profile_name        varchar(255) after password_encrypted,
    add column em_user_scope_value varchar(255) after profile_name,
    add foreign key (em_user_scope_value) references em_user_scope (value) on delete restrict on update cascade;

insert em_user_scope values
    ('Private', current_timestamp(), current_timestamp()),
    ('Public',  current_timestamp(), current_timestamp());

update tbl_user set
    profile_name        = name,
    em_user_scope_value = 'Private';

alter table tbl_user
    modify column profile_name        varchar (255) not null,
    modify column em_user_scope_value varchar (255) not null;
