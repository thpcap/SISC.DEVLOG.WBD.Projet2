const employesTBody = document.getElementById("employesTBody");
const params=new URLSearchParams(window.location.search);
function loadDetails(){
    let id = params.get("id");
    let url = APILink+"entreprises";
    fetch(url, {}).then((response)=>{
        if (!response.ok) {
            throw new Error(`HTTP error: ${response.status}`);
        }
        return response.json();
    })
    .then(data=>{

        data = data.find(ent => ent.id == id);

        console.debug(data);

        document.getElementById("secteur").innerText= data.secteur;
        document.getElementById("localisation").innerText= data.localisation;
        document.getElementById("taille").innerText= data.taille;
        document.getElementById("nom").innerText= data.nom;
        document.getElementById("dateCreation").innerText= data.dateCreation;
        let pays = data.paysPresents.split(",");
        document.getElementById("nbPays").innerText = pays.length;
        document.getElementById("Logo").setAttribute("src",data.url);
        document.getElementById("lienPostuler").setAttribute("href",data.lienPostuler);
        document.getElementById("siteWeb").setAttribute("href",data.siteWeb);
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
            data.forEach(emp => {
                let row = document.createElement("tr");
                row.innerHTML=`
                        <td><span class="badge-id">${emp.id}</span></td>
                        <td style="color: var(--text-primary); font-weight: 600;">${emp.nom}</td>
                        <td>Directrice Technique Cloud</td>
                        <td>12 ans</td>`
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