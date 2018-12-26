#Creating databases
create table users (
	id int (10) auto_increment,
	email char (40) not null,
    username char (20) not null,
    password char (64) not null,
	primary key (id)
);

create table admins (
	id int (10) auto_increment,
	email char (40) not null,
    username char (20) not null,
    password char (64) not null,
	primary key (id)
);

create table lobbies (
	id int (10) auto_increment,
    playerOne char(30),
	playerTwo char(30),
	playerThree char(30),
	playerFour char(30),
    primary key (id)
);

create table game (
	id int (10) auto_increment,
    playerOne char(30),
    playerOneHand char(255),
    playerOneValue int (10),
	playerTwo char(30),
    playerTwoHand char(255),
    playerTwoValue int (10),
	playerThree char(30),
    playerThreeHand char(255),
    playerThreeValue int (10),
	playerFour char(30),
    playerFourHand char(255),
    playerFourValue int (10),
    dealerHand char(255),
    dealerValue int (10),
    playerTurn int (10),
    primary key (id)
);

create table leaderboards (
	id int (10) auto_increment,
    username char(30),
    won int(10),
    games int(10),
    winrate int(10),
    primary key (id)
);

#Fill the users table with data
insert into users (email, username, password)
values ('tclhaddy@iastate.edu', 'Thomas', '5f4dcc3b5aa765d61d8327deb882cf99');

insert into users (email, username, password)
values ('tfuchs@iastate.edu', 'Tyler', '5f4dcc3b5aa765d61d8327deb882cf99');

insert into users (email, username, password)
values ('james@iastate.edu', 'James', '5f4dcc3b5aa765d61d8327deb882cf99');

insert into users (email, username, password)
values ('keatondj@iastate.edu', 'Keaton', '5f4dcc3b5aa765d61d8327deb882cf99');

#Fill the lobbies table with data
insert into lobbies (playerone, playertwo, playerthree, playerfour)
values ('empty', 'empty', 'empty', 'empty');

#Fill the Game instance
insert into game (
playerOne, 
playerOneHand, 
playerOneValue,
playerTwo, 
playerTwoHand, 
playerTwoValue,
playerThree, 
playerThreeHand, 
playerThreeValue,
playerFour, 
playerFourHand, 
playerFourValue,
dealerHand, 
dealerValue,
playerTurn)
values (
'empty', 
'Ace Spades, Two Clubs', 
0,
'empty', 
'Four Hearts, Ace Diamonds', 
0,
'empty', 
'Ten Hearts, Four Diamonds', 
0,
'empty', 
'Six Spades, Nine Hearts', 
0,
'King Clubs, Two Spades', 
0,
1
);

#Testing if tables were filled with values
select * from users;
select * from lobbies;
select * from game;
select * from leaderboards;

#Delete test user
DELETE FROM users where username = 'Keaton';

DELETE FROM game where id = 3;

Delete from users where username = "James";
