drop table T_WINERY if exists;

create table T_WINERY (ID bigint identity primary key, NAME varchar(255),
                        OWNER varchar(255) not null, BALANCE decimal(8,2), unique(NAME));
                        
ALTER TABLE T_WINERY ALTER COLUMN BALANCE SET DEFAULT 0.0;

