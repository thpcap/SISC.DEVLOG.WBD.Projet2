const tableBodyEntreprise = document.getElementById("tableBodyEntreprise");
const entreprisesNumber = document.getElementById("entreprisesNumber");
const entrepriseInput = document.getElementById("EntrepriseInput");
const SecteurInput= document.getElementById("SecteurInput");
const TailleInput= document.getElementById("TailleInput");
const VilleInput= document.getElementById("VilleInput");

function reloadTable(){
    //recupération des valeurs de recherche
    let secteursValue = SecteurInput.value;
    let tailleValue = TailleInput.value;
    let villeValue = VilleInput.value;

   

    let entreprisesLink = APILink + "entreprises";
    //ajout de la recherche (si valeurs de recherche-> utilisation de l'endpoint /enreprises/search sinon symplement /entreprises)
    if (secteursValue != '' || tailleValue != '' || villeValue != '') {

        entreprisesLink += "/search?";

        if (secteursValue != '') {
            entreprisesLink += "secteurs=" + encodeURIComponent(secteursValue);
        }

        if (tailleValue != '') {

            if (secteursValue != '') {
                entreprisesLink += "&";
            }

            entreprisesLink += "taille=" + encodeURIComponent(tailleValue);
        }

        if (villeValue != '') {

            if (secteursValue != '' || tailleValue != '') {
                entreprisesLink += "&";
            }

            entreprisesLink += "ville=" + encodeURIComponent(villeValue);
        }
    }
    //chargement de la liste des entreprises
    fetch(entreprisesLink, {}).then((response)=>{
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
            //ajout des lignes des entreprises dans le tableau
            data.forEach(entrreprise => {
                //recupération du filtre
                let compteSecteurs = {};
                let compteLocalisations = {};
                let compteTailles = {};

                compteSecteurs[entrreprise.secteur]=(compteSecteurs[entrreprise.secteur]||0)+1;
                compteLocalisations[entrreprise.localisation]=(compteLocalisations[entrreprise.localisation]||0)+1;
                compteTailles[entrreprise.taille]=(compteTailles[entrreprise.taille]||0)+1;
                
                //création et ajout de la ligne
                let row = document.createElement("tr");
                row.innerHTML=` 
                <td>
                    <div class="company-identity" data-name="`+entrreprise.nom+`">
                        <img src="`+entrreprise.url+`" alt="" class="company-logo">
                        <strong>`+entrreprise.nom+`</strong>
                    </div>
                </td>
                <td><span class="category-badge">` +entrreprise.secteur+`</span></td>
                <td><span class="size-badge">`+ entrreprise.taille+`</span></td>
                <td><span class="city-badge">`+entrreprise.localisation+`</span></td>
                <td class="table-action"><a class="details-link" href="`+DetailsPageLink+entrreprise.id+`">Voir les détails <span aria-hidden="true">→</span></a></td>`
                tableBodyEntreprise.append(row);
            });
            entreprisesNumber.innerText=data.length;

            //comptage du nombre d'entreprises qui valide un filtre
            
            fetch(
                APILink+"entreprises/filtres", {
            }).then((response)=>{
                if (!response.ok) {
                    throw new Error(`HTTP error: ${response.status}`);
                }
                return response.json();
            })
            .then(data=>{
                console.debug(data);

                // On remet les options par défaut

                SecteurInput.innerHTML = '<option value="" selected>Secteur</option>';
                TailleInput.innerHTML = '<option value="" selected>Taille</option>';
                VilleInput.innerHTML = '<option value="" selected>Ville</option>';

                //ajout des filtres de secteurs
                data.secteurs.forEach(txt => {

                    let row = document.createElement("option");

                    row.value = txt;
                    row.innerText = `${txt} (${compteSecteurs[txt]})`;

                    if (txt == secteursValue) {
                        row.selected = true;
                    }

                    SecteurInput.append(row);
                });

                //ajout des filtres de tailles
                data.tailles.forEach(txt => {

                    let row = document.createElement("option");

                    row.value = txt;
                    row.innerText = `${txt} (${compteTailles[txt]})`;

                    if (txt == tailleValue) {
                        row.selected = true;
                    }

                    TailleInput.append(row);
                });
                //ajout des filtres de localisation
                data.localisations.forEach(txt => {

                    let row = document.createElement("option");

                    row.value = txt;
                    row.innerText = `${txt} (${compteLocalisations[txt]})`;

                    if (txt == villeValue) {
                        row.selected = true;
                    }

                    VilleInput.append(row);
                });
            });
        }
        
    }).catch(error=>{
        console.error(error);
    });

    
    
}
document.addEventListener("DOMContentLoaded",function(){
    reloadTable();
    filter();
});


//cache les entreprises qui ne correspondent pas à la recherche
function filter() {
    let nameSearch = entrepriseInput.value;
    if(nameSearch.length>0){
        let entreprises = tableBodyEntreprise.children;
        let nb_entreprises=0;

        let regex = new RegExp("*"+nameSearch+"*", "gmi");

        Array.from(entreprises).forEach(entreprise => {

            let nom = entreprise
                .querySelector(".company-identity")
                .getAttribute("data-name");

            let res = regex.test(nom);

            if (!res) {
                entreprise.style.display = "none";
            } else {
                nb_entreprises++;
                entreprise.style.display = "";
            }
        });
        entreprisesNumber.innerText=nb_entreprises;

    }
}


