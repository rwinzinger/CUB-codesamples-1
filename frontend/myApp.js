'use strict';

var myApp = angular.module('myApp', ['ngResource', 'ngRoute']);

myApp.controller('AppController', ['$scope', '$http', function($scope, $http) {
  $scope.errorMsg = null;
  $scope.customer = null;
  $scope.authToken = null;
  $scope.articles = null;
  $scope.nextArticles = null;
  $scope.prevArticles = null;
  $scope.detailedArticle = null;
  $scope.shoppingCart = null;

  $scope.login = function() {
      $scope.errorMsg = null;

      // login customer & get customer info
      $http.post("/loginsrv/api/v1/login", {"username":$scope.username, "password":$scope.password})
      .then(
        function(loginResponse) {
          $scope.authToken = loginResponse.data.token;

          // get user data
          // alert("thank you for logging in! token: "+$scope.authToken)
          /* */
          $http.get(loginResponse.data._links.customer.href)
          .then(
            function(customerResponse) {
              $scope.customer = customerResponse.data;

              // load articles
              $scope.loadArticles();
            },
            function(customerError) {
              $scope.customer = null;
              $scope.authToken = null;

              if (customerError.status == -1) {
                $scope.errorMsg = "unknown error";
              } else {
                $scope.errorMsg = customerError.status+": "+customerError.statusText;
              }
            }
          )    
          /* */      
        },
        function(loginError) {
          $scope.customer = null;
          $scope.authToken = null;

          if (loginError.status == -1) {
            $scope.errorMsg = "unknown error";
          } else {
            $scope.errorMsg = loginError.status+": "+loginError.statusText;
          }
        }
      );
  }

  $scope.loadArticles = function(href) {
    $scope.prevArticles = null;
    $scope.nextArticles = null;
    $scope.detailedArticle = null;
    
    if (href == null) {
      href = "/articlesrv/api/v1/articles?ps=7";
    }

    // get article-list
    $http.get(href)
    .then(
      function(articleResponse) {
        $scope.articles = articleResponse.data.articles;
        $scope.prevArticles = articleResponse.data._links.prev;
        $scope.nextArticles = articleResponse.data._links.next;
      },
      function(articleError) {
        // show some error msg
        $scope.articles = null;
        $scope.nextArticles = null;
        $scope.prevArticles = null;
        $scope.detailedArticle = null;
      }
    );
  }

  $scope.setSelected = function(article) {
    // fetch detailed data from article service
    $scope.detailedArticle = article;
  }

  $scope.addToCart = function(article) {
    if ($scope.shoppingCart == null) {
      $scope.shoppingCart = {articles:[]}
    }

    $scope.shoppingCart.articles.push(article);

    $scope.shoppingCart.sumArticles = $scope.shoppingCart.articles.reduce(
      function(result, item) {
        return result+item.price;
      }, 0 // initial value
    );
    $scope.shoppingCart.sumShipping = $scope.shoppingCart.articles.reduce(
      function(result, item) {
        return result+item.shipping;
      }, 0
    );

    $scope.shoppingCart.overallPrice = $scope.shoppingCart.sumArticles+$scope.shoppingCart.sumShipping;
  }

  $scope.buyArticles = function() {
    alert("thank you for shopping!")
    $scope.shoppingCart = null;
  }

  $scope.logout = function() {
    // call serverside logout
    $scope.errorMsg = null;
    $scope.customer = null;
    $scope.authToken = null;
    $scope.username = null;
    $scope.password = null;
    $scope.articles = null;
    $scope.nextArticles = null;
    $scope.prevArticles = null;
    $scope.detailedArticle = null;
    $scope.shoppingCart = null;
  }
}]);
