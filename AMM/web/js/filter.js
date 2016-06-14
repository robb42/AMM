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
            
            $("#listaProdotti")
                .append($('<tr>')
                    .attr('class', 'table-head')
                    .append($('<th>')
                        .attr('colspan', '2')
                        .text('Nome')
                    )
                    .append($('<th>')
                        .text('Disponibili')
                    )
                    .append($('<th>')
                        .text('Prezzo')
                    )
                    .append($('<th>')
                        .attr('class', 'table-cart')
                    )
                );
        
            for(var prodotto in listaProdotti) {
                $("#listaProdotti")
                    .append($('<tr>')
                        .append($('<td>')
                            .attr('class', 'table-logo-box')
                            .append($('<div>')
                                .attr('class', 'table-logo-bg')
                                .attr('style', 'background-image:url(\''+listaProdotti[prodotto].urlImmagine+'\')')
                            )
                        )
                        .append($('<td>')
                            .text(listaProdotti[prodotto].nome)
                        )
                        .append($('<td>')
                            .text(listaProdotti[prodotto].quantita)
                        )
                        .append($('<td>')
                            .text(listaProdotti[prodotto].prezzo)
                        )
                        .append($('<td>')
                            .append($('<a>')
                                .attr('href', 'Cliente?GiocoID='+listaProdotti[prodotto].id)
                                .append($('<i>')
                                    .attr('class', 'fa shopping-cart')
                                )
                            )
                        )
                    );
            }
        }
    }); 
});