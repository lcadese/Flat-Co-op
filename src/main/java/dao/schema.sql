--drop table if exists payment;
--drop table if exists assigned;
--drop table if exists task;

--alter table if exists users
--drop constraint flat_users_fk;

--drop table if exists flat;
--drop table if exists users;

create table if not exists users
(
 userID varchar(50) not null,
 username varchar(30) not null,
 password varchar(30) not null,
 lastName varchar(50) not null,
 firstName varchar(50) not null,
 Email varchar(100),
 flatID varchar(50),
 constraint users_pk primary key (userID)
);

create table if not exists flat
(
 flatID varchar(50) not null,
 name varchar(50) not null,
 address varchar(200) not null,
 host varchar(50) not null,
 constraint flat_pk primary key (flatID),
 constraint users_flat_fk foreign key (host) references users(userID)
);

alter table users
add constraint flat_users_fk foreign key (flatID) references flat(flatID);

create table if not exists task
(
 taskID varchar(50),
 taskName varchar(50) not null,
 description text not null,
 flatID varchar(50) not null,
 completed bool not null,
 requestedDate timestamp not null,
 constraint task_pk primary key (taskID),
 constraint task_flat_fk foreign key (flatID) references flat(flatID)
);

create table if not exists assigned
(
 taskID varchar(50),
 userID varchar(50),
 constraint assigned_pk primary key (taskID,userID),
 constraint assigned_users_fk foreign key (userID) references users(userID),
 constraint assigned_task_fk foreign key (taskID) references task(taskID)
);

create table if not exists payment
(
 paymentID varchar(50),
 userID varchar(50),
 amount Numeric(10),
 payed bool not null,
 description varchar(100),
 constraint payment_pk primary key (paymentID),
 constraint payment_users_fk foreign key (userID) references users(userID)
);


-- Inserting Users without flatID
INSERT INTO users (userID, username, password, lastName, firstName, Email) VALUES
('user1', 'johnDoe', 'pass123', 'Doe', 'John', 'john.doe@example.com'),
('user2', 'janeDoe', 'pass456', 'Doe', 'Jane', 'jane.doe@example.com');

-- Inserting Flats
INSERT INTO flat (flatID, address, host, name) VALUES
('flat1', '1234 Main St, Dunedin, NZ', 'user1','name1'),
('flat2', '5678 Side St, Dunedin, NZ', 'user2','name2');

-- Updating Users to add flatID
UPDATE users SET flatID = 'flat1' WHERE userID = 'user1';
UPDATE users SET flatID = 'flat2' WHERE userID = 'user2';

-- Inserting Tasks
INSERT INTO task (taskID, taskName, description, flatID, completed, requestedDate) VALUES
('task1', 'Fix Leak', 'Fix the leaking sink in the bathroom.', 'flat1', false, '2024-04-23'),
('task2', 'Clean Living Room', 'Give the living room a good clean', 'flat2', true, '2024-04-22');

-- Inserting Assignments
INSERT INTO assigned (taskID, userID) VALUES
('task1', 'user1'),
('task2', 'user2');

-- Inserting Payments
INSERT INTO payment (paymentID, userID, amount, payed, description) VALUES
('payment1', 'user1', 100.00, FALSE, 'This is for John'),
('payment2', 'user2', 150.00, TRUE, 'Please pay Keith');