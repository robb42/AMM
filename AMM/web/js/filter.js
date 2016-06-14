/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
   $('#ricerca').keyup(function() {  
        // Preleva il valore
        var text = $("#ricerca").val();
       
        $.ajax({
            url: "Filter",
            data:{
              cmd: "search",
              text: text
            },
            dataType: 'json',
            success : function(data, state){
                aggiornaListaProdotti(data);
            },
            error : function(data, state){
            }
        });
        
       // Funzione che viene richiamata in caso di successo
        function aggiornaListaProdotti(listaProdotti) {
            // Cancella la tabella
            $("#listaProdotti").empty();
            for(var prodotto in listaProdotti) {
                $("#listaProdotti")//.find('tbody')
                    .append($('<tr>')
                        .append($('<td>')
                        .attr('class', 'table-logo-box')
                            .append($('<div>')
                                .attr('class', 'table-logo-bg')
                                .attr('style', 'background-image:url(\''+prodotto.urlImmagine+'\')')
                            )
                        )
                        .append($('<td>')
                            .text(prodotto.nome)
                        )
                        .append($('<td>')
                            .text(prodotto.quantita)
                        )
                        .append($('<td>')
                            .text(prodotto.prezzo)
                        )
                        .append($('<td>')
                            .append($('<a>')
                                .attr('href', 'Cliente?GiocoID='+prodotto.id)
                                .append($('<i>')
                                    .attr('class', 'fa shopping-cart')
                                )
                            )
                        )
                    );
                // Crea un nuovo tag li
                /*var newtr = document.createElement("tr");
                newtr.setAttribute("name", "alunno");
                // testo
                var text = document.createTextNode(listaAlunni[alunno].nome + 
                        " " + listaAlunni[alunno].cognome + " ");
                newli.appendChild(text);
                // Crea link
                var link = document.createElement("a");
                link.setAttribute("href", "Registra?alunnoId="+listaAlunni[alunno].id);
                var registraTxt = document.createTextNode("Registra esame");
                link.appendChild(registraTxt);
                newli.appendChild(link);
                
                // Aggiunge il tag li al tag ul
                $("#listaProdotti").append(newli);*/
            }
        }
    }); 
});