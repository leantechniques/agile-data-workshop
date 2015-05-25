Example of database migrations using Flyway

GETTING STARTED:
------------------------------------
MySQL commands
------------------------------------
$ mysql -u root -p
mysql> create database customer_dev;
mysql> grant all on customer_dev.* to agiledba@localhost identified by 'p@ssw0rd';

LAB EXERCISES:
 1.) Add new migration to indicate preferred customers
    - the view should only display preferred customers
    - existing customers are preferred
 2.) Extract Addresses to support multiple addresses
    - keep the contract the same for the view
    - existing addresses are considered as primary address
 3.) Execute migrations on a separate database using Command-line Tool
    - initialize a new database
    - run migrations
    - verify no dev test data leaked over to the next environment

$ mysql -u root -p
mysql> create database customer_test;
mysql> grant all on customer_test.* to agiledba@localhost identified by 'pa$$w3!rd';

flyway info
flyway clean
flyway baseline
flyway migrate -target=3
flyway migrate


$ mysql -u root -p
mysql> create database customer_prod;
mysql> grant all on customer_prod.* to agiledba@localhost identified by 'S3CR3T';
