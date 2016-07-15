<#import "/spring.ftl" as spring/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimal-ui, user-scalable=no"/>
    <title><@spring.message "login.title"/></title>

    <link rel="icon" href="/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/auth.css"/>
</head>
<body>
<div class="login container">
    <div class="col-xs-12 col-sm-10 col-sm-offset-1 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">

        <div class="login--form">
            <div class="login--header">
                <img alt="synyx Logo" src="/images/logo.png">
                <h2><@spring.message "logout.name"/></h2>
            </div>

            <form id="logoutForm" name="logoutForm" action="/logout" method="post">
                <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-block btn-danger" type="submit">
                    <span class="glyphicon glyphicon-log-out"></span>
                    <@spring.message "login.button.logout"/>
                </button>
            </form>
        </div>

    </div>
</div>

<script src="/webjars/jquery/1.11.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script>
    $('#logoutForm').ready(function () {
        $logoutForm = $('#logoutForm');
        var referrer = document.referrer;
        var action = 'logout';
        if (referrer) {
            action += '?redirect=' + referrer;
        }
        $logoutForm.attr('action', action);
        $logoutForm.submit();
    });
</script>
</body>
</html>