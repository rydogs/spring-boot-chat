angular.module("app", []).controller("home", function($scope, $http) {
    var self = this;
    var stompClient = null;
    $scope.messages = [];

    $http.get("/user").success(function(data) {
        self.user = data.userAuthentication.details;
        self.authenticated = true;
    }).error(function() {
        self.user = "N/A";
        self.authenticated = false;
    });

    $scope.connect = function() {
        connect();
    }

    $scope.disconnect = function() {
        disconnect();
    }

    $scope.send = function() {
        stompClient.send("/app/chat", {}, $scope.message);
        $scope.message = "";
    }

    var connect = function() {
        var socket = new SockJS('/chat-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
            $scope.messages = [];
            $scope.connected = true;
            stompClient.subscribe('/topic/messages', function(message) {
                $scope.messages.push(JSON.parse(message.body));
                $scope.$digest();
            });
            stompClient.subscribe("/app/participants", function(message) {
                $scope.participants = JSON.parse(message.body);
                $scope.$digest();
            });
        });
    }

    var disconnect = function() {
        if (stompClient != null) {
            stompClient.disconnect();
            $scope.messages = [];
            $scope.connected = false;
        }
        console.log("Disconnected");
    }
}).directive("userImage", function() {
    return {
      restrict: "E",
      scope: {
        "user": "=",
        "size": "="
      },
      template: '<img title="{{user.displayName}}" height="{{size}}" width="{{size}}" class="img-rounded" ng-src="https://graph.facebook.com/{{user.id}}/picture?type=large" />'
    };
});