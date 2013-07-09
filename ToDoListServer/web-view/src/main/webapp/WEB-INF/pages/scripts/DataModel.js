function User(id, username, password, authority, enabled) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.authority = authority;
    this.enabled = enabled;

    this.getId = function () {
        return this.id;
    };
    this.getUsername = function () {
        return this.username;
    };
    this.getPassword = function () {
        return this.password;
    };
    this.getAuthority = function () {
        return this.authority;
    };
    this.getEnabled = function() {
        return this.enabled;
    }
}

function Task(id, message, dateInMilliseconds, author, assignee, state) {
    this.id = id;
    this.message = message;
    this.dateInMilliseconds = dateInMilliseconds;
    this.author = author;
    this.assignee = assignee;
    this.state = state;

    this.getId = function(){
        return this.id;
    };
    this.getMessage = function () {
        return this.message;
    };
    this.setMessage = function(message){
        this.message = message;
    };
    this.getDateInMilliseconds = function () {
        return this.dateInMilliseconds;
    };
    this.setDateInMilliseconds = function (dateInMilliseconds){
        this.dateInMilliseconds = dateInMilliseconds;
    };
    this.getAuthor = function () {
        return this.author;
    };
    this.getAssignee = function(){
        return this.assignee;
    };
    this.setAssignee = function(assignee){
        this.assignee = assignee;
    };
    this.getState = function(){
        return this.state;
    };
    this.setState = function(state){
        this.state = state;
    };
    this.printDate = function(){
        return new Date(this.dateInMilliseconds).toLocaleString();
    }
}

function TaskList() {
    this.taskArray = []; //Array for Tasks
    this.addFirst = function (task) { //Add task to beginning Array
        this.taskArray.unshift(task);
    };
    this.addLast = function (task) { //Add task to ending Array
        this.taskArray.push(task);
    };
    this.getTaskArray = function () {
        if (this.taskArray) {
            return this.taskArray;
        } else return false;
    };
}

function UserList(){
    this.userArray = [];
    this.getUserArray = function(){
        return this.userArray;
    }
}

function DataModel() {
    this.userList = new UserList();
    this.myActiveTasks = new TaskList();
    this.myCompletedTasks = new TaskList();
    this.othersActiveTasks = new TaskList();
    this.othersCompletedTasks = new TaskList();
}