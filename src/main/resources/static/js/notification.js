var stompClient = null;
var privateStompClient = null;
socket = new SockJS('/ws');

privateStompClient = Stomp.over(socket);
privateStompClient.connect({}, function (frame) {
    privateStompClient.subscribe('/users/notification', function (result) {
        createAlert(JSON.parse(result.body));
        //updatePanel()
    });
});

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