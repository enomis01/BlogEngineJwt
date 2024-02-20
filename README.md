**BLOG ENGINE**

DESCRIZIONE: Un sistema di gestione di blog in cui gli utenti possono registrarsi, accedere e pubblicare articoli. Gli utenti registrati possono anche commentare gli articoli.
 L'autenticazione e l'autorizzazione sono gestite utilizzando JSON Web Token (JWT).

**Funzionalità Principali**:

**Registrazione Utente:** Gli utenti possono registrarsi inserendo un nome utente, una password e un'email.

**Accesso Utente:** Gli utenti registrati possono accedere al sistema inserendo il loro nome utente e la password. Vengono generati token JWT validi per l'autenticazione.

**Gestione Articoli:** Gli utenti possono pubblicare, modificare ed eliminare i propri articoli. Ogni articolo ha un titolo, un contenuto e una data di pubblicazione.

**Commenti sugli Articoli:** Gli utenti possono commentare gli articoli pubblicati da altri utenti. I commenti includono il nome dell'autore, il testo del commento e la data 
di pubblicazione del commento.

**Protezione Endpoint:** Gli endpoint per la pubblicazione, modifica ed eliminazione degli articoli sono protetti da JWT. Solo gli utenti autenticati possono accedere
 a queste funzionalità o l'utente stesso che ha creato/commentato.
 
**Pagina di Profilo Utente:** Gli utenti hanno una pagina di profilo in cui possono visualizzare gli articoli che hanno pubblicato e i commenti che hanno lasciato.

**Ricerca di Articoli:** Gli utenti possono cercare articoli per titolo o autore.

**Gestione Token JWT:** Le password (o token) vengono crittografate e memorizzate in modo sicuro nel database. I token JWT hanno una scadenza (60 minuti) e possono essere rigenerati se sono scaduti.

Tecnologie Utilizzate:

**Spring MVC per la gestione delle richieste HTTP e delle viste.**
**Spring Security per l'autenticazione e l'autorizzazione.** (Auth)
**JSON Web Token (JWT) per la gestione dell'autenticazione e dell'accesso sicuro agli endpoint.**
**Database relazionale PostgreSQL.**
