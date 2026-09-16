const tableBodyEntreprise = document.getElementById("tableBodyEntreprise");
const entreprisesNumber = document.getElementById("entreprisesNumber");
const entrepriseInput = document.getElementById("EntrepriseInput");
function reloadTable(){
    let data;
    fetch(APILink, {}).then((response)=>{
        if (!response.ok) {
            throw new Error(`HTTP error: ${response.status}`);
        }
        return response.json();
    })
    .then(data=>{
        //emptyTable
        tableBodyEntreprise.innerHTML="";
        //reloadTable
        if(data.length!=0){
            data.forEach(entrreprise => {
                console.debug(entrreprise);
                let row = document.createElement("tr");
                row.innerHTML=` 
                <td>
                    <div class="company-identity" data-name="`entrreprise.nom`">
                        <img src="https://brandemia.org/contenido/subidas/2022/11/tipografia-y-paleta-de-color.png" alt="" class="company-logo">
                        <strong>`+entrreprise.nom+`</strong>
                    </div>
                </td>
                <td><span class="category-badge">E-commerce</span></td>
                <td><span class="size-badge">TGE</span></td>
                <td><span class="city-badge">Villneuved'asq</span></td>
                <td class="table-action"><a class="details-link" href="`+DetailsPageLink+entrreprise.id+`">Voir les détails <span aria-hidden="true">→</span></a></td>`
                tableBodyEntreprise.append(row);
            });
        }
        entreprisesNumber.innerText=data.length;
    }).catch(error=>{
        console.error(error);
    });
    //APIlink is in the page
}
document.addEventListener("DOMContentLoaded",function(){
    reloadTable();
});



function filter(int){
     //filter
    let nameSearch = entrepriseInput.value;
    if(nameSearch.length!=0){
        let regex=new RegExp(nameSearch+"*", "gmi")
        data=data.filter(ent=>{
            let res =regex.test(ent.nom);
            return res;
        });
    }
}