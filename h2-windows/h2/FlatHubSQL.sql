drop table if exists payment;
drop table if exists assigned;
drop table if exists task;

alter table if exists users
drop constraint flat_users_fk;

drop table if exists flat;
drop table if exists users;

create table users
(
 userID char(10) not null,
 familyName char(50) not null,
 fistName char(50) not null,
 phoneNumber char(20),
 Email char(100),
 dateOfBirth date not null,
 flatID char(10),
 constraint users_pk primary key (userID)
);

create table flat
(
 flatID char(10) not null,
 location char(200) not null,
 name char(50) not null,
 host char(10) not null,
 constraint flat_pk primary key (flatID),
 constraint users_flat_fk foreign key (host) references users(userID)
);

alter table users
add constraint flat_users_fk foreign key (flatID) references flat(flatID);

create table task
(
 taskID char(10),
 taskName char(50) not null,
 description text not null,
 flatID char(10) not null,
 requestedDate timestamp not null,
 constraint task_pk primary key (taskID),
 constraint task_flat_fk foreign key (flatID) references flat(flatID)
);

create table assigned
(
 taskID char(10),
 userID char(10),
 completed bool not null,
 constraint assigned_pk primary key (taskID,userID),
 constraint assigned_users_fk foreign key (userID) references users(userID),
 constraint assigned_task_fk foreign key (taskID) references task(taskID)
);

create table payment
(
 taskID char(10),
 userID char(10),
 amount Numeric(10),
 played bool not null,
 constraint payment_pk primary key (taskID,userID),
 constraint payment_users_fk foreign key (userID) references users(userID),
 constraint payment_task_fk foreign key (taskID) references task(taskID)
)