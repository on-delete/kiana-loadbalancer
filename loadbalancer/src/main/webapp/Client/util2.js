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
function getMacAdress(){
  
  var project = document.getElementById("chosenProject").value;
  var myJSONObject = {"customerProject": project, "startDate": null, "endDate": null, "gceCount": null}; 
  
  $.ajax({                                         
           url: 'http://104.197.107.205:8080/loadbalancer/MacCountService/getMacCountForAP', 
           processData: false,
           type: "POST",
           data: myJSONObject, //JSON.stringify(myJSONObject),
           contentType: "application/json",
  		     //crossDomain: true,
           success: function(response){
               console.log("Success!");
            },
           error: function() {
              alert(JSON.stringify(myJSONObject));
              //console.log("Too bad!");
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
            document.getElementById('chosenProject').value = '';
            document.getElementById('macField').value = '';
            document.getElementById('projectID').value = 'Choose project';
}

 // get customer projects von dem server          
function getCustomerProjects() {
    $.ajax({
            url: "http://104.197.107.205:8080/loadbalancer/customerProjects",
            type: "GET",
            contentType: 'json',
            success: function(resultData) {
                //console.log(resultData);
                //window.alert(resultData);
                buildMenu(data);
            },
            error : function(jqXHR, textStatus, errorThrown) {
            },

        });
  
}

 // fügt die projects in der select option menü
function buildMenu(data){
            for(var i=0;i<data.length;i++){
                var projectName = obj["projectName"];
                $.each(i, function(projectName) {   
                 $('#projectID')
                     .append($("<option></option>")
                     .attr(projectName)
                     .text(projectName)); 
                });
            }
}  

 // nimm ausgewählten project und schreib ihn im chosen project feld
function getValue(){

       if(document.getElementById('projectID') != '..')
       {
        var option = document.getElementById('projectID').value;
        document.getElementById("chosenProject").value = option;
       }
}

 // get alle customer projects und MACs von dem server und erstell die tabelle         
function getAllProjects() {
    $.ajax({
            url: "http://104.197.107.205:8080/loadbalancer/customerProjects",
            type: "GET",
            contentType: 'json',
            success: function(resultData) {
                //console.log(resultData);
                //window.alert(resultData);
               createTable(resultData);
            },
            error : function(jqXHR, textStatus, errorThrown) {
            },

        });
        
       function createTable(resultData){
            $("#getAllProjects").empty();
            $("#getAllProjects").append("<tr><th align=\"left\">Customer Project</th><th align=\"left\">Client MACs</th>");

            for(var i=0;i<data.length;i++){
                var obj = data[i];
                var customerProject = obj["customerProject"];
                var clientMacs = obj["clientMacs"];
                $("#getAllProjects").append("<tr><td>"+customerProject+"</td><td>"+clientMacs+"</td>");
            }
        }
 
  
}