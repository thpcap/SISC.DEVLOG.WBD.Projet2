const employesTBody = document.getElementById("employesTBody");
const params=new URLSearchParams(window.location.search);
function loadDetails(){
    let id = params.get("id");
    let url = APILink+"/entreprises/"+id;
    fetch(url, {}).then((response)=>{
        if (!response.ok) {
            throw new Error(`HTTP error: ${response.status}`);
        }
        return response.json();
    })
    .then(data=>{
        employesTBody.innerHTML="";
        let employes =data.employes;
        employes.forEach(emp => {
            let urlEmp=EmpAPILink+"employes?idEmployes="+emp.id;
            fetch(urlEmp, {}).then((response)=>{
            if (!response.ok) {
                throw new Error(`HTTP error: ${response.status}`);
            }
            return response.json();
            }).then(data=>{
                let row = document.createElement("tr");
                row.innerHTML="<td><strong>"+data.nom+"</strong></td><td>"+data.poste+"</td><td>"+data.experience+"</td>"
            }).catch(error=>{
                console.error(error);
            });
        });
    }).catch(error=>{
        console.error(error);
    });
}