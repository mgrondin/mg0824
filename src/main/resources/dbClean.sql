drop table if exists RentalAgreements;
drop table if exists Customers;
drop table if exists Prices;
drop table if exists Tools;

CREATE TABLE Tools (
    ToolCode VARCHAR(4) NOT NULL PRIMARY KEY,
    ToolType VARCHAR(50) NOT NULL ,
    Brand VARCHAR(50) NOT NULL ,     
    available INT NOT NULL DEFAULT 0
    --I have chosen to "available" to represent the number of availible units.
);

CREATE TABLE Prices (
    ToolType VARCHAR(50) NOT NULL PRIMARY KEY REFERENCES Tools(ToolType),
    DailyChargeCents INT NOT NULL,
    WeekdayCharge BOOL NOT NULL,
    WeekendCharge BOOL NOT NULL,
    HolidayCharge BOOL NOT NULL  
);

CREATE TABLE Customers(
	CustomerID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	FirstName VARCHAR(50) NOT NULL,
	LastName VARCHAR(50) NOT NULL,
	Email VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE RentalAgreements(
	RentalAgreementID INTEGER PRIMARY KEY AUTOINCREMENT,
	CustomerID INTEGER NOT NULL REFERENCES Customers(CustomerID),
	ToolCode VARCHAR(4) REFERENCES Tools(ToolCode),
	RentalDays INT NOT NULL,
	CheckOutDate DATE NOT NULL,
	DueDate DATE NOT NULL,
	--DueDate is calculated from CheckOutDate and RentalDays, so this is redundant.
	-- However, I have chosen to store it because it is likley we will want to search by dueDate in the future,  
	-- and omiting this info would require a more complex SQL/PSQL to calculate dueDate at search time
	DailyChargeCents INT NOT NULL,
	--Note: "DailyChargeCents" is stored instead of looked up from the Prices table
	-- becuase an existing rental agreement should not change if the price of a tool changes
	ChargeDays INT NOT NULL,
	--Note: Simalar to "DailyChargeCents", the count of chargable days should not change
	-- in the event a new holiday is added or a tool policy/price changes
	PreDiscountChargeCents INT NOT NULL,
	DiscountPercent INT NOT NULL DEFAULT 0,
	--Note: there is no need to store "Discount Amount" 
	-- because this info only needs to be printed in reports and is easy to calculate
	FinalChargeCents INT NOT NULL
	--Technically "FinalChargeCents" is redundant data because we have "PreDiscountChargeCents"
	-- but I have chosen to store it because it is likley that in the future we might want to seach
	-- this database by either the final amount or the sub total.
	-- Finally that means the percent is redundant too, but it is probibly best to store that value
	-- explicity as it is something the customer agreed to.  In the event these values dont add up, best to have a record of it.
);

INSERT into Tools (ToolCode, ToolType  , Brand  ,available)
Values            ("CHNS"  , "Chainsaw", "Stihl",4);

INSERT into Tools (ToolCode, ToolType, Brand   ,available)
Values            ("LADW"  , "Ladder", "Werner",7);

INSERT into Tools (ToolCode, ToolType    , Brand   ,available)
Values            ("JAKD"  , "Jackhammer", "DeWalt",2);

INSERT into Tools (ToolCode, ToolType    , Brand   ,available)
Values            ("JAKR"  , "Jackhammer", "Ridgid",1);


INSERT into Prices (ToolType, DailyChargeCents, WeekdayCharge, WeekendCharge, HolidayCharge)
Values             ("Ladder", 199       , True         , True         , False        );

INSERT into Prices (ToolType  , DailyChargeCents, WeekdayCharge, WeekendCharge, HolidayCharge)
Values             ("Chainsaw", 149       , True         , false         ,True          );

INSERT into Prices (ToolType    , DailyChargeCents, WeekdayCharge, WeekendCharge, HolidayCharge)
Values             ("Jackhammer", 299       , True         , False        , False        );

INSERT into Customers(FirstName, LastName, Email)
Values               ("Jon"    , "Doe"   , "jon.doe@likesTools.com");







