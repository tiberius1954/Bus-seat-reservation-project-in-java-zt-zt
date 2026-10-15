BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "adminuser" (
	"adid"	INTEGER,
	"username"	TEXT DEFAULT ' ',
	"password"	TEXT DEFAULT ' ',
	"phone"	TEXT DEFAULT ' ',
	PRIMARY KEY("adid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "buses" (
	"busid"	INTEGER,
	"busno"	TEXT DEFAULT ' ',
	"color"	TEXT DEFAULT ' ',
	"model"	TEXT DEFAULT ' ',
	"layid"	INTEGER DEFAULT 0,
	PRIMARY KEY("busid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "busgrid" (
	"gid"	INTEGER,
	"glid"	INTEGER DEFAULT 0,
	"col1"	TEXT DEFAULT ' ',
	"col2"	TEXT DEFAULT ' ',
	"col3"	TEXT DEFAULT ' ',
	"col4"	TEXT DEFAULT ' ',
	"col5"	TEXT DEFAULT ' ',
	"col6"	TEXT DEFAULT ' ',
	PRIMARY KEY("gid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "buslayout" (
	"blid"	INTEGER,
	"lname"	TEXT,
	"rows"	INTEGER,
	"columns"	INTEGER,
	"capacity"	INTEGER,
	"driver_position"	TEXT DEFAULT ' ',
	"path"	TEXT DEFAULT ' ',
	PRIMARY KEY("blid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "contactus" (
	"coid"	INTEGER DEFAULT 0,
	"name"	TEXT DEFAULT ' ',
	"email"	TEXT DEFAULT ' ',
	"message"	TEXT DEFAULT ' ',
	"date"	TEXT DEFAULT ' ',
	PRIMARY KEY("coid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "drivers" (
	"driverid"	INTEGER,
	"busid"	INTEGER DEFAULT 0,
	"Name"	TEXT DEFAULT ' ',
	"License_number"	TEXT DEFAULT ' ',
	"phone"	TEXT DEFAULT ' ',
	PRIMARY KEY("driverid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "employees" (
	"empid"	INTEGER,
	"name"	TEXT DEFAULT ' ',
	"phone"	TEXT DEFAULT ' ',
	"email"	TEXT DEFAULT ' ',
	"gender"	TEXT DEFAULT ' ',
	"dob"	TEXT DEFAULT ' ',
	"dateofjoin"	TEXT DEFAULT ' ',
	"address"	TEXT DEFAULT ' ',
	"roll"	TEXT DEFAULT ' ',
	PRIMARY KEY("empid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "feedback" (
	"fid"	INTEGER,
	"passid"	INTEGER DEFAULT 0,
	"schedid"	INTEGER DEFAULT 0,
	"rating"	INTEGER DEFAULT 0,
	"comments"	TEXT DEFAULT ' ',
	"fdate"	TEXT DEFAULT ' ',
	PRIMARY KEY("fid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "passenger" (
	"passid"	INTEGER,
	"pname"	TEXT DEFAULT ' ',
	"username"	TEXT DEFAULT ' ',
	"phone"	TEXT DEFAULT ' ',
	"email"	TEXT DEFAULT ' ',
	"gender"	TEXT DEFAULT ' ',
	"address"	TEXT DEFAULT ' ',
	"password"	TEXT DEFAULT ' ' UNIQUE,
	"regdate"	TEXT DEFAULT ' ',
	PRIMARY KEY("passid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "payment" (
	"payhid"	INTEGER,
	"total_amount"	NUMERIC DEFAULT 0,
	"payment_date"	TEXT DEFAULT ' ',
	"payment_method"	TEXT DEFAULT ' ',
	"card_name"	TEXT DEFAULT ' ',
	"card_number"	TEXT DEFAULT ' ',
	"cvv"	INTEGER DEFAULT 0,
	"exp_year"	INTEGER DEFAULT 0,
	"exp_month"	INTEGER DEFAULT 0,
	PRIMARY KEY("payhid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "payments" (
	"payrid"	INTEGER DEFAULT 0,
	"resid"	INTEGER DEFAULT 0,
	"amount"	NUMERIC DEFAULT 0.0,
	"payhid"	INTEGER DEFAULT 0,
	PRIMARY KEY("payrid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "routes" (
	"routeid"	INTEGER,
	"origin"	TEXT DEFAULT ' ',
	"destination"	TEXT DEFAULT ' ',
	"distance_km"	INTEGER DEFAULT 0,
	"duration"	TEXT DEFAULT ' ',
	UNIQUE("origin","destination"),
	PRIMARY KEY("routeid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "schedule" (
	"schedid"	INTEGER,
	"routeid"	INTEGER DEFAULT 0,
	"busid"	INTEGER DEFAULT 0,
	"departure_time"	TEXT DEFAULT ' ',
	"arrival_time"	TEXT DEFAULT ' ',
	"fare"	NUMERIC DEFAULT 0,
	"driverid"	INTEGER DEFAULT 0,
	PRIMARY KEY("schedid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "seatreservation" (
	"resid"	INTEGER,
	"passid"	INTEGER DEFAULT 0,
	"schedid"	INTEGER DEFAULT 0,
	"seatno"	TEXT DEFAULT ' ',
	"status"	TEXT DEFAULT ' ',
	"resdate"	TEXT DEFAULT ' ',
	"fare"	NUMERIC DEFAULT 0.0,
	PRIMARY KEY("resid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "ticket" (
	" tickid"	INTEGER,
	"resid"	INTEGER,
	"tdate"	TEXT,
	"amount"	NUMERIC DEFAULT 0,
	"ticketno"	TEXT,
	PRIMARY KEY(" tickid" AUTOINCREMENT)
);
INSERT INTO "adminuser" ("adid","username","password","phone") VALUES (2,'mary','mary','232222111');
INSERT INTO "adminuser" ("adid","username","password","phone") VALUES (3,'admin','admin','12233322');
INSERT INTO "buses" ("busid","busno","color","model","layid") VALUES (1,'bus1000','red','iveco',9);
INSERT INTO "buses" ("busid","busno","color","model","layid") VALUES (3,'Bus1001','Blue','Volvo',10);
INSERT INTO "buses" ("busid","busno","color","model","layid") VALUES (4,'Bus1002','Green','Scania',8);
INSERT INTO "buses" ("busid","busno","color","model","layid") VALUES (5,'Bus1003','White','Neoplan',11);
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (15,8,'1 ','2 ','','3 ','4 ',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (16,8,'5 ','6 ','','7 ','8 ',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (17,8,'9 ','10','  ','11','12',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (18,8,'13','14','','15','16',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (19,8,'17','18','','19','20',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (20,8,'21','22','','','',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (21,8,'23','24','','','',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (22,8,'25','26','','27','28',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (23,8,'29','30','','31','31',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (24,8,'33','34','','35','36',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (25,8,'37','38','','39','40',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (26,8,'41','42','','43','44',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (27,8,'45','46','47','48','49',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (28,9,'1 ','2 ','','','3 ','4 ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (29,9,'5 ','6 ','','','7 ','8 ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (30,9,'9 ','10','','','11','12');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (31,9,'13','14','','','15','16');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (32,9,'17','18','','','19','20');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (33,9,'21','22','','','23','24');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (34,9,'25','26','','','27','28');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (35,9,'29','30','','','31','32');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (36,9,'33','34','','','35','36');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (37,9,'37','38','','','39','40');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (38,9,'41','42','','','43','44');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (39,9,'45','46','','','47','48');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (40,9,'49','50','','','','');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (41,9,'51','52','53','54','55','56');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (42,10,'1 ','2 ','','3 ','4 ',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (43,10,'5 ','6 ','','7 ','8 ',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (44,10,'9 ','10','','11','12',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (45,10,'13','14','','15','16',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (46,10,'17','18','','19','20',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (47,10,'21','22','','23','24',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (48,10,'25','26','','27','28',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (49,10,'29','30','','31','32',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (50,10,'33','34','','35','36',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (51,10,'37','38','','39','40',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (52,10,'41','42','43','44','45',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (53,11,'1 ','2 ','','3 ','4 ',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (54,11,'5 ','6 ','','7 ','8 ',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (55,11,'9 ','10','','11','12',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (56,11,'13','14','','15','16',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (57,11,'17','18','','19','20',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (58,11,'21','22','  ','23','24',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (59,11,'25','26','','27','28',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (60,11,'29','30','','  ','  ',' ');
INSERT INTO "busgrid" ("gid","glid","col1","col2","col3","col4","col5","col6") VALUES (61,11,'31','32','33','34','35',' ');
INSERT INTO "buslayout" ("blid","lname","rows","columns","capacity","driver_position","path") VALUES (8,'L13-5',13,5,49,'Left','\seatmaps\lay13-5.png');
INSERT INTO "buslayout" ("blid","lname","rows","columns","capacity","driver_position","path") VALUES (9,'L14-6',14,6,56,'Left','\seatmaps\lay14-6.png');
INSERT INTO "buslayout" ("blid","lname","rows","columns","capacity","driver_position","path") VALUES (10,'L11-5',11,5,45,'Left','\seatmaps\lay11-5.png');
INSERT INTO "buslayout" ("blid","lname","rows","columns","capacity","driver_position","path") VALUES (11,'L9-5',9,5,35,'Left','\seatmaps\lay9-5.png');
INSERT INTO "drivers" ("driverid","busid","Name","License_number","phone") VALUES (1,1,'John Haris','1222111','1234567');
INSERT INTO "drivers" ("driverid","busid","Name","License_number","phone") VALUES (2,3,'Henry Fonda','12342211','98674122');
INSERT INTO "drivers" ("driverid","busid","Name","License_number","phone") VALUES (3,4,'Frank Gordon','9342378','24376542');
INSERT INTO "drivers" ("driverid","busid","Name","License_number","phone") VALUES (4,5,'Harrison Ford','12255221','65221122');
INSERT INTO "employees" ("empid","name","phone","email","gender","dob","dateofjoin","address","roll") VALUES (4,'Peter Falk','234455322','peter.falk@gmail.com','Male','2006-11-22','2026-10-14','budapest freedom square 10.','Ticketing staff');
INSERT INTO "employees" ("empid","name","phone","email","gender","dob","dateofjoin","address","roll") VALUES (5,'Julia Roberts','23377544','julia.robert@gmail.com','Female','1998-01-15','2026-10-14','Budapest Dozsa györgy út 11.','Conductor');
INSERT INTO "passenger" ("passid","pname","username","phone","email","gender","address","password","regdate") VALUES (6,'Jane Fonda','jane','122233333','Jane.fonda@gmail.com','Female','Budapest Mars street 20.','jane','2026-10-09');
INSERT INTO "passenger" ("passid","pname","username","phone","email","gender","address","password","regdate") VALUES (7,'Philip Moris','philip','14566888','philip.moris@gmail.com','Male','Budapest, mars street 9.','philip','2026-10-09');
INSERT INTO "payment" ("payhid","total_amount","payment_date","payment_method","card_name","card_number","cvv","exp_year","exp_month") VALUES (3,3000,'2026-09-29','Cash on delivery','','','','','');
INSERT INTO "payment" ("payhid","total_amount","payment_date","payment_method","card_name","card_number","cvv","exp_year","exp_month") VALUES (4,3000,'2026-09-29','Cash on delivery','','','','','');
INSERT INTO "payment" ("payhid","total_amount","payment_date","payment_method","card_name","card_number","cvv","exp_year","exp_month") VALUES (5,3000,'2026-09-29','Debit card','Erste','1122',2222,26,9);
INSERT INTO "payment" ("payhid","total_amount","payment_date","payment_method","card_name","card_number","cvv","exp_year","exp_month") VALUES (6,1000,'2026-09-29','Cash on delivery','','','','','');
INSERT INTO "payment" ("payhid","total_amount","payment_date","payment_method","card_name","card_number","cvv","exp_year","exp_month") VALUES (7,2000,'2026-09-29','Cash on delivery','','','','','');
INSERT INTO "payment" ("payhid","total_amount","payment_date","payment_method","card_name","card_number","cvv","exp_year","exp_month") VALUES (8,2000,'2026-09-30','Cash on delivery','','','','','');
INSERT INTO "payment" ("payhid","total_amount","payment_date","payment_method","card_name","card_number","cvv","exp_year","exp_month") VALUES (9,2000,'2026-09-30','Cash on delivery','','','','','');
INSERT INTO "payment" ("payhid","total_amount","payment_date","payment_method","card_name","card_number","cvv","exp_year","exp_month") VALUES (10,2000,'2026-10-04','Cash on delivery','','','','','');
INSERT INTO "payment" ("payhid","total_amount","payment_date","payment_method","card_name","card_number","cvv","exp_year","exp_month") VALUES (11,3000,'2026-10-09','Cash on delivery','','','','','');
INSERT INTO "payment" ("payhid","total_amount","payment_date","payment_method","card_name","card_number","cvv","exp_year","exp_month") VALUES (12,4000,'2026-10-09','Debit card','erste','12211',1222,26,11);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (1,23,1000,3);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (2,24,1000,3);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (3,25,1000,3);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (4,26,1000,4);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (5,27,1000,4);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (6,28,1000,4);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (7,29,1000,5);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (8,30,1000,5);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (9,31,1000,5);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (10,34,1000,6);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (11,35,1000,7);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (12,36,1000,7);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (13,39,1000,8);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (14,40,1000,8);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (15,41,1000,9);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (16,42,1000,9);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (17,45,1000,10);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (18,46,1000,10);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (19,47,1500,11);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (20,48,1500,11);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (21,49,2000,12);
INSERT INTO "payments" ("payrid","resid","amount","payhid") VALUES (22,50,2000,12);
INSERT INTO "routes" ("routeid","origin","destination","distance_km","duration") VALUES (1,'Budapest ','Prague',525,'07:30');
INSERT INTO "routes" ("routeid","origin","destination","distance_km","duration") VALUES (2,'Budapest','Vienna',250,'05:50');
INSERT INTO "routes" ("routeid","origin","destination","distance_km","duration") VALUES (3,'Budapest','Krakow',450,'09:10');
INSERT INTO "schedule" ("schedid","routeid","busid","departure_time","arrival_time","fare","driverid") VALUES (3,1,1,'2026-08-30 10:10','2026-08-30 15:15',1000,1);
INSERT INTO "schedule" ("schedid","routeid","busid","departure_time","arrival_time","fare","driverid") VALUES (4,3,5,'2026-12-01 09:30','2026-12-01 17:00',1500,4);
INSERT INTO "schedule" ("schedid","routeid","busid","departure_time","arrival_time","fare","driverid") VALUES (6,2,3,'2026-11-11 11:00','2026-11-11 18:30',2000,2);
INSERT INTO "schedule" ("schedid","routeid","busid","departure_time","arrival_time","fare","driverid") VALUES (7,2,3,'2026-10-01 10:00','2026-17-01 17:30',2000,2);
INSERT INTO "seatreservation" ("resid","passid","schedid","seatno","status","resdate","fare") VALUES (47,7,4,'1 ','Booked','2026-10-09',1500);
INSERT INTO "seatreservation" ("resid","passid","schedid","seatno","status","resdate","fare") VALUES (48,7,4,'2 ','Booked','2026-10-09',1500);
INSERT INTO "seatreservation" ("resid","passid","schedid","seatno","status","resdate","fare") VALUES (49,7,6,'13','Booked','2026-10-09',2000);
INSERT INTO "seatreservation" ("resid","passid","schedid","seatno","status","resdate","fare") VALUES (50,7,6,'14','Booked','2026-10-09',2000);
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (1,23,'2026-09-29',1000,'32457');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (2,24,'2026-09-29',1000,'58291');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (3,25,'2026-09-29',1000,'3240');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (4,26,'2026-09-29',1000,'TC99259');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (5,27,'2026-09-29',1000,'TC88811');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (6,28,'2026-09-29',1000,'TC64069');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (7,29,'2026-09-29',1000,'TC79256');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (8,30,'2026-09-29',1000,'TC28572');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (9,31,'2026-09-29',1000,'TC31990');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (10,34,'2026-09-29',1000,'TC42337');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (11,35,'2026-09-29',1000,'TC16013');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (12,36,'2026-09-29',1000,'TC24643');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (13,39,'2026-09-30',1000,'TC23185');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (14,40,'2026-09-30',1000,'TC52888');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (15,41,'2026-09-30',1000,'TC81950');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (16,42,'2026-09-30',1000,'TC79203');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (17,45,'2026-10-04',1000,'TC57844');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (18,46,'2026-10-04',1000,'TC8682');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (19,47,'2026-10-09',1500,'TC19015');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (20,48,'2026-10-09',1500,'TC97277');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (21,49,'2026-10-09',2000,'TC55577');
INSERT INTO "ticket" (" tickid","resid","tdate","amount","ticketno") VALUES (22,50,'2026-10-09',2000,'TC99925');
COMMIT;
