Book-a-pitch este o aplicatie care permite rezervarea terenurilor de fotbal (destinata amatorilor acestui sport), care usureaza procesul de programare atat pentru utilizatori, dar si pentru detinatorii terenurilor.![Captură de ecran de la 2025-03-12 15-25-14](https://github.com/user-attachments/assets/4502373b-79bd-400c-ab84-07ed1be97f00)

Utilizatorii pot vizualiza intervalele orare disponibile pentru fiecare teren disponibil, pot rezerva terenul, pot achita pretul prin intermediul unui serviciu integrat de payment, in urma caruia vor fi notificati prin email pentru confirmare. De asemenea, au acces la 
toate detaliile necesare despre facilitatile terenului, locatia sau contactul acestuia. ![Captură de ecran de la 2025-03-12 15-28-16](https://github.com/user-attachments/assets/3a576537-b557-451d-800b-13c70d4cd22d)

Acestia pot vedea in timp real rezervarile facute si, prin parcurgerea unui proces simplu, sa faca si ei o rezervare (dupa ce trec de procesul de autentificare si logare).
![Captură de ecran de la 2025-03-12 15-30-54](https://github.com/user-attachments/assets/76393c73-2b19-4936-b18c-794bce617676)


Acest repository prezinta doar partea de backend a aplicatiei, API-ul cu care clientul va interactiona. API-ul a fost dezvoltat folosind Java Spring.
Securitatea este propusa prin folosirea JWT (Json Web Tokens), care asigura identificarea utilizatorilor si securizeaza endpoint-urile sensibile. 
Baza de date folosita este PostgreSQL, iar ORM-ul folosit este Hibernate.
Logica de business a aplicatiei este asigurata de validatori custom care verifica integritatea datelor primite si trimise de la / catre client.
De asemenea, aplicatia contine si servicii de mailing (smtp + spring-boot-starter-mail), respectiv payment (Stripe API). 

Arhitectura aplicatiei include, de asemenea: Layered Architecture(SoC), Repository Pattern, Data Mapper Pattern, Observer Pattern, Result Pattern
