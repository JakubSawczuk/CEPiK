
Insert into adress(IdAdress,City,Street,BuildingNr,ResidenceNr)
values(1,'Bolkowice','Lesna',12,25);
Insert into adress(IdAdress,City,Street,BuildingNr,ResidenceNr)
values(2,'Osiecznica','Lesna',13,40);
Insert into adress(IdAdress,City,Street,BuildingNr,ResidenceNr)
values(3,'Paszowice','Lesna',2,null);
Insert into adress(IdAdress,City,Street,BuildingNr,ResidenceNr)
values(4,'Gromadka','Lesna',5,null);
Insert into adress(IdAdress,City,Street,BuildingNr,ResidenceNr)
values(5,'Warta Boleslawiecka','Lesna',6,null);
Insert into adress(IdAdress,City,Street,BuildingNr,ResidenceNr)
values(6,'Bolkowice','Lipowa',85,null);
Insert into adress(IdAdress,City,Street,BuildingNr,ResidenceNr)
values(7,'Paszowice','Lipowa',12,45);
Insert into adress(IdAdress,City,Street,BuildingNr,ResidenceNr)
values(8,'Osiecznica','Lipowa',32,64);
Insert into adress(IdAdress,City,Street,BuildingNr,ResidenceNr)
values(9,'Gromadka','Lipowa',35,23);
Insert into adress(IdAdress,City,Street,BuildingNr,ResidenceNr)
values(10,'Warta Boleslawiecka','Lipowa',14,14);
Insert into adress(IdAdress,City,Street,BuildingNr,ResidenceNr)
values(11,'Bolkowice','Kwiatowa',35,null);
Insert into adress(IdAdress,City,Street,BuildingNr,ResidenceNr)
values(12,'Nowogrodziec','Lipowa',47,77);
Insert into adress(IdAdress,City,Street,BuildingNr,ResidenceNr)
values(13,'Warta Boleslawiecka','Lesna',6,null);
Insert into adress(IdAdress,City,Street,BuildingNr,ResidenceNr)
values(14,'Gromadka','Lakowa',37,null);
Insert into adress(IdAdress,City,Street,BuildingNr,ResidenceNr)
values(15,'Paszowice','Krotka',33,99);
Insert into adress(IdAdress,City,Street,BuildingNr,ResidenceNr)
values(16,'Osiecznica','Kwiatowa',38,18);
Insert into driver(PeselDrv,SurnameDrv,FirstNameDrv,SecondNameDrv,adress_IdAdress)
values(99762156377,'Kowalski','Janusz',null,1);
Insert into driver(PeselDrv,SurnameDrv,FirstNameDrv,SecondNameDrv,adress_IdAdress)
values(84846110715,'Kowalska','Anna','Natalia',2);
Insert into driver(PeselDrv,SurnameDrv,FirstNameDrv,SecondNameDrv,adress_IdAdress)
values(82441539933,'Nowak','Maciej','Janusz',8);
Insert into driver(PeselDrv,SurnameDrv,FirstNameDrv,SecondNameDrv,adress_IdAdress)
values(83015277197,'Babacka','Grazyna',null,9);
Insert into driver(PeselDrv,SurnameDrv,FirstNameDrv,SecondNameDrv,adress_IdAdress)
values(86572996991,'Jarzyna','Andrzej',null,10);
Insert into driver(PeselDrv,SurnameDrv,FirstNameDrv,SecondNameDrv,adress_IdAdress)
values(98908462392,'Abacki','Mikolaj','Andrzej',11);
Insert into driver(PeselDrv,SurnameDrv,FirstNameDrv,SecondNameDrv,adress_IdAdress)
values(88497228648,'Lisek','Monika',null,12);
Insert into driver(PeselDrv,SurnameDrv,FirstNameDrv,SecondNameDrv,adress_IdAdress)
values(84846110711,'Kowalska','Anna','Magda',2);
Insert into policeman(PeselPol,SurnamePol,FirstNamePol,SecondNamePol,adress_IdAdress)
values(99762156377,'Kowalski','Janusz',null,1);
Insert into policeman(PeselPol,SurnamePol,FirstNamePol,SecondNamePol,adress_IdAdress)
values(84846110715,'Kowalska','Anna','Natalia',2);
Insert into policeman(PeselPol,SurnamePol,FirstNamePol,SecondNamePol,adress_IdAdress)
values(98493839747,'Kowalski','Michal','Andrzej',3);
Insert into policeman(PeselPol,SurnamePol,FirstNamePol,SecondNamePol,adress_IdAdress)
values(84025099595,'Jarzyna','Mikolaj',null,4);
Insert into policeman(PeselPol,SurnamePol,FirstNamePol,SecondNamePol,adress_IdAdress)
values(86572996991,'Jarzyna','Andrzej',null,5);
Insert into policeman(PeselPol,SurnamePol,FirstNamePol,SecondNamePol,adress_IdAdress)
values(90158440762,'Abacka','Anna',null,6);
Insert into policeman(PeselPol,SurnamePol,FirstNamePol,SecondNamePol,adress_IdAdress)
values(85808872168,'Jarzyna','Katarzyna',null,7);
Insert into mandate(IdMandate,PeselDrv,PeselPol,Points,DateMandate,Cause)
values(1,99762156377,90158440762,10,'2017-10-10','zbyt szybka jazdajazda pod wplywem alkoholu');
Insert into mandate(IdMandate,PeselDrv,PeselPol,Points,DateMandate,Cause)
values(2,99762156377,98493839747,16,'2017-10-10','przekroczenie predkosci, wyprzedzanie na pasach,jazda pod wplywem');
Insert into mandate(IdMandate,PeselDrv,PeselPol,Points,DateMandate,Cause)
values(3,83015277197,90158440762,5,'2017-9-10','rozmowa przez telefon podczas jazdy');
Insert into mandate(IdMandate,PeselDrv,PeselPol,Points,DateMandate,Cause)
values(4,86572996991,99762156377,3,'2017-11-11','brak');
Insert into mandate(IdMandate,PeselDrv,PeselPol,Points,DateMandate,Cause)
values(5,88497228648,85808872168,4,'2017-9-10','przekroczenie predkosci');
Insert into vehicle(Brand,Category,Type,Model,Variant,Version,RegistrationNumber,VIN,ProductionYear,DateFirstRegistration,GrossVehicleWeightRating,PeopleCapacity)
values('Citroen','M1','coupe','C1',3,'1','XR9360Y','C1234A567G89D0111',2000,'2000-11-19',800,4);
Insert into vehicle(Brand,Category,Type,Model,Variant,Version,RegistrationNumber,VIN,ProductionYear,DateFirstRegistration,GrossVehicleWeightRating,PeopleCapacity)
values('Seat', 'M1','coupe','Ibiza',5,'1','WPO1223','Z999999F999J99999',2010,'2015-08-15',1600,5);
Insert into vehicle(Brand,Category,Type,Model,Variant,Version,RegistrationNumber,VIN,ProductionYear,DateFirstRegistration,GrossVehicleWeightRating,PeopleCapacity)
values('Volkswagen','N1','dostawczy','Transporter',5,'Long','WGPZN49','88S8888888G8888K8',1997,'2000-01-01',3500,3);
Insert into vehicle(Brand,Category,Type,Model,Variant,Version,RegistrationNumber,VIN,ProductionYear,DateFirstRegistration,GrossVehicleWeightRating,PeopleCapacity)
values('Fiat','N1','dostawczy','Ducato',5,'L3H2','WPHN459','777Z7777B77T7AHBA',2008,'2008-10-10',3500,3);
Insert into vehicle(Brand,Category,Type,Model,Variant,Version,RegistrationNumber,VIN,ProductionYear,DateFirstRegistration,GrossVehicleWeightRating,PeopleCapacity)
values('Fiat','M1','coupe','Bravo',5,'2','WZX1234','12347777B77T7AHBA',2005,'2006-8-10',1300,3);
Insert into vehicle(Brand,Category,Type,Model,Variant,Version,RegistrationNumber,VIN,ProductionYear,DateFirstRegistration,GrossVehicleWeightRating,PeopleCapacity)
values('BMW','M1','sedan','E36',3,'2','WZX3GD5','12347746B77T7ZDKA',1999,'2000-1-10',1300,5);
Insert into vehicle(Brand,Category,Type,Model,Variant,Version,RegistrationNumber,VIN,ProductionYear,DateFirstRegistration,GrossVehicleWeightRating,PeopleCapacity)
values('BMW','M1','suv','E84',5,'2','WBC673D','38Z7777B77T7KLJBA',2010,'2010-8-10',2000,5);
Insert into technicaltest(DateTest,ExpirationDateTest,MileAge,IdMotorworks,Clauses,IdTest,VIN)
Values('2016-11-19','2017-11-19',120000,1,null,1,'C1234A567G89D0111');
Insert into technicaltest(DateTest,ExpirationDateTest,MileAge,IdMotorworks,Clauses,IdTest,VIN)
Values('2015-08-30','2017-08-30',200000,1,'Stan Dobry',2,'777Z7777B77T7AHBA');
Insert into technicaltest(DateTest,ExpirationDateTest,MileAge,IdMotorworks,Clauses,IdTest,VIN)
Values('2017-02-01','2018-02-01',350000,1,'Problem z hamulcami',3,'Z999999F999J99999');
Insert into technicaltest(DateTest,ExpirationDateTest,MileAge,IdMotorworks,Clauses,IdTest,VIN)
Values('2017-9-10','2018-9-10',100000,1,'Brak podłogi',4,'88S8888888G8888K8');
Insert into technicaltest(DateTest,ExpirationDateTest,MileAge,IdMotorworks,Clauses,IdTest,VIN)
Values('2017-8-10','2019-8-10',200000,2,null,5,'12347777B77T7AHBA');
Insert into technicaltest(DateTest,ExpirationDateTest,MileAge,IdMotorworks,Clauses,IdTest,VIN)
Values('2017-1-10','2018-1-10',260000,2,null,6,'12347746B77T7ZDKA');
Insert into technicaltest(DateTest,ExpirationDateTest,MileAge,IdMotorworks,Clauses,IdTest,VIN)
Values('2016-8-10','2018-8-10',100000,3,null,7,'38Z7777B77T7KLJBA');
insert into oc(IdOC,VIN,OCDate,OCExpirationDate)
Values(1,'C1234A567G89D0111','2016-11-19','2017-11-19');
insert into oc(IdOC,VIN,OCDate,OCExpirationDate)
Values(2,'Z999999F999J99999','2017-08-15','2018-08-15');
insert into oc(IdOC,VIN,OCDate,OCExpirationDate)
Values(3,'88S8888888G8888K8','2017-01-01','2018-01-01');
insert into oc(IdOC,VIN,OCDate,OCExpirationDate)
Values(4,'777Z7777B77T7AHBA','2017-10-10','2018-10-10');
insert into oc(IdOC,VIN,OCDate,OCExpirationDate)
Values(5,'12347777B77T7AHBA','2017-8-10','2018-8-10');
insert into oc(IdOC,VIN,OCDate,OCExpirationDate)
Values(6,'12347746B77T7ZDKA','2017-1-10','2018-1-10');
insert into oc(IdOC,VIN,OCDate,OCExpirationDate)
Values(7,'38Z7777B77T7KLJBA','2017-8-10','2018-8-10');
Insert into vehicleowner(PeselDrv,VIN)
Values(83015277197,'777Z7777B77T7AHBA');
Insert into vehicleowner(PeselDrv,VIN)
Values(99762156377,'777Z7777B77T7AHBA');
Insert into vehicleowner(PeselDrv,VIN)
Values(99762156377,'88S8888888G8888K8');
Insert into vehicleowner(PeselDrv,VIN)
Values(86572996991,'Z999999F999J99999');
Insert into vehicleowner(PeselDrv,VIN)
Values(84846110715,'C1234A567G89D0111');
Insert into vehicleowner(PeselDrv,VIN)
Values(83015277197,'12347777B77T7AHBA');
Insert into vehicleowner(PeselDrv,VIN)
Values(88497228648,'12347746B77T7ZDKA');
Insert into vehicleowner(PeselDrv,VIN)
Values(86572996991,'38Z7777B77T7KLJBA');
Insert into vehicleloss(IdVloss,VIN,DateEvent,DateFindingVLoss)
Values(1,'C1234A567G89D0111','2017-02-02',null);
Insert into vehicleloss(IdVloss,VIN,DateEvent,DateFindingVLoss)
Values(2,'38Z7777B77T7KLJBA','2016-02-02','2017-06-06');
Insert into vehicleloss(IdVloss,VIN,DateEvent,DateFindingVLoss)
Values(3,'12347746B77T7ZDKA','2016-10-16',null);
Insert into registrationdocument(IdAuth,VIN,DateAuth,ExpirationDateAuth,CommentAuth)
Values(1,'C1234A567G89D0111','2000-11-19','2017-02-02','skradziony');
Insert into registrationdocument(IdAuth,VIN,DateAuth,ExpirationDateAuth,CommentAuth)
Values(2,'Z999999F999J99999','2015-08-15',null,null);
Insert into registrationdocument(IdAuth,VIN,DateAuth,ExpirationDateAuth,CommentAuth)
Values(3,'88S8888888G8888K8','2000-01-01',null,null);
Insert into registrationdocument(IdAuth,VIN,DateAuth,ExpirationDateAuth,CommentAuth)
Values(4,'777Z7777B77T7AHBA','2008-10-10',null,null);
Insert into registrationdocument(IdAuth,VIN,DateAuth,ExpirationDateAuth,CommentAuth)
Values(5,'12347777B77T7AHBA','2006-8-10',null,null);
Insert into registrationdocument(IdAuth,VIN,DateAuth,ExpirationDateAuth,CommentAuth)
Values(6,'12347746B77T7ZDKA','2000-1-10','2016-10-16','skradziony');
Insert into registrationdocument(IdAuth,VIN,DateAuth,ExpirationDateAuth,CommentAuth)
Values(7,'38Z7777B77T7KLJBA','2010-8-10',null,null);
Insert into drivinglicense(KategoryDL,DateAuth,ExpirationDateAuth,IdAuth,PeselDrv,CommentAuth)
values('B','2015-10-11','2024-10-11',1,84846110715,null);
Insert into drivinglicense(KategoryDL,DateAuth,ExpirationDateAuth,IdAuth,PeselDrv,CommentAuth)
values('A','2016-02-26','2020-02-26',2,98908462392,'Ze względu na wadę wzroku');
Insert into drivinglicense(KategoryDL,DateAuth,ExpirationDateAuth,IdAuth,PeselDrv,CommentAuth)
values('A2','2017-08-08','2026-08-08',3,82441539933,null);
Insert into drivinglicense(KategoryDL,DateAuth,ExpirationDateAuth,IdAuth,PeselDrv,CommentAuth)
values('C+E','2010-01-01','2015-01-01',4,99762156377,null);
Insert into drivinglicense(KategoryDL,DateAuth,ExpirationDateAuth,IdAuth,PeselDrv,CommentAuth)
values('B','2016-02-26','2020-02-26',5,98908462392,'Ze względu na wadę wzroku');
Insert into drivinglicense(KategoryDL,DateAuth,ExpirationDateAuth,IdAuth,PeselDrv,CommentAuth)
values('B','2018-01-01','2020-01-01',6,83015277197,null);
Insert into drivinglicense(KategoryDL,DateAuth,ExpirationDateAuth,IdAuth,PeselDrv,CommentAuth)
values('B','2017-08-20','2021-08-20',7,86572996991,null);
Insert into drivinglicense(KategoryDL,DateAuth,ExpirationDateAuth,IdAuth,PeselDrv,CommentAuth)
values('B','2016-02-14','2019-02-14',8,98908462392,null);
Insert into drivinglicense(KategoryDL,DateAuth,ExpirationDateAuth,IdAuth,PeselDrv,CommentAuth)
values('B','2017-03-13','2018-03-13',9,88497228648,null);
Insert into authorisationloss(IdALoss,IdAuth,Aut_IdAuth,DateALoss,DateFindingALoss)
values(1,1,null,'2016-10-24','2017-06-01');
Insert into authorisationloss(IdALoss,IdAuth,Aut_IdAuth,DateALoss,DateFindingALoss)
values(2,null,6,'2016-10-16',null);
Insert into authorisationloss(IdALoss,IdAuth,Aut_IdAuth,DateALoss,DateFindingALoss)
values(3,6,null,'2017-02-14','2017-04-24');
Insert into temporaryauthorisation(IdTempAuth,IdAuth,Aut_IdAuth,DateTempAuth,ExpirationDateTempAuth)
Values(1,1,null,'2016-10-24','2016-11-12');
Insert into temporaryauthorisation(IdTempAuth,IdAuth,Aut_IdAuth,DateTempAuth,ExpirationDateTempAuth)
Values(2,6,null,'2017-02-15','2017-03-15');
Insert into withdrawnauthorisation(IdWithdrawn,IdAuth,Aut_IdAuth,DataWithdrawn,ReturnDateWithdrawn)
value(1,4,null,'2017-10-10','2018-01-10');
Insert into withdrawnauthorisation(IdWithdrawn,IdAuth,Aut_IdAuth,DataWithdrawn,ReturnDateWithdrawn)
value(2,null,6,'2016-10-10',null);
Insert into withdrawnauthorisation(IdWithdrawn,IdAuth,Aut_IdAuth,DataWithdrawn,ReturnDateWithdrawn)
value(3,null,1,'2017-02-02',null)