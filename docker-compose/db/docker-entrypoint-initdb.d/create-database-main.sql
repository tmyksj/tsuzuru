create user if not exists 'tsuzuru'@'%' identified by 'my-secret-pw';
create database if not exists `tsuzuru_main`;
grant all on `tsuzuru_main`.* to 'tsuzuru'@'%';
