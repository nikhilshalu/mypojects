--Which route (origin & destination) has seen the maximum diversion?
A = load './PIG/flights_details.csv' USING PigStorage(',');
B = FOREACH A GENERATE (chararray)$17 as origin, (chararray)$18 as dest,(int)$24 as diversion;
C = FILTER B BY (origin is not null) AND (dest is not null) AND (diversion == 1);
D = GROUP C by (origin,dest);
E = FOREACH D generate group, COUNT(C.diversion);
F = ORDER E BY $1 DESC;
Result = limit F 10;
dump Result;
STORE Result INTO './PIG/maxDiversion/';