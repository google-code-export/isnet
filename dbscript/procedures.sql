CREATE OR REPLACE PROCEDURE gen(mem number) as
tmpVar NUMBER;
/******************************************************************************
   NAME:       gen
   PURPOSE:    

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        8/14/2010          1. Created this procedure.

   NOTES:

   Automatically available Auto Replace Keywords:
      Object Name:     gen
      Sysdate:         8/14/2010
      Date and Time:   8/14/2010, 6:04:56 PM, and 8/14/2010 6:04:56 PM
      Username:         (set in TOAD Options, Procedure Editor)
      Table Name:       (set in the "New PL/SQL Object" dialog)

******************************************************************************/
BEGIN

      dbms_output.put_line('--generated for member : '|| mem);
    FOR MM IN (SELECT * FROM member WHERE MENTORED_BY_MEMBER_ID =mem )
    LOOP
        select KNOWN_MEMBER_ID_SEQ.NEXTVAL into tmpVar from dual;
       dbms_output.put_line('INSERT INTO KNOWN_MEMBER (KNOWN_ID,MEMBER_ID, KNOWN_MEMBER_ID) VALUES ('|| tmpVar || ' , '|| mem || ' , '|| mm.MEMBER_ID ||');' );
    
    END LOOP;
    dbms_output.put_line('--generated for member end : '|| mem);
    
END gen;



/

CREATE OR REPLACE PROCEDURE GenerateKnownMember IS
tmpVar NUMBER;
/******************************************************************************
   NAME:       GenerateKnowMember
   PURPOSE:    

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        8/14/2010          1. Created this procedure.

   NOTES:

   Automatically available Auto Replace Keywords:
      Object Name:     GenerateKnowMember
      Sysdate:         8/14/2010
      Date and Time:   8/14/2010, 4:48:25 PM, and 8/14/2010 4:48:25 PM
      Username:         (set in TOAD Options, Procedure Editor)
      Table Name:       (set in the "New PL/SQL Object" dialog)

******************************************************************************/
BEGIN

   
FOR mem IN (SELECT * FROM member)
  LOOP
    
    --DELETE FROM KNOWN_MEMBER;
   
    IF mem.MENTORED_BY_MEMBER_ID IS NOT NULL THEN
    select KNOWN_MEMBER_ID_SEQ.NEXTVAL into tmpVar from dual;
     dbms_output.put_line('----');
     --INSERT INTO KNOWN_MEMBER (KNOWN_ID,MEMBER_ID, KNOWN_MEMBER_ID) VALUES (tmpVar, mem.MEMBER_ID,mem.MENTORED_BY_MEMBER_ID );
      dbms_output.put_line('INSERT INTO KNOWN_MEMBER (KNOWN_ID,MEMBER_ID, KNOWN_MEMBER_ID) VALUES ('|| tmpVar || ' , '||mem.MEMBER_ID || ' , '|| mem.MENTORED_BY_MEMBER_ID ||');' );
    dbms_output.put_line('----');
    END IF;
    
    gen(mem.MEMBER_ID);

  END LOOP;
  
END GenerateKnownMember; 



/