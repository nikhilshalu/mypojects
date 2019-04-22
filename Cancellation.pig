flight_delayed = LOAD './PIG/flights_details.csv' USING PigStorage(',') AS (id:int,Year:int,Month:int,DayofMonth:int,DayOfWeek:int,DepTime:int,CRSDepTime:int,ArrTime:int,CRSArrTime:int,UniqueCarrier:chararray,FlightNum:int,TailNum:chararray,ActualElapsedTime:int,CRSElapsedTime:int,AirTime:int,ArrDelay:int,DepDelay:int,Origin:chararray,Dest:chararray,Distance:int,TaxiIn:int,TaxiOut:int,Cancelled:int,CancellationCode:chararray,Diverted:int,CarrierDelay:int,WeatherDelay:int,NASDelay:int,SecurityDelay:int,LateAircraftDelay:int);

filterRec = foreach flight_delayed generate Month , FlightNum , Cancelled , CancellationCode ;
filterData = FILTER filterRec by CancellationCode=='B' AND Cancelled==1;
monthGroup = group filterData BY Month;
iterateMonth = foreach monthGroup generate group, COUNT(filterData.Cancelled);
orderOnCount= order iterateMonth by $1 DESC; 
Result = limit orderOnCount 1; 
STORE Result INTO './PIG/CancellationMonth/';