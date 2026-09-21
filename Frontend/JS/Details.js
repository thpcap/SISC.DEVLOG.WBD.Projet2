const employesTBody = document.getElementById("employesTBody");
const params=new URLSearchParams(window.location.search);
function loadDetails(){
    let id = params.get("id");
    let url = APILink+"entreprises/"+id;
    fetch(url, {}).then((response)=>{
        if (!response.ok) {
            throw new Error(`HTTP error: ${response.status}`);
        }
        return response.json();
    })
    .then(data=>{


        document.getElementById("secteur").innerText= data.secteur;
        document.getElementById("secteur2").innerText= data.secteur;

        document.getElementById("localisation").innerText= data.localisation;
        document.getElementById("localisation2").innerText= data.localisation;

        document.getElementById("taille").innerText= data.taille;
        document.getElementById("taille2").innerText= data.taille;

        document.getElementById("nom").innerText= data.nom;
        document.getElementById("dateCreation").innerText= data.dateCreation;
        let pays = data.paysPresents.split(",");
        document.getElementById("nbPays").innerText = pays.length;
        document.getElementById("Logo").setAttribute("src",data.url);
        document.getElementById("lienPostuler").setAttribute("href",data.lienPostuler);
        document.getElementById("siteWeb").setAttribute("href",data.siteWeb);
        document.getElementById("description").innerText= data.description;
        document.getElementById("chiffreDAffaires").innerText= data.chiffreAffaires;
        document.getElementById("nbEmployes").innerText= data.nbEmployes;
        document.getElementById("noteSatisfaction").innerText= data.noteSatisfaction;
        document.getElementById("scoreRse").innerText= data.scoreRse;
        document.getElementById("nbAgences").innerText= data.nbAgences;
        data.avantages.split(",").forEach(av=>{
            let row = document.createElement("span");
            row.classList.add("perk-tag");
            row.innerText=av;
            document.getElementById("avantages").append(row);
        });
        


        console.debug(data);

        employesTBody.innerHTML="";
        let employes =data.employes;
        let idsEmployesTxt=employes.map(emp => "idEmployes="+emp.id).join("&");
        let urlEmp=EmpAPILink+"employes?"+idsEmployesTxt;
        fetch(urlEmp, {}).then((response)=>{
        if (!response.ok) {
            throw new Error(`HTTP error: ${response.status}`);
        }
        return response.json();
        }).then(data=>{
            console.debug(data);
            let aujourdHui = new Date();

            data.forEach(emp => {
                let dateArrivee = new Date(emp.dateArrivee);
                let anciennete = aujourdHui.getFullYear() - dateArrivee.getFullYear();
                let row = document.createElement("tr");
                row.innerHTML=`
                        <td style="color: var(--text-primary); font-weight: 600;">${emp.nom+emp.prenom}</td>
                        <td>Directrice Technique Cloud</td>
                        <td>${anciennete} An${anciennete>1?"s":""}</td>`
                employesTBody.append(row);
            })
        }).catch(error=>{
            console.error(error);
        });
    }).catch(error=>{
        console.error(error);
    });
}
document.addEventListener("DOMContentLoaded",function(){
    loadDetails();
});