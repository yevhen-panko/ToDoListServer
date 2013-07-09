$(document).ready(function () {
    var ajaxAPI = new AjaxAPI();
    var utils = new Utils();
    if (utils.getURLParameter("error")){
        $("#loginError").append("Your email or password is wrong. Please try again.");
    }
    $("#RegistrationButton").click(function(){
        $("#Login").fadeOut(0);
        $("#Registration").fadeIn(200);
        $("#loginError").empty();
    });

    $("#RegistrationEmail").keyup(function(){
        var email = $('#RegistrationEmail').val();
        if (email.length > 0){
            if (utils.isValidEmailAddress(email)){
                $("#RegistrationEmail").css("background", "rgba(16, 194, 15, 0.22)");
                $("#ConfirmRegistration").removeAttr("disabled");
            } else {
                $("#RegistrationEmail").css("background", "rgba(210, 52, 22, 0.15)");
                $("#ConfirmRegistration").attr("disabled", true);
            }
        } else {
            $("#RegistrationEmail").css("background", "none");
            $("#ConfirmRegistration").attr("disabled", true);
        }
    });

    $("#RegistrationPassword").keydown(function (event) {
        if (event.keyCode == 13) {
            confirmRegistration();
        }
    });

    $("#ConfirmRegistration").click(function(){
        confirmRegistration();
    });

    function confirmRegistration(){
        var id;
        var username = $("#RegistrationEmail").val();
        var password = $("#RegistrationPassword").val();
        var authority = "ROLE_USER";
        var enabled = true;
        var user = new User(id, username, password, authority, enabled);
        var count = 0;
        $("#registrationSuccess").empty();
        ajaxAPI.findAllUsers(function(usersJSON){
            for (var i=0; i<usersJSON.length; i++){
                if (utils.createUserFromJSON(usersJSON[i]).getUsername() == user.getUsername()){
                    count++;
                }
            }
            if (count != 0){
                $("#registrationError").append("Sorry, user with that email is already exists.");
            } else {
                ajaxAPI.createUser(user, function(){
                    $("#registrationError").empty();
                    $("#registrationSuccess").append("Registration is successful");
                    $("#LoginEmail").val(user.getUsername());
                    $("#LoginPassword").val(user.getPassword());
                    $("#Registration").fadeOut(0);
                    $("#Login").fadeIn(200);
                });
            }
        });
    }
});