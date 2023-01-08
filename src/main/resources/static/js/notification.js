var stompClient = null;
var privateStompClient = null;
socket = new SockJS('/ws');

privateStompClient = Stomp.over(socket);
privateStompClient.connect({}, function (frame) {
    privateStompClient.subscribe('/users/notification', function (result) {
        let obj = JSON.parse(result.body);
        notificationSound();
        createAlert(obj);
        updateNavbar(obj.count);
        updatePanel();
        checkPopover();
    });
});

function notificationSound() {
    var audio = new Audio('/audio/notification.mp3');
    audio.play();
}

function checkPopover() {
    if(document.getElementById("readAllNotificationsBtn")!=null){
        document.getElementById('popover').click();
        setTimeout(() => {
            document.getElementById('popover').click();
        } , 100);
    }
}

function updateNavbar(count){
    document.getElementById('notificationBadge').innerHTML=''+count;
}

function updatePanel() {
    $.post("/falsi/notification/refresh").done(function (fragment) {
        $('#popover').attr('data-content', fragment);
    });
}

function createAlert(notificationAlert) {
    document.getElementById('notificationAlert').insertAdjacentHTML('beforeend', getAlertText(notificationAlert));
    setTimeout(() => {
        document.getElementById(notificationAlert.id).remove();
    }
        , 3000);
}

function getAlertText(notificationAlert) {
    return ' <div class="alert alert-success" id="' + notificationAlert.id + '">'
        + '<button type="button" class="close" data-dismiss="alert">x</button>'
        + '<strong>' + notificationAlert.text + '</strong>'
        + '</div>';
}

function readAll(){
    $.get("/falsi/notification/read-all").done(function () {
        updateNavbar(0);
        $('#popover').popover('hide');
        $('#popover').attr('data-content', '');
    });
}