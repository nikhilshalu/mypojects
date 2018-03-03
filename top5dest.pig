--1) Find out the top 5 most visited destinations.

flight_details = LOAD './PIG/flights_details.csv' USING PigStorage(',') AS (id:int,Year:int,Month:int,DayofMonth:int,DayOfWeek:int,DepTime:int,CRSDepTime:int,ArrTime:int,CRSArrTime:int,UniqueCarrier:chararray,FlightNum:int,TailNum:chararray,ActualElapsedTime:int,CRSElapsedTime:int,AirTime:int,ArrDelay:int,DepDelay:int,Origin:chararray,Dest:chararray,Distance:int,TaxiIn:int,TaxiOut:int,Cancelled:int,CancellationCode:chararray,Diverted:int,CarrierDelay:int,WeatherDelay:int,NASDelay:int,SecurityDelay:int,LateAircraftDelay:int);

filterRec = foreach flight_details generate Year as year, FlightNum as flight_num, Origin as origin, Dest as dest;

destNotNull = filter filterRec  by dest is not null;

grpDest = group destNotNull BY dest; 

iterateDest = foreach grpDest generate group, COUNT(destNotNull.dest); 

sortDest = order iterateDest by $1 DESC; 

Result = LIMIT sortDest 5;
 
airport_details = LOAD './PIG/airports.csv' USING PigStorage(','); 
 
filterAirport = foreach airport_details generate (chararray)$0 as dest, (chararray)$2 as city, (chararray)$4 as country; 

joined_table = join Result by $0, filterAirport by dest; 

STORE joined_table INTO './PIG/top5Dest/';