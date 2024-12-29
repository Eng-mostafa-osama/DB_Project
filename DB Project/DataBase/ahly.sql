create database Ahly;
use ahly;
-- Table: User
CREATE TABLE User (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    UFname VARCHAR(25) NOT NULL,
    ULname VARCHAR(25) NOT NULL,
    UAge INT NOT NULL,
    UPhoneNumber VARCHAR(15),
    UEmail VARCHAR(40)
);
-- Table: Teams
CREATE TABLE Teams (
    TeamID INT AUTO_INCREMENT PRIMARY KEY,
    TName VARCHAR(50) NOT NULL
);

-- Table: Members
CREATE TABLE Members (
    MemberID INT AUTO_INCREMENT PRIMARY KEY,
    MEmail VARCHAR(40) NOT NULL,
    MfName VARCHAR(25) NOT NULL,
    MlName VARCHAR(25),
    MSubscriptions VARCHAR(20) default 'Not Subscriped',
    MPhoneNumber VARCHAR(15),
    TeamID INT,
    FOREIGN KEY (TeamID) REFERENCES Teams(TeamID)
);

-- Table: Owner
CREATE TABLE Owner (
    OwnerID INT AUTO_INCREMENT PRIMARY KEY,
    OName VARCHAR(25) NOT NULL,
    OPhone VARCHAR(15)
);

-- Table: Subscriptions
CREATE TABLE Subscriptions (
    SubscriptionID INT AUTO_INCREMENT PRIMARY KEY,
    SStartDate DATE NOT NULL,
    SEndDate DATE NOT NULL,
    SPlanType VARCHAR(50) default 'Monthly',
    SAmount DECIMAL(10, 2),
    UserID INT,
    MemberID INT,
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    FOREIGN KEY (MemberID) REFERENCES Members(MemberID)
);

-- Table: Expenses
CREATE TABLE Expenses (
    ExpenseID INT AUTO_INCREMENT PRIMARY KEY,
    EExpenseType VARCHAR(50),
    EAmount DECIMAL(10, 2),
    OwnerID INT,
    TeamID INT,
    FOREIGN KEY (OwnerID) REFERENCES Owner(OwnerID),
    FOREIGN KEY (TeamID) REFERENCES Teams(TeamID)
);

alter table teams
add ExpenseID int,
add FOREIGN KEY (ExpenseID) REFERENCES Expenses(ExpenseID) ;

-- Table: Team Members
CREATE TABLE TeamMembers (
    Role VARCHAR(50) ,
    TeamID INT,
    MemberID INT,
    PRIMARY KEY (TeamID, MemberID),
    FOREIGN KEY (TeamID) REFERENCES Teams(TeamID),
    FOREIGN KEY (MemberID) REFERENCES Members(MemberID)
);

-- Table: Training Sessions
CREATE TABLE TrainingSessions (
    SessionID INT AUTO_INCREMENT PRIMARY KEY,
    TRDuration int NOT NULL,
    TRDate DATE NOT NULL,
    TeamID INT,
    FOREIGN KEY (TeamID) REFERENCES Teams(TeamID)
);

-- Table: Coaches
CREATE TABLE Coaches (
    CoachID INT AUTO_INCREMENT PRIMARY KEY,
    CEmail VARCHAR(50),
    CfName VARCHAR(40),
    ClName VARCHAR(40),
    CPhone VARCHAR(15),
    CAge INT,
    TeamID INT,
    FOREIGN KEY (TeamID) REFERENCES Teams(TeamID)
);

-- Table: Manages
CREATE TABLE Manages (
    OwnerID INT,
    TeamID INT,
    PRIMARY KEY (OwnerID, TeamID),
    FOREIGN KEY (OwnerID) REFERENCES Owner(OwnerID),
    FOREIGN KEY (TeamID) REFERENCES Teams(TeamID)
);

alter table user
add constraint chk_Uage
check (Uage>0);

alter table Coaches
add constraint chk_Cage
check (Cage>0);

alter table Subscriptions
add constraint chk_Samount
check (Samount>0);

alter table expenses
add constraint chk_Eamount
check (Eamount>0);
alter table user 
rename users;
alter table users
add column UPassword varchar(35);
alter table users
add column Uusername varchar(35)
 after ULname;
 
 insert into users(UFname,ULname,Uusername,Uage,UPhoneNumber,UEmail,UPassword)
values ('mostafa',
'osama',
'darsh',
20,
01155424909,
'mostafaosama3060@gmail.com',
'12345678910');
alter table users
add isOwner bool default 0;
update  users 
set isOwner = 1 where UserID =1 ;