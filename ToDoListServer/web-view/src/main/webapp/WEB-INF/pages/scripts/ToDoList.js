function TaskRenderer() {
    this.renderTask = function (task, currentUser, taskListID, taskTemplateID, taskClass) {
        $(taskListID).append($(taskTemplateID).html());
        $(taskListID).find("li:last").addClass(taskClass).attr("id", task.getId());
        $("#" + task.getId()).find("span:first").addClass(taskClass + "Text").prepend(task.getMessage());
        $("#" + task.getId() + " .TaskDate").append(task.printDate());

        //For my tasks print Author.username, for others tasks print Assignee.username
        if (taskListID == "#MyTaskList") {
            $("#" + task.getId() + " .TaskAuthor").append(task.getAuthor().getUsername());
        } else $("#" + task.getId() + " .TaskAuthor").append("To: " + task.getAssignee().getUsername());

        //I can't remove tasks, if I isn't Author.
        if (currentUser.getUsername() != task.getAuthor().getUsername()) {
            $("#" + task.getId() + " .DeleteTask").removeClass().addClass("NoDeleteTask").attr("src", "pages/images/noDelete.png");
        }

        //I can't mark done/undone tasks, if I isn't Assignee.
        if (taskClass == "ActiveTask") {
            if (currentUser.getUsername() == task.getAuthor().getUsername() &&
                currentUser.getUsername() != task.getAssignee().getUsername()) {
                $(taskListID + " .CheckTask").removeClass().addClass("NoCheckTask").attr("src", "pages/images/checked.png");
            } else $(taskListID + " .CheckTask").last().attr("src", "pages/images/unchecked.png");
        } else {
            if (currentUser.getUsername() == task.getAuthor().getUsername() &&
                currentUser.getUsername() != task.getAssignee().getUsername()) {
                $(taskListID + " .CheckTask").removeClass().addClass("NoCheckTask").attr("src", "pages/images/checked.png");
            } else $(taskListID + " .CheckTask").last().attr("src", "pages/images/checked.png");
        }
    };
    this.addEventsToTask = function (toDoList) {
        $(".ActiveTask, .CompletedTask").hover(
            function () {
                $(this).addClass("Selected");
                $(".CheckTask", this).fadeIn(0);
                $(".NoCheckTask", this).fadeIn(0);
                $(".DeleteTask", this).fadeIn(0);
                $(".NoDeleteTask", this).fadeIn(0);
                $(".UpTask", this).fadeIn(0);
            },
            function () {
                $(this).removeClass("Selected");
                $(".CheckTask", this).fadeOut(0);
                $(".NoCheckTask", this).fadeOut(0);
                $(".DeleteTask", this).fadeOut(0);
                $(".NoDeleteTask", this).fadeOut(0);
                $(".UpTask", this).fadeOut(0);
            }
        );

        $(".DeleteTask").click(function () {
            var taskId = $(this).parent().attr("id");
            var task;
            if (confirm("Do you really want to delete this task?")) {
                task = toDoList.utils.findTaskById(taskId, toDoList.dataModel);
                toDoList.ajaxAPI.deleteTask(task, function () {
                    toDoList.initializeMyTasks();
                    toDoList.initializeOthersTasks();
                    $("#SuccessMessage").empty().append("Task was successful deleted.").fadeIn(0).delay(3000).fadeOut(3000);
                });
            }
        });

        $(".NoDeleteTask").click(function () {
            $("#ErrorMessage").empty().append("You can not delete this task because you are not the author.").fadeIn(0).delay(3000).fadeOut(3000);
        });

        $(".CheckTask").click(function () {
            var taskId = $(this).parent().attr("id");
            var task;
            task = toDoList.utils.findTaskById(taskId, toDoList.dataModel);
            if (task.getState() == "ACTIVE") {
                task.setState("COMPLETED");
                $("#SuccessMessage").empty().append("Task was successful marked done.").fadeIn(0).delay(3000).fadeOut(3000);
            } else {
                task.setState("ACTIVE");
                $("#SuccessMessage").empty().append("Task was successful marked undone.").fadeIn(0).delay(3000).fadeOut(3000);
            }
            task.setDateInMilliseconds(new Date().getTime());
            console.log(task);
            toDoList.ajaxAPI.updateTask(task, function () {
                toDoList.initializeMyTasks();
                toDoList.initializeOthersTasks();
            });
        });

        $(".NoCheckTask").click(function () {
            $("#ErrorMessage").empty().append("You can not mark done/undone " +
                "task if you are not the assignee.").fadeIn(0).delay(3000).fadeOut(3000);
        });

        $(".ActiveTaskText").dblclick(function () {
            var taskId = $(this).parent().attr("id");
            var task = toDoList.utils.findTaskById(taskId, toDoList.dataModel);
            var currentUser;
            var $taskContainer = $(this).parent();
            toDoList.ajaxAPI.findCurrentUser(function (userJSON) {
                currentUser = toDoList.utils.createUserFromJSON(userJSON);
                if (currentUser.getId() == task.getAuthor().getId()) {
                    $taskContainer.empty().append($("#EditTaskFormTemplate").html());
                    $("#EditAssignee").typeahead([
                        {
                            name: "EditAssignee",
                            local: toDoList.usersNames
                        }
                    ]).val(task.getAssignee().getUsername());
                    $("#SaveButton").click(function () {
                        task.setMessage($("#TaskEdit").val());
                        toDoList.ajaxAPI.findUserByUsername($("#EditAssignee").val(), function(AssigneeJSON){
                            var assignee = toDoList.utils.createUserFromJSON(AssigneeJSON);
                            task.setAssignee(assignee);
                            task.setDateInMilliseconds(new Date().getTime());
                            toDoList.ajaxAPI.updateTask(task, function () {
                                toDoList.initializeMyTasks();
                                toDoList.initializeOthersTasks();
                                $("#SuccessMessage").empty().append("Task was successful updated.").fadeIn(0).delay(3000).fadeOut(3000);
                            });
                        });
                    });
                    $("#TaskEdit").val(task.getMessage()).keydown(function (event) {
                        if (event.ctrlKey && event.keyCode == 13) {
                            task.setMessage($("#TaskEdit").val());
                            toDoList.ajaxAPI.updateTask(task, function () {
                                toDoList.initializeMyTasks();
                                toDoList.initializeOthersTasks();
                                $("#SuccessMessage").empty().append("Task was successful updated.").fadeIn(0).delay(3000).fadeOut(3000);
                            });
                        }
                    });
                }
            });
        });

        $("#DeleteAllCompletedTaskButton").click(function () {
            if (confirm("Are you really want to delete all completed tasks?")) {
                for (var i = 0; i < toDoList.dataModel.myCompletedTasks.getTaskArray().length; i++) {
                    var task = toDoList.dataModel.myCompletedTasks.getTaskArray()[i];
                    if (task.getAuthor().getUsername() == task.getAssignee().getUsername()) {
                        toDoList.ajaxAPI.deleteTask(task, function (JSON) {
                        });
                    }
                }
                toDoList.initializeMyTasks();
                toDoList.initializeOthersTasks();
                $("#SuccessMessage").empty().append("All tasks were successful deleted.").fadeIn(0).delay(3000).fadeOut(3000);
            }
        });
    };
}

