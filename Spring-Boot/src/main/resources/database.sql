
insert into tbl_user(first_name,last_name,password,username) values('naveen','kumar','e691cb702f5d25642aa87009ef1948f8','naveen');
insert into tbl_role(role_name) values('ROLE_ADMIN');
insert into tbl_user_role(user_id,role_id,active_date) values(1,1,now());