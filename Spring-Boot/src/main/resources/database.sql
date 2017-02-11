
insert into tbl_user(first_name,last_name,password,username) values('naveen','kumar','$2a$10$nwi2xHRKegHZ0Dl6Rz/fDeTLa/dD/K4ivbFpo1ULzUVzeGfub6Zmm','naveen');
insert into tbl_role(role_name) values('ROLE_ADMIN');
insert into tbl_user_role(user_id,role_id,active_date) values(1,1,now());