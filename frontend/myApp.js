'use strict';

var myApp = angular.module('myApp', ['ngResource', 'ngRoute']);

myApp.controller('AppController', ['$scope', '$http', '$q', function($scope, $http, $q) {
  $scope.errorMsg = null;
  $scope.customer = null;
  $scope.authToken = null;
  $scope.articles = null;
  $scope.nextArticles = null;
  $scope.prevArticles = null;
  $scope.detailedArticle = null;
  $scope.shoppingCart = null;

  $scope.urls = {
    login : {"self":"/loginsrv/api/v1/login"},
    article : {"list":"/articlesrv/api/v1/articles"},
    cart : {},
    customer : {}
  }

  $scope.login = function() {
      $scope.errorMsg = null;

      // login customer & get customer info
      $http.post($scope.urls.login.self, {"username":$scope.username, "password":$scope.password})
      .then(
        function(loginResponse) {
          $scope.authToken = loginResponse.data.token;

          // get user data
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
      href = $scope.urls.article.list;
    }

    // get article-list
    $http.get(href)
    .then(
      function(articleResponse) {
        $scope.articles = articleResponse.data.articles;
        if (articleResponse.data._links != null) {
          $scope.prevArticles = articleResponse.data._links.prev;
          $scope.nextArticles = articleResponse.data._links.next;
        }
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

  $scope.createShoppingCart = function() {
    $http.post("/cartsrv/api/v1/customers/"+$scope.username+"/carts")
      .then(
        function(cartResponse) {
          $scope.shoppingCart = cartResponse.data.shoppingCart;
          $scope.urls.cart.self = cartResponse.data._links.self.href;
          $scope.urls.cart.discard = cartResponse.data._links.discard.href;
        },
        function(cartError) {
          $scope.shoppingCart = null;
          
          if (cartError.status == -1) {
            $scope.errorMsg = "unknown error";
          } else if (cartError.status == 409) {
            $scope.warnMsg = "You already have a shopping cart!";
            $scope.urls.cart.self = "/cartsrv/api/v1/customers/"+$scope.username+"/carts/"+cartError.data.message;
            $scope.urls.cart.discard = $scope.urls.cart.self;
            $http.get($scope.urls.cart.self)
              .then(
                function(cartResponse) {
                  $scope.shoppingCart = cartResponse.data.shoppingCart;
                  $scope.refreshCart();
                },
                function(cartError) {
                  $scope.shoppingCart = null;
          
                  if (cartError.status == -1) {
                    $scope.errorMsg = "unknown error";
                  } else {
                    $scope.errorMsg = cartError.status+": "+cartError.statusText;
                  }
                }
              )
          } else {
            $scope.errorMsg = cartError.status+": "+cartError.statusText;
          }
        }
      );
  }

  $scope.addToCart = function(article) {
    if ($scope.shoppingCart == null) {
      $scope.warnMsg = "please create a shopping cart first!"
      return
    }

    // also send detail-link: "detail":article._links.detail
    $http.post($scope.urls.cart.self+"/items", {"articleId":article.articleid})
      .then(
        function(cartResponse) {
          $scope.shoppingCart = cartResponse.data.shoppingCart;
          $scope.refreshCart();
        },
        function(cartError) {
          if (cartError.status == -1) {
            $scope.errorMsg = "unknown error";
          } else {
            $scope.errorMsg = cartError.status+": "+cartError.statusText;
          }
        }
      )
  }

  $scope.refreshCart = function() {
    var articles = [];
    var articleDetailPromises = [];
    for (var i = 0; i < $scope.shoppingCart.items.length; i++) {
      var item = $scope.shoppingCart.items[i];

      var promise = $http.get("/articlesrv/api/v1/article/"+item.articleId);
      promise.then(
      function(success) {
        articles.push(success.data);
      }, function(error) {
        if (error.status == -1) {
          $scope.errorMsg = "unknown error";
        } else {
          $scope.errorMsg = error.status+": "+error.statusText;
        }
      })
      articleDetailPromises.push(promise);
    }

    $q.all(articleDetailPromises).then(
      function(success) {
        $scope.shoppingCart.articles = articles;

        var sumArticles, sumShipping;

         sumArticles = $scope.shoppingCart.articles.reduce(
           function(result, item) {
             return result+item.price;
           }, 0 // initial value
         );
         sumShipping = $scope.shoppingCart.articles.reduce(
           function(result, item) {
             return result+item.shipping;
           }, 0 // initial value
         );
         $scope.shoppingCart.sumArticles = sumArticles;
         $scope.shoppingCart.sumShipping = sumShipping;
         $scope.shoppingCart.overallPrice = $scope.shoppingCart.sumArticles+$scope.shoppingCart.sumShipping;
      },
      function(error) {
        $scope.errorMsg = "error: "+error;
      }
    )
  }

  $scope.buyArticles = function() {
    $http.post($scope.urls.cart.self+"/checkout").then(
      function(success) {
        $scope.successMsg = "Thanks for shopping with us";
        $scope.shoppingCart = null;
      }, function(error) {
        if (error.status == -1) {
          $scope.errorMsg = "unknown error";
        } else {
          $scope.errorMsg = error.status+": "+error.statusText;
        }
      }
    )
  }

  $scope.discardCart = function() {
    $http.delete($scope.urls.cart.discard).then(
      function(success) {
        $scope.warnMsg = "We think that something went wrong now ...";
        $scope.shoppingCart = null;
      }, function(error) {
        if (error.status == -1) {
          $scope.errorMsg = "unknown error";
        } else if (error.status == 410) {
          $scope.successMsg = "Sorry that you couldn't find something to buy";
          $scope.shoppingCart = null;
        } else {
          $scope.errorMsg = error.status+": "+error.statusText;
        }
      }
    )
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

  $scope.dismissErrorMsg = function() {
    $scope.errorMsg = null;
  }

  $scope.dismissWarnMsg = function() {
    $scope.warnMsg = null;
  }

  $scope.dismissSuccessMsg = function() {
    $scope.successMsg = null;
  }
}]);
