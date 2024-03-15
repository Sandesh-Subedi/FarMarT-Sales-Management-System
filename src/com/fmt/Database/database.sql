# Computer Science II 
# FarMarT Sales Management System
# Assignment 4 - Project Phase III
#
# Name(s): Sandesh Subedi
#	       Darius Banks
# Date: 2023-03-31

# This is a database file for assignment 4. We created the tables 
# and inserted datas so that we can use those data from the tables 
# without using the csv or any flat files for our project.

drop table if exists InvoiceItem;
drop table if exists Item;
drop table if exists Invoice;
drop table if exists Store ;
drop table if exists Email;
drop table if exists Manager;
drop table if exists Person;
drop table if exists Address;

create table Address(
addressId int not null primary key auto_increment,
street varchar(50) not null,
city varchar(50) not null,
state varchar(50) not null,
zipCode varchar(50) not null,
country varchar(50) not null
);


create table Person(
personId int not null primary key auto_increment,
personCode varchar(50) not null unique,
lastName varchar(50) not null,
firstName varchar(50) not null,
addressId int not null,
foreign key (addressId) references Address(addressId)
);

create table Manager(
managerId int not null primary key auto_increment,
personId int not null,
foreign key (personId) references Person(personId)
);

create table Email(
emailId int not null primary key auto_increment,
emailAddress varchar(50) not null,
personId int not null,
foreign key (personId) references Person (personId)
);

create table Store(
storeId int not null primary key auto_increment,
storeCode varchar(50) not null,
managerId int not null,
addressId int not null,
foreign key (managerId) references Manager(managerId),
foreign key (addressId) references Address(addressId)
);

create table Invoice(
invoiceId int not null primary key auto_increment,  
invoiceCode varchar(50) not null,
invoiceDate varchar(50) not null,
customerId int not null,
salesPersonId int not null,
storeId int not null,
foreign key (salesPersonId) references Manager(managerId),
foreign key (customerId) references Person(personId),
foreign key (storeId) references Store(storeId)
);

create table Item(
itemId int not null primary key auto_increment,
itemCode varchar(50),
type varchar(50) not null,
name varchar(50) not null,
model varchar(50),
unit varchar(50),
unitPrice double ,
hourlyRate double
);

create table InvoiceItem(
invoiceItemId int not null primary key auto_increment,
invoiceId int not null,
itemId int not null,
purchasePrice double,
hoursBilled double,
fee double,
startLeaseDate varchar(50),
endLeaseDate varchar(50),
quantityPurchase double,
foreign key (invoiceId) references Invoice(invoiceId),
foreign key (itemId) references Item(itemId)
);

#Insert statements: 
#--------------------------------------------------------------------------------------------------------------------------------

# Inserting to Address
insert into Address (addressId, street, city, state, zipCode, country) values (1, "13", "Lincoln", "Nebraska", "68503", "US");
insert into Address (addressId, street, city, state, zipCode, country) values (2, "16", "Lincoln", "Nebraska", "68511", "US");
#--------------------------------------------------------------------------------------------------------------------------------

# Inserting to Person
insert into Person (personId, personCode, lastName, firstName, addressId) values (1, "fdc278", "Subedi", "Sandesh", 1);
insert into Person (personId, personCode, lastName, firstName, addressId) values (2, "bb887a", "Banks", "Darius", 1);
insert into Person (personId, personCode, lastName, firstName, addressId) values (3, "b34bb6", "Pudasaini", "Irish", 2);
insert into Person (personId, personCode, lastName, firstName, addressId) values (4, "23r562", "Raut", "Dipen", 2);
#--------------------------------------------------------------------------------------------------------------------------------

# Inserting to Manager
insert into Manager (managerId, personId) values (1, 1);
insert into Manager (managerId, personId) values (2, 2);
insert into Manager (managerId, personId) values (3, 3);
insert into Manager (managerId, personId) values (4, 4);
#--------------------------------------------------------------------------------------------------------------------------------

# Inserting to Email
insert into Email (emailId, emailAddress, personId) values (1, "sandeshsubedi324@gmail.com", 1);
insert into Email (emailId, emailAddress, personId) values (2, "subedisandesh@gmail.com", 1);
insert into Email (emailId, emailAddress, personId) values (3, "subedisash@gmail.com", 2);
insert into Email (emailId, emailAddress, personId) values (4, "irishpuda@gmail.com", 3);
insert into Email (emailId, emailAddress, personId) values (5, "pudairi@gmail.com", 3);
insert into Email (emailId, emailAddress, personId) values (6, "dipenraute6@gmail.com", 4);
insert into Email (emailId, emailAddress, personId) values (7, "dipen32@gmail.com", 4);
#--------------------------------------------------------------------------------------------------------------------------------

