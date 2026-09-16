const tableBodyEntreprise = document.getElementById("tableBodyEntreprise");
function reloadTable(){
    let data;
    fetch(APILink, {}).then((response)=>{
        if (!response.ok) {
            throw new Error(`HTTP error: ${response.status}`);
        }
        return response.json();
    })
    .then(data=>{
        data.forEach(entrreprise => {
            console.debug(entrreprise);
            let row = document.createElement("tr");
            row.innerHTML=` 
             <td>
                <div class="company-identity">
                    <img src="https://brandemia.org/contenido/subidas/2022/11/tipografia-y-paleta-de-color.png" alt="" class="company-logo">
                    <strong>`+entrreprise.nom+`</strong>
                </div>
            </td>
            <td><span class="category-badge">E-commerce</span></td>
            <td class="table-action"><a class="details-link" href="`+DetailsPageLink+entrreprise.id+`">Voir les détails <span aria-hidden="true">→</span></a></td>`
            tableBodyEntreprise.append(row);
        });
    }).catch(error=>{
        alert(error);
    });
    //APIlink is in the page
}
document.addEventListener("DOMContentLoaded",function(){
    reloadTable();
})
