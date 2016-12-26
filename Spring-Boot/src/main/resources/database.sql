create table tbl_user (id bigint not null auto_increment, first_name varchar(128), last_name varchar(128), password longtext, username varchar(64), primary key (id));
create table tbl_user_role (id integer not null auto_increment, role_name varchar(128) not null, user_id bigint, primary key (id));
alter table tbl_user add constraint UK_k0bty7tbcye41jpxam88q5kj2 unique (username);
alter table tbl_user_role add constraint UK_k0ijfnxd35ym2mj3fniha0spw unique (role_name);
alter table tbl_user_role add constraint FKggc6wjqokl2vlw89y22a1j2oh foreign key (user_id) references tbl_user (id);

insert into tbl_user(first_name,last_name,password,username) values('naveen','kumar','e691cb702f5d25642aa87009ef1948f8','naveen');
insert into tbl_user_role(user_id,role_name) values(1,'ROLE_ADMIN');