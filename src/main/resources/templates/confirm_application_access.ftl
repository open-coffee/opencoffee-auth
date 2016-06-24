<#import "/spring.ftl" as spring/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimal-ui, user-scalable=no"/>
    <title><@spring.message "authorize.title"/></title>

    <link rel="icon" href="../favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="../webjars/font-awesome/4.6.3/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="../webjars/bootstrap/3.3.6/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../css/auth.css"/>
</head>

<body>
<div class="authorize container">
    <div class="col-xs-12 col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-8 col-lg-offset-2">

        <div class="authorize--header">
            <img alt="synyx Logo" src="../images/logo.png">
            <h2>
                <@spring.message "authorize.title"/>
            </h2>
        </div>
        <div class="authorize--form">
            <p>
            <@spring.message "authorize.text.first"/>
            </p>
            <p class="authorize--client-id">
            ${authorizationRequest.clientId}
            </p>
            <p>
            <@spring.message "authorize.text.second"/>
            </p>
            <div class="row">
                <div class="col-xs-6">
                    <form role="form" action="../oauth/authorize" method="post">
                        <div class="form-group">
                            <input name="user_oauth_approval" value="true" type="hidden"/>
                            <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" id="scope.openid" name="scope.openid" value="true">
                            <input type="hidden" id="authorize" name="authorize" value="Authorize">
                            <button class="btn btn-success" type="submit">
                                <i class="fa fa-thumbs-up"></i>
                            </button>
                        </div>
                    </form>
                </div>

                <div class="col-xs-6">
                    <form role="form" action="../oauth/authorize" method="post">
                        <div class="form-group">
                            <input name="user_oauth_approval" value="true" type="hidden"/>
                            <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" id="scope.openid" name="scope.openid" value="false">
                            <input type="hidden" id="authorize" name="authorize" value="Authorize">
                            <button class="btn btn-danger" type="submit">
                                <i class="fa fa-thumbs-down"></i>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="../webjars/jquery/1.11.1/jquery.min.js"></script>
<script src="../webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>