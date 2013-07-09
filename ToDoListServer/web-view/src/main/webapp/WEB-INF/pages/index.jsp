<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="pages/css/style.css">
    <link rel="stylesheet" type="text/css" href="pages/css/tabsStyle.css">
    <link rel="stylesheet" type="text/css" href="pages/css/typeAHeadStyle.css">
    <script type="text/javascript" src="pages/scripts/lib/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="pages/scripts/lib/typeahead.min.js"></script>
    <script type="text/javascript" src="pages/scripts/DataModel.js"></script>
    <script type="text/javascript" src="pages/scripts/Utils.js"></script>
    <script type="text/javascript" src="pages/scripts/AjaxAPI.js"></script>
    <script type="text/javascript" src="pages/scripts/ToDoList.js"></script>
    <script id="AddTaskFormTemplate" type="text/x-jquery-tmpl">
        <textarea rows="5" cols="25" id="NewTaskMessage" class="TaskMessageForm"></textarea>
        <input type="text" placeholder="Enter Assignee" id="Assignee">
        <input type="button" value="Add Task" id="AddButton" disabled>
    </script>
    <script id="EditTaskFormTemplate" type="text/x-jquery-tmpl">
        <textarea id="TaskEdit"></textarea>
        <span id="EditAssigneeContainer">
            <input type="text" placeholder="Enter Assignee" id="EditAssignee">
        </span>
        <input type="button" id="SaveButton" value="Save">
    </script>
    <script id="TaskTemplate" type="text/x-jquery-tmpl">
        <li class="" id="">
                <span class=""><br>
                    <span class="TaskDate"></span>
                    <span class="TaskAuthor"></span>
                </span>
            <img class="DeleteTask" src="pages/images/delete.png" title="Delete this task.">
            <img class="CheckTask" src="" title="Check this task as completed/active.">
        </li>
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            var toDoList = new ToDoList("#tab1", "#tab2");
            toDoList.initializeContainer();
            toDoList.addEventsToContainer();
            toDoList.initializeMyTasks();
            toDoList.initializeOthersTasks();
        })
    </script>
    <title> ToDoList </title>
</head>
<body>
<div id="TaskContainer">
    <p id="ErrorMessage">Error message</p>
    <p id="SuccessMessage">Success message</p>
    <a id="logout" href="${pageContext.request.contextPath}/logout">logout</a>

    <h1>To Do List</h1>
    <ul id="tabs">
        <li><a href="#" title="tab1">My Tasks</a></li>
        <li><a href="#" title="tab2">Others Tasks</a></li>
    </ul>
    <div id="content">
        <div id="tab1">
        </div>
        <div id="tab2">
        </div>
    </div>
</div>

</body>
</html>