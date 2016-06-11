/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  rober
 * Created: 24-mag-2016
 */

INSERT INTO goccia (id, nome, cognome, username, password, saldo)
    VALUES (default, 'Gabe', 'Newell', 'Gaben', '3', 333.33);
INSERT INTO venditore (id, gocciaId)
    VALUES (default, 1);

INSERT INTO prodotto (id, venditoreId, nome, urlImmagine, descrizione, prezzo)
    VALUES (default, 1, 'Squadra Fortezza 2', 'Images/001.jpg', 'Boh bello', 11.11);
INSERT INTO gioco (id, prodottoId, gocciaId)
    VALUES (default, 1, 1);

INSERT INTO prodotto (id, venditoreId, nome, urlImmagine, descrizione, prezzo)
    VALUES (default, 1, 'Mezza-Vita', 'Images/002.jpg', 'Boh bello', 11.11);
INSERT INTO gioco (id, prodottoId, gocciaId)
    VALUES (default, 2, 1);

INSERT INTO goccia (id, nome, cognome, username, password, saldo)
    VALUES (default, 'Ayy', 'Lmao', 'ayy', 'lmao', 11.11);
INSERT INTO cliente (id, gocciaId)
    VALUES (default, 2);