# Inserting to Store
insert into Store (storeId, storeCode, managerId, addressId) values (1, "2z0434", 2, 1);
insert into Store (storeId, storeCode, managerId, addressId) values (2, "83668b", 3, 2);
insert into Store (storeId, storeCode, managerId, addressId) values (3, "32451g", 4, 2);
#--------------------------------------------------------------------------------------------------------------------------------

# Inserting to Invoice
insert Invoice (invoiceId, invoiceCode, invoiceDate, customerId, salesPersonId, storeId) values (1, "INV010", "2023-03-01", 1, 2, 1);
insert Invoice (invoiceId, invoiceCode, invoiceDate, customerId, salesPersonId, storeId) values (2, "INV011", "2023-03-02", 2, 3, 2);
insert Invoice (invoiceId, invoiceCode, invoiceDate, customerId, salesPersonId, storeId) values (3, "INV012", "2023-03-03", 3, 3, 3);
insert Invoice (invoiceId, invoiceCode, invoiceDate, customerId, salesPersonId, storeId) values (4, "INV013", "2023-03-04", 4, 3, 3);
insert Invoice (invoiceId, invoiceCode, invoiceDate, customerId, salesPersonId, storeId) values (5, "INV014", "2023-03-05", 3, 4, 3);
#--------------------------------------------------------------------------------------------------------------------------------

# Inserting to Item
insert Item (itemId, itemCode, type, name, model, unit, unitPrice, hourlyRate) values (1,"2d3452", "E", "Truck", "F203", null, null, null);
insert Item (itemId, itemCode, type, name, model, unit, unitPrice, hourlyRate) values (2,"n34n67j", "P", "Fertilizer", null, "liter", 13.25, null);
insert Item (itemId, itemCode, type, name, model, unit, unitPrice, hourlyRate) values (3,"230hb23", "S", "Delivery", null, null, null, 80);
insert Item (itemId, itemCode, type, name, model, unit, unitPrice, hourlyRate) values (4,"2d3452", "E", "Harvester", "Husker 2000", null, null, null);
insert Item (itemId, itemCode, type, name, model, unit, unitPrice, hourlyRate) values (5,"n34n67j", "P", "Corn seed", null, "quinton", 400, null);
insert Item (itemId, itemCode, type, name, model, unit, unitPrice, hourlyRate) values (6,"230hb23", "S", "Plowing", null, null, null, 900);
#--------------------------------------------------------------------------------------------------------------------------------

# Inserting to InvoiceItem
insert InvoiceItem (invoiceItemId, invoiceId, itemId, purchasePrice, hoursBilled, fee, startLeaseDate, endLeaseDate, quantityPurchase) 
			values (1, 1, 1, 500.0, 5.0, 80.0, "2022-01-02", "2023-09-01", 5.0);
insert InvoiceItem (invoiceItemId, invoiceId, itemId, purchasePrice, hoursBilled, fee, startLeaseDate, endLeaseDate, quantityPurchase)
			values (2, 1, 2, 5000.0, null, null, null, null, 5.0);
insert InvoiceItem (invoiceItemId, invoiceId, itemId, purchasePrice, hoursBilled, fee, startLeaseDate, endLeaseDate, quantityPurchase) 
			values (3, 1, 3, null, 5.0, null, null, null, null);
insert InvoiceItem (invoiceItemId, invoiceId, itemId, purchasePrice, hoursBilled, fee, startLeaseDate, endLeaseDate, quantityPurchase) 
			values (4, 2, 4, 600.0, 5.0, 80.0, "2022-01-02", "2023-09-01", 5.0);
insert InvoiceItem (invoiceItemId, invoiceId, itemId, purchasePrice, hoursBilled, fee, startLeaseDate, endLeaseDate, quantityPurchase) 
			values (5, 2, 5, null, null, null, null, null, 6.0);
insert InvoiceItem (invoiceItemId, invoiceId, itemId, purchasePrice, hoursBilled, fee, startLeaseDate, endLeaseDate, quantityPurchase) 
			values (6, 3, 5, null, null, null, null, null, 4.0);
insert InvoiceItem (invoiceItemId, invoiceId, itemId, purchasePrice, hoursBilled, fee, startLeaseDate, endLeaseDate, quantityPurchase)
			values (7, 4, 5, null, null, null, null, null, 8.0);
insert InvoiceItem (invoiceItemId, invoiceId, itemId, purchasePrice, hoursBilled, fee, startLeaseDate, endLeaseDate, quantityPurchase) 
			values (8, 4, 5, null, null, null, null, null, 8.0);