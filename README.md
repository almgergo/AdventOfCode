# Advent of code 10 and 20 megoldások

## H1 10 
Saját linkelt listát csináltam a körkörös viselkedés miatt. Csak olyan funkciókat implementáltam amit szükségesnek éreztem a megoldáshoz.


## H1 20
### H2 1
Az első rész megoldásához SQL-t használtam, leginkább azért mert gyorsan tesztelni tudtam vele az ötleteimet és pont megfelelő egy ilyen logikai megoldás gyors kitalálására. A jelenlegi megoldásom: melyik részecskének a legkisebb a gyorsulása, ha többnek ugyanannyi, akkor sebesség, majd melyik van legközelebb az origóhoz. 
Ez a megoldás nem tökéletes, gondolkodtam még sebesség + gyorsulás skaláris szorzatán is mint mérőszám, ha több részecskéne megegyezik a sebessége. Valahogy mérni kéne a radiális vetületét a gyorsulásnak / sebességnek, mivel ezek fogják az origótól távolabb vinni a részecskét. Persze a végtelenben minden radiális, ezért csak egyező gyorsulások esetén kéne vizsgálni. Erre nem maradt időm végiggondolni (gondoltam ha kész vagyok mindennel még finomítok rajta).

### H2 2
A második résznél a legtriviálisabb megoldás az lett volna, hogy léptetem a rendszert és minden lépésnél kinyírom azokat a részecskéket, akik ugyanabban a rácspontban ülnek (egy hashmap-ben letárolva pozíció szerint a részecskéket, és ahol több van azokat törölni). Itt minden egyéb tulajdonság figyelembevétele nélkül nem lehet megmondani, hogy fog-e még találkozni 2 részecske a jövőben szóval tulajdonképpen ha egy idő után nem változik az élő részecskék száma, akkor az csak egy erős tipp, hogy később sem fog.  
Ezen próbáltam javítani azzal, hogy számon tartom minden részecskéhez azokat a részecskéket ("szomszédok"), akikkel még valaha esélyük lesz találkozni, így folyamatosan csökken a számon tartandó récsecskék száma és csak addig kell folytatni a számolást, ameddig van olyan részecske, akinek van esélye találkozni egy másikkal. Persze ezen is lehetne bőven optimalizálni, itt az egyik legcsúnyább dolog, hogy hatalmas listákkal indul az egész O(n^2), mivel mindenkinek mindenki szomszédja az elején, viszont van végeredmény. 
 