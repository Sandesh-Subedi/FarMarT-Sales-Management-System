# Computer Science II
# Assignment 4 - Project Phase III
# Queries
#
# Name(s): Sandesh Subedi
#		   Darius Banks
# This file contains bunch of queries to pull out the data 
# from the database applying certain condiutions

# A query to retrieve the main attributes of each person (their code, and last/first name).
select personCode, firstName, lastName
from Person;

# A query to retrieve the major fields for every person including their address (but excluding emails).
select Person.personCode, Person.firstName, Person.lastName, Person.addressId
from Person;

# A query to get the email addresses of a specific person.
select Email.emailId,emailAddress
from Email
where Email.personId = 2;

# A query to change the email address of a specific email record.
update Email
set emailAddress = "ktm@gmail.com"
where emailId = 7;

# A query (or series of queries) to remove a specific person record.
#   Deleting person table that includes deleting every stuffs that are 
#   linked with the keys in the parent and child tables:
delete from Email where Email.personId = 2;
delete from InvoiceItem where invoiceId = (select invoiceId from Invoice where salesPersonId = 2);
delete from InvoiceItem where invoiceId = (select invoiceId from Invoice where customerId = 2);
delete from Invoice where Invoice.salesPersonId = 2;
delete from Invoice where Invoice.customerId = 2;
delete from Store where Store.managerId = 2;
delete from Manager where Manager.personId = 2;
delete from Person where Person.personId = 2;

# A query to get all the items on a specific invoice record.
select Invoice.invoiceId, Item.name as itemName
from Invoice
Join InvoiceItem on Invoice.invoiceId = InvoiceItem.invoiceId
Join Item on InvoiceItem.itemId = Item.itemId
where Invoice.invoiceId = 3;

# A query to get all the items purchased by a specific person.
select Invoice.invoiceId, COUNT(InvoiceItem.invoiceItemId) as ItemsPurchasedBySpecificPerson
from InvoiceItem
join Invoice ON InvoiceItem.invoiceId = Invoice.invoiceId
where Invoice.customerId = 3;

# A query to find the total number of sales made at each store.
select Store.storeId, COUNT(Invoice.invoiceId) as totalSales
from Invoice
join Store on Invoice.storeId = 2;

# A query to find the total number of sales made by each employee.
select Invoice.salesPersonId, Person.firstName, Person.lastName, COUNT(Invoice.invoiceId)
as totalSalesByEmployee
from Invoice 
join Person on Invoice.salesPersonId = Person.personId group by salesPersonId;

# A query to find the subtotal charge of all products in each invoice .
select Invoice.invoiceId, Item.type, SUM(quantityPurchase * Item.unitPrice)
as subTotal
from InvoiceItem
join Item on InvoiceItem.itemId = Item.itemId
join Invoice on InvoiceItem.invoiceId = Invoice.invoiceId where Item.type = "P" group by invoiceId;

# A query to detect invalid data in invoice as follows. In a single invoice, 
#    a particular product should only appear once since any number of units can 
#    be consolidated to a single record.
select Invoice.invoiceId, Item.name, COUNT(Invoice.invoiceId)
from Invoice
join InvoiceItem ON Invoice.invoiceId = InvoiceItem.invoiceId
join Item ON Item.itemId= InvoiceItem.itemId
where  Item.itemType = "P" group by Invoice.invoiceId having count(*) > 1;

# A query to detect a potential instance of fraud where an employee makes 
#    a sale to themselves (the same person is the sales person as well as the customer).
select Invoice.salesPersonId, Invoice.customerId, Invoice.invoiceCode
from Invoice
where Invoice.salesPersonId = customerId;