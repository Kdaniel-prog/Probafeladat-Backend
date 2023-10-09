# Probafeladat-Backend

A megoldást kérlek, töltsd fel GitHub vagy GitLab-ra és commitold lépésről-lépésre, hogy
lássam, milyen fázisokban haladtál. Készíts hozzá egy rövid leírást is, ami leírja, hogyan
indítható el az alkalmazás és a hozzá tartozó tesztek.

-Későn vettem észre, hogy sürön kéne commitolnom. A Controller teszteléseknél már próbáltam erre odafigyelni.
-Elinditás: Miután le lett tötltve a projekt, utána rögtön el is lehet inditani az alkalmazást, mivel az adatbázishoz H2 adatbázist használtam.
-Tesztek: A controllerek implamentációs tesztjei a controller package kattintva indiható el.

A Intellij IDEA-ba fejlesztettem az alkalmazást ezért célszerű ezt a fejlesztői környezetet használni a tesztelésére.

Végpontok:

/positions [POST]

/positions/{id} [GET]

/positions/{keyword}/{location} [GET] // én Accounting/London teszteltem arra visszaadott egy rekordot.

/clients [GET]

Az alkalmazás a 8080 porton hallgatózik.
