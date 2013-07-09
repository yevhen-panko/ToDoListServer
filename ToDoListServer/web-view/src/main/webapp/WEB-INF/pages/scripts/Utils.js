function Utils() {
    var self = this;

    this.createUserFromJSON = function (JSON) {
        var id = JSON["id"];
        var username = JSON["username"];
        var password = JSON["password"];
        var authority = JSON["authority"];
        var enabled = JSON["enabled"];
        if (id != undefined && username != undefined && password != undefined
            && authority != undefined && enabled != undefined) {
            return new User(id, username, password, authority, enabled);
        } else return false;
    };
    this.createTaskFromJSON = function (JSON) {
        var id = JSON["id"];
        var message = JSON["message"];
        var dateInMilliseconds = JSON["dateInMilliseconds"];
        var author = this.createUserFromJSON(JSON["author"]);
        var assignee = this.createUserFromJSON(JSON["assignee"]);
        var state = JSON["state"];
        if (id != undefined && message != undefined && dateInMilliseconds != undefined
            && author != undefined && assignee != undefined && state != undefined) {
            return new Task(id, message, dateInMilliseconds, author, assignee, state);
        } else return false;
    };
    this.findTaskById = function(id, DataModel){
        var task;
        for (var i=0; i<DataModel.myActiveTasks.getTaskArray().length; i++){
            task =  DataModel.myActiveTasks.getTaskArray()[i];
            if (task.getId() == id){
                return task;
            }
        }
        for (var j=0; j<DataModel.myCompletedTasks.getTaskArray().length; j++){
            task =  DataModel.myCompletedTasks.getTaskArray()[j];
            if (task.getId() == id){
                return task;
            }
        }
        for (var k=0; k<DataModel.othersActiveTasks.getTaskArray().length; k++){
            task =  DataModel.othersActiveTasks.getTaskArray()[k];
            if (task.getId() == id){
                return task;
            }
        }
        for (var m=0; m<DataModel.othersCompletedTasks.getTaskArray().length; m++){
            task =  DataModel.othersCompletedTasks.getTaskArray()[m];
            if (task.getId() == id){
                return task;
            }
        }
        return false;
    };

    this.createNewTaskFromForm = function(toDoList){
        var id;
        var message = $("#NewTaskMessage").val();
        var dateInMilliseconds = new Date().getTime();
        toDoList.ajaxAPI.findCurrentUser(function (authorJSON) {
            var author = self.createUserFromJSON(authorJSON);
            var username = $("#Assignee").val();
            if (username == ""){
                username = author.getUsername();
            }
            toDoList.ajaxAPI.findUserByUsername(username, function (assigneeJSON) {
                var assignee = self.createUserFromJSON(assigneeJSON);
                var state = "ACTIVE";
                var task = new Task(id, message, dateInMilliseconds, author, assignee, state);
                console.log(task);
                toDoList.ajaxAPI.createTask(task, function () {
                    toDoList.initializeMyTasks();
                    toDoList.initializeOthersTasks();
                    $("#AddButton").attr("disabled", true);
                });
            });
        });
    };

    this.isValidEmailAddress = function(emailAddress){
        var pattern = new RegExp(/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);
        return pattern.test(emailAddress);
    };

    this.getURLParameter = function(sParam){
        var sPageURL = window.location.search.substring(1);
        var sURLVariables = sPageURL.split('&');
        for (var i = 0; i < sURLVariables.length; i++){
            var sParameterName = sURLVariables[i].split('=');
            if (sParameterName[0] == sParam){
                return sParameterName[1];
            }
        }
        return false;
    }
}