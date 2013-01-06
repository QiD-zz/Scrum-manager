/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackmine;

/**
 *
 * TÄHÄN VOI KIRJOITELLA KOMMENTTEJA OHJELMASTA (tosi kätsy.. eikö?)
 * 
 * 
 * Aatelin koittaa vähä selventää miten mä itsevaltaisesti ajattelin tän ohjelman toimivan.
 * Toisin sanoen tää on kaikki jossain määrin ehdotusluontoista. Tosin osittain
 * jo toteutettuakin.
 * 
 * Eli MVC-rakenne toteutuisi kutakuinkin siten, että kun MainViewissä tehdään joku
 * muutos, se tieto menee controllerin kautta aina modelille, jota sitten näkymä,
 * eli mainView kuuntelee ja päivittää itseään. Tällä hetkellä kaikki yksittäisen
 * projektin tiedot löytyvät projectModel-nimisestä luokasta ja siinä pitäisi olla
 * melkolailla kaikki tarvittavat toiminnot jopa implementoitu.
 * 
 * Eli kun yritetään lisätä esimerkiksi uusi sprintti, täytyy sprintti lisätä
 * modeliin, josta sitten näkymä hoksaa, että uusi sprinttihän sinne ilmestyi ja
 * täten päivittää itsensä (update-metodi ei toistaiseksi ole toiminnassa, joten
 * eihän se mtn päivitä). Tämän vuoksi nyt myös siirtonapit eivät toimi, koska
 * kyseisiä taskeja tai sprinttejä ei ole modelissa ja model näin ollen herjaa
 * että "no such sprint or task".
 * 
 * Viimeisenä asiana tänään pähkäilin, että onko kustomoidulle tablemodelille
 * tarvetta vai ei. Kustoimoitu malli ymmärtääkseni lähinnä mahdollistaa addTask-
 * tyyppisen metodin toteutuksen. Ei tarvitsisi olla setValueAt:n varassa vaan
 * voidaan lisätä listaan taskeja ja arvot menevät automaattisesti paikalleen.
 * Toteutin jonkinlaisen tablemodelin, mutta en ole vielä testannut tai implemen-
 * toinut sitä.
 * 
 * Niin joo ja toi assignee mukamas tarkottaa sitä, kelle se taski ois nakitettu.
 * ***************************************************************************
 * Oma tableModel on ihan hyvä idea, sitä voidaan kokeilla käytännössä ja jos ei
 * toimi niin käyttää sit noita vakiomalliin. En oo täysin varma, mutta jotenkin
 * tuntuis siltä, että tuolla taulumallissa tarvis olla omaa tasks-listaa vaan
 * se hakis ne tiedot aina tuolta projektimallista. Sit pitäis olla myös oma
 * tablemodel kai sprinteille, joka hakis tietonsa kans projektimallista.
 * Tuossa tablemodelissa on varmaankin myös vain yksi column aina, vaikka
 * Taskilla ois kuinka paljon attribuutteja.
 * 
 * Lähinnä oon miettinyt tuon projektimallin toimintaa, että mitä siellä updatessa
 * tapahtuu näkymän puolella, kun se saa noita päivityskutsuja? Jotenkin ne pitäis
 * tuolla näkymän puolella kai toteuttaa ne päivitykset sitten, kun uutta tietoa
 * tulee. Nyt siellä ei ole juurikaan yhteyksiä mallin puolelle. Mä mietin, että
 * pitäiskö noilla notifyObserverseilla olla eri parametri, kun eri juttua tapahtuu
 * joka sit näkymän updatessa napataan ja tehdään se mitä mallista haluttiin?

 * Mä tein tuollaisen VAKIOT-luokan, johon kokosin noita tekstivakioita vähän yhteen,
 * joita voi sit käyttää eri puolilla ohjelmaa. Niitä voisi käyttää erottelemaan
 * noita eri asioiden updatetkin.

 * -Kuisma
 * 
 * 8.6.
 * update toimaa ainakin jollain tavalla.
 * - Julius
 * 
 * 
 * 10.6.
 * Huomasin, että jos raahattavan taskin tiputtaa jonnekin muualle kuin listan
 * päälle se katoaa. Pari kertaa ohjelma myös kaatui siihen tai seuraavaan
 * raahaukseen, kun indeksit paukkuivat yli. Muutaman kerran testaillessa sain
 * kaikki taskit katoamaan, mutta ohjelma jatkoi kuitenkin toimintaansa.
 *
 * 11.6.
 *
 * TODO-LIST:
 * 
 * - RENDERERIT -KUISMA
 * - EDITORIT - HANNE
 * - SAVE/LOAD - JULIUS // musta tämä nyt toimii
 * - EDISTYMISKÄYRÄ - HANNE
 * - JÄRJESTÄMINEN (ROWSORTER) - KUISMA
 *  
 * 13.6. MOROOOO
 * 
 * Oon duunaillut tuota rendereriä, mutta unohdin, että mitä me sovittiin kumpaankin
 * listaa tulevan noita tietoja?
 * 
 * Toinen asia: pistetäänkö me Taskiin se joku progressvalue-attribuutti? Sen
 * kautta vois tuohon rendereriin piirrellä jonku alkeellisen progressbarin,
 * josta näkis suoraan kuinka monta prosenttia taskista on tehty. Aloittelin
 * väsään asioita sen pohjalta, että semmoinen päätetään pistää taskille.
 * 
 * -Kuisma
 * 
 * 13.6.
 * 
 * Tasklistassa ei tarvis näkyä ku taskin nimi ja prioriteetti. Sprintin puolella oli
 * sit vähän enemmän. Oisko ollu nimi, prio, state ja assignee?
 * 
 * Tarvii vaihtaa varmaan tuolta tuo enum statesta ensimmäinen vaihe
 * UNASSIGNED -> vaikka WAITING tai PENDING, taidanpa tehdä sen heti.
 * 
 * Toi progressvalue on musta käyttäjälle lähes mahoton, ku eihän tehtävästä
 * oikeen voi etukäteen tietää, kuinka monta prosenttia siitä milläkin hetkellä
 * on tehtynä. Josko tyydyttäis vaan siihen aika arvioon jota sitten vois vielä 
 * kehittää 'jäljellä oleva työmäärä tunteina'-arvioks. Toisaalta onko se sitten
 * ihan yhtä mahoton arvioida? Progressbaari ois kyl sinänsä kauheen kiva. Se
 * tuntiarvio taas ois siitä mukava, kun vois laskee paljonko projektissa/sprintissä
 * on milloinkin tunteja tehty/jäljellä. Prosenttiluku ei taas sitä oikeen kerro.
 * 
 * Paljon ajatuksia. Vähän päätöksiä.
 * 
 * Ja juu sielä näkyy transferhandlerin yhteydessä olevan vielä sellanen bugi,
 * että kun listan viimestä liikuttaa listasta toiseen se kopioituu eikä siirry.
 * Sain vieläkin hukkuun taskeja kun heittelin niitä ympäri näyttöö. 
 * 
 * Joo. Semmosta.
 * 
 * - Julius
 * 
 * 18.6. maanantai
 * 
 * Toi renderer alkaa oleen aika ok kuosissa. Se kirjoittelee nyt taskien nimen
 * ja staten, pistää taustavärin priorityn mukaan ja piirtelee tuon progressbarin
 * noiden arvioitujen taskin kokonaisaika-arvion ja tehtyjen tuntien perusteella.
 * 
 * Tuossa sortauksessa päädyin käyttämään noita comparatoreja, koska niiden
 * avulla on helppo tuolla projektissa vaan sortata ainakin tuota task-listaa.
 * Tuossa on nyt priorityn ja aloituspäivämäärän mukaan sorttaavat comparatorit.
 * Tein projectModeliin tuommoiseen lisSorting-metodin, joka saa parametrina
 * käytetyn sorttaustyypiin, joka on vakio. Tällä hetkellä t
 *
 *
 *
 * 25.6 maanantai
 * Tein tänään tota member jutskaa, en ihan valmiiksi saanut. Joten en committanut
 * vielä, koska teille saattaa tulla ärsyttäviä ongelmia. Seuraavaksi teen sen editorin.
 * Ajattelin tehä tätä nyt kaks tuntia päivässä, mutta katotaan kuinka kerkeen.
 *
 * -Hanne
 *
 *
 *
 */
public class FOORUMI {
    
}
