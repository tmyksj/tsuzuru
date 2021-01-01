create user if not exists 'tsuzuru'@'%' identified by 'my-secret-pw';
create database if not exists `tsuzuru_test`;
grant all on `tsuzuru_test`.* to 'tsuzuru'@'%';
