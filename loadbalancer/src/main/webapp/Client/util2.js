// JavaScript Document
  //adresse
  //http://104.197.107.205:8080/loadbalancer/MacCountService/getMacCountForAP
  
  //var customerProject; //je nach dem von drop down menu
  
  //1. wenn die seite 10.html geöffnet wird, müssen die customer projects via REST in drop down menu geladen werden - DONE
  //2. klick auf start - DONE
  //3. dann wird JSON object erstellt und gesendet werden - DONE
  //4. antwort bekommen und ins feld "Number of client MAC" schreiben - TODO
  //5. noch das ganze für die tabelle - TODO (11.html)
  
  //JSON für einen Accesspoint
  //{"customerProject": "Project",   "startDate": null,   "endDate": null, "gceCount": null}

  // request an der anzahl der MACs pro projekt
function getMacAdressOnce(){
  
  var project = $("#projectID").val();
  var myJSONObject = {"customerProject": project, "startDate": null, "endDate": null, "gceCount": null};
  
  $.ajax({                                         
           url: 'http://104.197.107.205:8080/loadbalancer/MacCountService/getMacCountForAP', 
           processData: false,
           type: "POST",
           data: JSON.stringify(myJSONObject), //JSON.stringify(myJSONObject),
           contentType: "application/json",
           success: function(response){
               console.log("Success!");
               $("#macField").val(response);
            },
           error: function() {
              alert(JSON.stringify(myJSONObject));
            }
       });
}

function getMacAdress(customerProject){

    var myJSONObject = {"customerProject": customerProject, "startDate": null, "endDate": null, "gceCount": null};

    $.ajax({
        url: 'http://104.197.107.205:8080/loadbalancer/MacCountService/getMacCountForAP',
        processData: false,
        type: "POST",
        data: JSON.stringify(myJSONObject), //JSON.stringify(myJSONObject),
        contentType: "application/json",
        success: function(response){
            console.log("Success!");
            createTable(customerProject, response);
        },
        error: function() {
            alert(JSON.stringify(myJSONObject));
        }
    });
}

function response(){
  //TODO
  //das soll die funktion, die die nummer von MACs in dem feld Number of client MAC schreibt
  //man erhält json file von dem server zurück
  
  //so etwas
  //var macAdresses = {"customerProject": project, "startDate": null, "endDate": null, "gceCount": null};
  //document.getElementById('macField').value = macAdresses; 
}

function refresh(){
            getCustomerProjects();
            document.getElementById('macField').value = '';
            document.getElementById('projectID').value = 'Choose project';
}

 // get customer projects von dem server          
function getCustomerProjects() {
    $.ajax({
            url: "http://104.197.107.205:8080/loadbalancer/customerProjects",
            type: "GET",
            contentType: 'application/json',
            success: function(data) {
                buildMenu(data);
            },
            error : function(jqXHR, textStatus, errorThrown) {
            },

        });
  
}

 // fügt die projects in der select option menü
function buildMenu(data){
            for(var i=0;i<data.length;i++){
                var projectName = data[i];
                $('#projectID').append("<option value="+projectName+">"+projectName+"</option>");
            }
}  

 // nimm ausgewählten project und schreib ihn im chosen project feld


 // get alle customer projects und MACs von dem server und erstell die tabelle         
function getAllProjects() {
    $.ajax({
            url: "http://104.197.107.205:8080/loadbalancer/customerProjects",
            type: "GET",
            contentType: 'application/json',
            success: function(resultData) {
                $("#getAllProjects").empty();
                $("#getAllProjects").append("<tr><th align=\"left\">Customer Project</th><th align=\"left\">Client MACs</th>");

                for(var i=0;i<resultData.length;i++){
                    var result = getMacAdress(resultData[i]);
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
            },
        });
}

function createTable(customerProject, result){
    $("#getAllProjects").append("<tr><td>"+customerProject+"</td><td>"+result+"</td>");
}