function ToDoList(myContainerID, otherContainerID) {
    var self = this;
    this.dataModel = new DataModel();
    this.ajaxAPI = new AjaxAPI();
    this.utils = new Utils();
    this.taskRenderer = new TaskRenderer();
    this.myContainerID = myContainerID;
    this.otherContainerID = otherContainerID;
    this.initializeMyTasksOK = false;
    this.initializeOthersTasksOK = false;
    this.usersNames = [];

    this.initializeContainer = function () {
        $(self.myContainerID).empty();
        $(self.otherContainerID).empty();
        $(self.myContainerID).append($("#AddTaskFormTemplate").html());
        $("#content").find("div").hide(); // hide content
        $("#tabs").find("li:first").attr("id", "current"); // activate first tab
        $('#content').find("div:first").fadeIn(); // show content
    };

    this.addEventsToContainer = function () {
        $('#tabs').find('a').click(function (e) {
            e.preventDefault();
            $("#content").find("div").hide(); //hide all content
            $("#tabs").find("li").attr("id", ""); //reset ID
            $(this).parent().attr("id", "current"); // activate tab
            $('#' + $(this).attr('title')).fadeIn(); // show content of current tab
        });

        $("#NewTaskMessage").keydown(function (event) {
            $(this).removeClass().addClass("TaskMessageFormActive");
            if ($.trim($(this).val()).length > 0) {
                $("#AddButton").removeAttr("disabled");
            }
            if (event.ctrlKey && event.keyCode == 13) {
                self.utils.createNewTaskFromForm(self);
                $("#SuccessMessage").empty().append("Task was successful created.").fadeIn(0).delay(3000).fadeOut(3000);
            }
        }).keyup(function () {
                if ($.trim($(this).val()).length == 0) {
                    $("#AddButton").attr("disabled", true);
                    $(this).removeClass().addClass("TaskMessageForm");
                }
            });
        $("#AddButton").click(function () {
            self.utils.createNewTaskFromForm(self);
            $("#SuccessMessage").empty().append("Task was successful created.").fadeIn(0).delay(3000).fadeOut(3000);
        });

        $(document).keyup(function (event) {
            if (event.keyCode == 27) {
                self.initializeMyTasks();
                self.initializeOthersTasks();
            }
        });
    };

    this.addEventsToTasks = function (initializeMyTasksOK, initializeOthersTasksOK) {
        if (initializeMyTasksOK && initializeOthersTasksOK) {
            self.taskRenderer.addEventsToTask(self);
        }
    };

    this.cleanTaskList = function () {
        $("#MyTaskList").remove();
        $("#OtherTaskList").remove();
        $(self.myContainerID).find("br").remove();
        $("#DeleteAllCompletedTaskButton").remove();
        $("#NewTaskMessage").val("").removeClass().addClass("TaskMessageForm");
        $("#Assignee").unbind().val("");
        $("h3").remove();
        self.dataModel.myActiveTasks.getTaskArray().length = 0;
        self.dataModel.myCompletedTasks.getTaskArray().length = 0;
        self.dataModel.othersActiveTasks.getTaskArray().length = 0;
        self.dataModel.othersCompletedTasks.getTaskArray().length = 0;
        self.dataModel.userList.getUserArray().length = 0;
        self.initializeMyTasksOK = false;
        self.initializeOthersTasksOK = false;
        self.usersNames.length = 0;
    };

    this.initializeMyTasks = function () {
        self.cleanTaskList();
        self.ajaxAPI.findCurrentUser(function (userJSON) {
            var assignee = self.utils.createUserFromJSON(userJSON);
            self.ajaxAPI.findAllUsers(function (userListJSON) {
                for (var j = 0; j < userListJSON.length; j++) {
                    var user = self.utils.createUserFromJSON(userListJSON[j]);
                    self.dataModel.userList.getUserArray().push(user);
                    self.usersNames.push(user.getUsername());
                }
                $("#Assignee").typeahead([
                    {
                        name: "Assignee",
                        local: self.usersNames
                    }
                ]);
            });
            self.ajaxAPI.findTasksByAssigneeAndState(assignee, "ACTIVE", function (ActiveTasksJSON) {
                $(self.myContainerID).append('<ol id="MyTaskList"></ol>');
                $(self.myContainerID).append('<br><input type="button" id="DeleteAllCompletedTaskButton" value="Delete all completed tasks">');
                var count = 0;
                for (var i = 0; i < ActiveTasksJSON.length; i++) {
                    var task = self.utils.createTaskFromJSON(ActiveTasksJSON[i]);
                    self.taskRenderer.renderTask(task, assignee, "#MyTaskList", "#TaskTemplate", "ActiveTask");
                    self.dataModel.myActiveTasks.addLast(task);
                    count++;
                }
                self.ajaxAPI.findTasksByAssigneeAndState(assignee, "COMPLETED", function (CompletedTasksJSON) {
                    for (var i = CompletedTasksJSON.length - 1; i >= 0; i--) {
                        var task = self.utils.createTaskFromJSON(CompletedTasksJSON[i]);
                        self.taskRenderer.renderTask(task, assignee, "#MyTaskList", "#TaskTemplate", "CompletedTask");
                        self.dataModel.myCompletedTasks.addLast(task);
                        count++;
                    }
                    if (self.dataModel.myCompletedTasks.getTaskArray().length > 0) {
                        $("#DeleteAllCompletedTaskButton").fadeIn(0);
                    } else $("#DeleteAllCompletedTaskButton").fadeOut(0);
                    self.initializeMyTasksOK = true;
                    self.addEventsToTasks(self.initializeMyTasksOK, self.initializeOthersTasksOK);
                    if (count == 0) {
                        $("#MyTaskList").remove();
                        $(self.myContainerID).append("<h3>Your Task list is empty now. Use form to add new task.</h3>");
                    }
                });
            });
        });
    };

    this.initializeOthersTasks = function () {
        self.ajaxAPI.findCurrentUser(function (userJSON) {
            var author = self.utils.createUserFromJSON(userJSON);
            self.ajaxAPI.findTasksByAuthorAndState(author, "ACTIVE", function (ActiveTasksJSON) {
                $(self.otherContainerID).append('<ol id="OtherTaskList"></ol>');
                var count = 0;
                for (var i = 0; i < ActiveTasksJSON.length; i++) {
                    var task = self.utils.createTaskFromJSON(ActiveTasksJSON[i]);
                    if (task.getAssignee().getUsername() != author.getUsername()) {
                        self.taskRenderer.renderTask(task, author, "#OtherTaskList", "#TaskTemplate", "ActiveTask");
                        self.dataModel.othersActiveTasks.addLast(task);
                        count++;
                    }
                }
                self.ajaxAPI.findTasksByAuthorAndState(author, "COMPLETED", function (CompletedTasksJSON) {
                    for (var i = CompletedTasksJSON.length - 1; i >= 0; i--) {
                        var task = self.utils.createTaskFromJSON(CompletedTasksJSON[i]);
                        if (task.getAssignee().getUsername() != author.getUsername()) {
                            self.taskRenderer.renderTask(task, author, "#OtherTaskList", "#TaskTemplate", "CompletedTask");
                            self.dataModel.othersCompletedTasks.addLast(task);
                            count++;
                        }
                    }
                    self.initializeOthersTasksOK = true;
                    self.addEventsToTasks(self.initializeMyTasksOK, self.initializeOthersTasksOK);
                    if (count == 0) {
                        $("OtherTaskList").remove();
                        $(self.otherContainerID).append("<h3>Your Task list is empty now. Use form to add new task.</h3>");
                    }
                });
            });
        });
    };
}