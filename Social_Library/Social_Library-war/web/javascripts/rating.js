/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function radio_click(n, book_id){
                for(var i = 1; i<6; i++){
                    if(i<=n) document.getElementById("radio_rate_"+i+"_"+book_id).checked=true;//setAttribute("checked", "");
                    else document.getElementById("radio_rate_"+i+"_"+book_id).checked=false; //document.getElementById('radio_rate_'+i).attributes.removeAttribute("checked");
                }
                document.getElementById("rate_value"+"_"+book_id).setAttribute("value", n);
            }