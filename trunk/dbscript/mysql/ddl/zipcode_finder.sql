DELIMITER $$  
 
DROP   FUNCTION  IF EXISTS `GetDistance`$$  
 
CREATE   FUNCTION  `GetDistance`(  
 lat1  numeric (9,6),  
 lon1  numeric (9,6),  
 lat2  numeric (9,6),  
 lon2  numeric (9,6)  
)  RETURNS   decimal (10,5)  
BEGIN  
  DECLARE  x  decimal (20,10);  
  DECLARE  pi  decimal (21,20);  
  SET  pi = 3.14159265358979323846;  
  SET  x = sin( lat1 * pi/180 ) * sin( lat2 * pi/180  ) + cos(  
 lat1 *pi/180 ) * cos( lat2 * pi/180 ) * cos(  abs( (lon2 * pi/180) -  
 (lon1 *pi/180) ) );  
  SET  x = atan( ( sqrt( 1- power( x, 2 ) ) ) / x );  
  RETURN  ( 1.852 * 60.0 * ((x/pi)*180) ) / 1.609344;  
END $$  
 


DELIMITER $$  

 
DROP   PROCEDURE  IF EXISTS `GetNearbyZipCodes`$$  
 
CREATE   PROCEDURE  `GetNearbyZipCodes`(  
    zipbase  varchar (6),  
    range  numeric (15)  
)  
BEGIN  
DECLARE  lat1  decimal (5,2);  
DECLARE  long1  decimal (5,2);  
DECLARE  rangeFactor  decimal (7,6);  
SET  rangeFactor = 0.014457;  
SELECT  latitude,longitude  into  lat1,long1  FROM  'ZIPCODEDATA'  WHERE  zipcode = zipbase;  
SELECT  B.zipcode  
FROM  'ZIPCODEDATA'  AS  B   
WHERE    
 B.latitude  BETWEEN  lat1-(range*rangeFactor)  AND  lat1+(range*rangeFactor)  
  AND  B.longitude  BETWEEN  long1-(range*rangeFactor)  AND  long1+(range*rangeFactor)  
  AND  GetDistance(lat1,long1,B.latitude,B.longitude)  <= range;  
END $$  
 
