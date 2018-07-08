
CREATE TABLE acceleration  (
  id number(10) null, 
  x NUMBER(10) NULL,
  y NUMBER(10) null,
  z NUMBER(10) null,
  vx NUMBER(10) NULL,
  vy NUMBER(10) null,
  vz NUMBER(10) null,
  ax NUMBER(10) NULL,
  ay NUMBER(10) null,
  az NUMBER(10) null
 );
  
create sequence seq_acceleration start with 1 increment by 1;
         
   select * from (
    select id - 1, 
    (abs(x) + abs(y) + abs(z)) as dist, 
    (abs(vx) + abs(vy) + abs(vz)) as vel, 
    (abs(ax) + abs(ay) + abs(az)) as acc,
    x, vx, ax, y, vy, ay, z, vz, az from acceleration
   ) order by acc asc, vel asc, dist asc

/*
Insert scripteket ezzel csináltam notepad++-ban a kapott adathalmazból
regex:
   find: "p=<(.*?)>, v=<(.*?)>, a=<(.*?)>\r\n"
   replace: "insert into acceleration values \(seq_acceleration.nextval,\1,\2,\3\);\r\n"
*/