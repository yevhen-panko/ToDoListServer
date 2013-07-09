function AjaxAPI() {
    this.createTask = function (task, callback) {
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'},
            url: "/tasks/create",
            method: "POST",
            data: JSON.stringify(task)
        }).done(function (result) {
                callback(result);
            });
    };
    this.deleteTask = function (task, callback) {
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'},
            url: "/tasks/delete",
            method: "DELETE",
            data: JSON.stringify(task)
        }).done(function (result) {
                callback(result);
            });
    };
    this.updateTask = function (task, callback) {
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'},
            url: "/tasks/update",
            method: "PUT",
            data: JSON.stringify(task)
        }).done(function (result) {
                callback(result);
            });
    };
    this.createUser = function (user, callback) {
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'},
            url: "/users/create",
            method: "POST",
            data: JSON.stringify(user)
        }).done(function (result) {
                callback(result);
            });
    };
    this.findTasksByAuthorAndState = function (author, state, callback) {
        $.ajax({
            url: "/tasks/author/" + author.getId() + "/" + state,
            method: "GET"
        }).done(function (result) {
                callback(result);
            });
    };
    this.findTasksByAssigneeAndState = function (assignee, state, callback) {
        $.ajax({
            url: "/tasks/assignee/" + assignee.getId() + "/" + state,
            method: "GET"
        }).done(function (result) {
                callback(result);
            });
    };
    this.findAllUsers = function (callback) {
        $.ajax({
            url: "/users",
            method: "GET"
        }).done(function (result) {
                callback(result);
            });
    };
    this.findUserByID = function (userID, callback) {
        $.ajax({
            url: "/users/id/" + userID,
            method: "GET"
        }).done(function (result) {
                callback(result);
            });
    };
    this.findUserByUsername = function (username, callback) {
        $.ajax({
            url: "/users/username?username=" + username,
            method: "GET"
        }).done(function (result) {
                callback(result);
            });
    };
    this.findCurrentUser = function (callback) {
        $.ajax({
            url: "/users/currentUser",
            method: "GET"
        }).done(function (result) {
                callback(result);
            });
    };
}