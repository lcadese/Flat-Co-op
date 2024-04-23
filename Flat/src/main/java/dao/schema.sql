drop table if exists payment;
drop table if exists assigned;
drop table if exists task;

alter table if exists users
drop constraint flat_users_fk;

drop table if exists flat;
drop table if exists users;

create table if not exists users
(
 userID varchar(30) not null,
 username varchar(30) not null,
 password varchar(30) not null,
 lastName varchar(50) not null,
 firstName varchar(50) not null,
 Email varchar(100),
 flatID varchar(10),
 constraint users_pk primary key (userID)
);

create table if not exists flat
(
 flatID varchar(10) not null,
 address varchar(200) not null,
 name varchar(50) not null,
 host varchar(10) not null,
 constraint flat_pk primary key (flatID),
 constraint users_flat_fk foreign key (host) references users(userID)
);

alter table users
add constraint flat_users_fk foreign key (flatID) references flat(flatID);

create table task
(
 taskID varchar(10),
 taskName varchar(50) not null,
 description text not null,
 flatID varchar(10) not null,
 completed bool not null,
 requestedDate timestamp not null,
 constraint task_pk primary key (taskID),
 constraint task_flat_fk foreign key (flatID) references flat(flatID)
);

create table if not exists assigned
(
 taskID varchar(10),
 userID varchar(30),
 constraint assigned_pk primary key (taskID,userID),
 constraint assigned_users_fk foreign key (userID) references users(userID),
 constraint assigned_task_fk foreign key (taskID) references task(taskID)
);

create table if not exists payment
(
 taskID varchar(10),
 userID varchar(30),
 amount Numeric(10),
 payed bool not null,
 constraint payment_pk primary key (taskID,userID),
 constraint payment_users_fk foreign key (userID) references users(userID),
 constraint payment_task_fk foreign key (taskID) references task(taskID)
);

-- INSERT INTO users (userid,username,password,lastname,firstname,email,flatid)
-- VALUES ('1','Dave is best','God','Dave','Dave','Dave@gmail.com',null)