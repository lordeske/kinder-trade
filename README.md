inder Trade - Platforma za Razmenu Figurica 
Kinder Trade je moderna web aplikacija namenjena kolekcionarima figurica koji žele da trguju, razmenjuju i komuniciraju sa drugim kolekcionarima. Platforma omogućava lako kreiranje trgovina, slanje ponuda i pregovaranje putem kontra ponuda.

Funkcionalnosti
Korisnici
-Registracija i prijava korisnika (JWT autentifikacija)
-Pregled i uređivanje profila
-Lista svih korisnika

Figurice
-Dodavanje, ažuriranje i brisanje figurica
-Pregled svih dostupnih figurica
-Omiljene figurice i sistem pretrage

Trgovina figuricama
- Kreiranje ponude za trgovinu figuricama između korisnika
- Prihvatanje ili odbijanje trgovine
- Povlačenje trgovine pre nego što je prihvaćena
- Slanje kontra ponude (Counter Offer) ako se ponuda ne sviđa
- Automatska validacija figurica (provera da li korisnik poseduje figuricu pre razmene)
- Filtriranje trgovina po statusu (PENDING, ACCEPTED, DECLINED, COUNTER_OFFER)

Chat sistem
- Privatni chat između korisnika
- Globalni javni chat za sve kolekcionare

Kako funkcioniše trgovina?
- Korisnik A kreira ponudu i bira figurice koje želi da ponudi i koje želi da dobije.
- Korisnik B dobija ponudu i može da:

Prihvati ponudu – trgovina se zatvara, figurice se razmenjuju
Odbije ponudu – trgovina se označava kao odbijena
Pošalje kontra ponudu – korisnik A dobija novu ponudu sa izmenjenim figuricama
- je trgovina završena, oba korisnika dobijaju istorijat razmene.

Tehnologije
Backend: Spring Boot (Java), Spring Security, JWT, JPA (Hibernate), PostgreSQL
Frontend: React.js, React Router, Context API
Baza podataka: PostgreSQL sa tabelama users, figurice, trgovina, trgovina_figurice
Autentifikacija: JWT (JSON Web Token)
Realtime chat: WebSockets

Video Primer Aplikacije 
https://www.youtube.com/watch?v=A2kVDirZf9o
