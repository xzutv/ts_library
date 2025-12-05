Github: https://github.com/xzutv/ts_library

**1. Berätta om dina val av tester för Username och motivera varför du valt dem. Är det ett rimligt och bra urval av tester? Är det något du i efterhand tycker borde läggas till eller tas bort?**

Jag har valt att ha ett positivt samt ett negativt parametertest med ValueSource där jag testar olika kombinationer för regex kravet som vi ställt.

Jag tyckte inte att den ursprungliga regexen var tillräckligt restriktiv i hur ett användarnamn får utformas så därför uppdaterade jag det med en look-ahead där vi kräver att ett användarnamn måste innehålla minst en bokstav.

Jag tyckte att det kunde vara lämpligt att ha en null-check i ledet så vi även fångar upp det och testar mot detta.

Sedan hade jag två separata test för en tom sträng och en sträng med endast white-space för synlighetens skull. Jag kan tycka att det kanske blir svårläst att ha så mycket olika regex kombinationer i ett och samma parametertest. Fler tester kanske skulle behöva brytas ut till egna tester för att veta vad som testas.

Man skulle kunna lägga till massa fler kontroller / krav för vad ett användarnamn får vara och hur det är utformat.

**2. Vad anser du själv om skillnaderna mellan asserts från JUnit och de som erbjuds av AssertJ? Är det värt att plocka in ett extra bibliotek för detta eller är det bara onödigt?**

Till en början tyckte jag att det kändes onödigt då man ändå hade lärt sig JUnit och tyckte att de var tillräckligt bra. Men efter att ha fått använda AssertJ så insåg jag själv hur mycket mer läsbart det blev. Det är värt att plocka in ett extra bibliotek om man i sitt team kommer överens om att hålla sig till samma bibliotek / mönster.

**3. Givet det ursprungliga utseendet hos metoden register i UserDao, vad hade varit ett bra och rimligt urval av tester för den metoden? Ge exempel på tester och testdata.**

Då denna metod är skriven på ett sätt där den kallar på flera privata metoder, många steg som måste mockas och gör flera sql-operationer så blir det ganska omfattande att testa. Här hade varit bättre att använda en H2 databas med integrationstester.

Men här är ett test med mock där vi kontrollerar att när ett sql exception kastas vid connection loss så returneras en false från register metoden:
```@Test  
void register_ShouldReturnFalseWhenSQLExceptionOccurs() throws SQLException {  
    when(ds.getConnection()).thenThrow(new SQLException("Database error"));  
  
    UserDao userDao = new UserDao(ds);  
    boolean result = userDao.register("testuser", "Test User", "password");  
  
    assertThat(result).isFalse();  
}
```

Jag skulle även testa en lyckad registrering, rollback vid fel, apostrof replacement för realname. nullparametrar, långa strängar, dubbla registreringar av samma användarnamn, edgecases.


**4. Vad anser du själv om skillnaderna mellan att testa UserDao med hjälp av mockar gentemot att testa med hjälp av minnesdatabas? Vilken metod tycker du verkar vettigast och varför då?**

- Att testa den med hjälp av mockar kräver ganska mycket overhead vilket kan vara krävande för den som skriver tester. Det kan vara lätt att missa något i setup processen och svåra att läsa/förstå.

- Genom att använda en minnesdatabas slipper man alla mockar och kan nästan direkt ge sig på det man faktiskt vill göra, att skriva tester för våra sql queries och se till att sql-operationerna fungerar som förväntat.

- Mockar är starkt kopplade till java-koden, ändrar vi något i den så går våra tester sönder ex (om vi byter från statement till prepared statement). Det blir då mycket mer omständigt att refaktorisera då alla tester som påverkas av detta måste skrivas om.

* Mockar går mycket snabbare än integrationstester då vi vid varje integrationstest startar databasen, kör migrationer och sätter in testdata. Detta kan bli mycket långsamt vid många tester.

  Jag tycker att i detta fall att det är vettigare att använda minnesdatabas för UserDao på grund av de ovanstående anledningarna. Men jag kan också se att det är vettigt att använda sig utav båda strategierna för att komplettera varandra.


**5. Vad ser du för problem med att skriva integrationstester mot ett riktigt databassystem (i det här fallet MySQL)? Dvs. att ha en testmiljö som använder MySQL istället för t.ex. mockar eller H2. Vad finns det för svårigheter med det?**

Det första jag kommer att tänka på är det är svårt att isolera tester från varandra. Data från ett test kan påverka nästa och oväntade problem uppstår. Man lär rensa databasen mellan varje test vilket blir snabbt omständigt.

Som jag förstått det är det även mycket långsammare än h2 som är in memory, det kan i sin tur leda till att det avskräcker från att kontinuerligt köra tester.

Alla utvecklare måste också ha mysql på sin egen maskin vilket kan leda till "Works on my machine"-problem. Exempel är att man kan ha olika versioner installerade som kan påverka hur det fungerar på en maskin mot en annan (tex om en version har vissa reserverade ord medan en annan inte har det). Man kan ha olika konfigurationer och operativsystem som hanterar mysql på olika sätt.

Min slutsats är att det minskar förtroendet för testerna och slösar tid och resurser i onödan. Man bör istället använda sig utav en minnesdatabas som konfigureras på samma sätt över alla maskiner. 