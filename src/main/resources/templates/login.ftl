<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimal-ui, user-scalable=no"/>
    <link rel="icon" href="favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/auth.css"/>
    <title>synyx login</title>
    <script src="https://code.jquery.com/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
<div class="login container">
  <div class="col-xs-12 col-sm-10 col-sm-offset-1 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
  <#if RequestParameters['error']??>
    <div class="alert alert-danger">
      Login war nicht erfolgreich. Bitte beachten Sie die Groß- und Kleinschreibung.
    </div>
  </#if>
    <div class="login--header">
        <img alt="synyx Logo" src="images/logo.png">
        <h2>Login</h2>
    </div>
    <div class="login--form">
        <#if !RequestParameters['logout']??>
            <form role="form" action="login" method="post">
                <div class="form-group">
                    <label for="username" class="hidden">Benutzername:</label>
                    <input type="text" class="form-control" id="username" name="username" placeholder="Benutzername" autofocus/>
                </div>
                <div class="form-group">
                    <label for="password" class="hidden">Passwort:</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Passwort"/>
                </div>
                <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-block btn-success">
                    <span class="glyphicon glyphicon-log-in"></span>
                    Login
                </button>
            </form>
        </#if>
        <#if RequestParameters['logout']??>
            <div>
                <h2>Willkommen bei synyx!</h2>
                <p>Sie sind eingeloggt.</p>
            </div>
            <form id="logoutForm" name="logoutForm" action="logout" method="post">
                <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-success" type="submit">Logout</button>
            </form>
            <script>
                $('document').ready(function () {
                    $logoutForm = $('#logoutForm');
                    var referrer = document.referrer;
                    var action = 'logout';
                    if(referrer) {
                        action += '?redirect=' + referrer;
                    }
                    $logoutForm.attr('action', action);
                    $logoutForm.submit();
                });
            </script>
        </#if>
    </div>
  </div>
</div>
</body>
</html>