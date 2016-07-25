<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimal-ui, user-scalable=no"/>
    <title><@spring.message "clients.all.title"/></title>

    <link rel="icon" href="/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/auth.css"/>
</head>
<body>
<nav class="navbar navbar-default" id="app-header">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-8 col-sm-offset-2">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                            data-target="#clients-navbar-collapse" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a href="/">
                        <img class="navbar--synyx-logo" src="/images/logo_name.png">
                    </a>
                </div>

                <div class="collapse navbar-collapse" id="clients-navbar-collapse">
                    <ul class="nav navbar-nav navbar-left">
                        <li class="active">
                            <a href="/clients"><@spring.message "clients.all.title"/></a>
                        </li>
                        <li>
                            <a href="/clients/new"><@spring.message "clients.new.title"/></a>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right navbar--user-info">
                        <li>
                            <span id="navhbar--current-user">
                                <@security.authentication property="principal.username" />
                            </span>
                        </li>
                        <li>
                            <form action="/logout" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" id="navbar--logout-link"><span class="glyphicon glyphicon-log-out"></span>Logout</button>
                            </form>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-8 col-sm-offset-2">
        <#list clients as client>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <a href="/clients/${client.clientId}">${client.clientId}</a>
                    <@security.authorize access="hasRole('ROLE_safsa')">

                    <div class="pull-right">
                        <a href="/clients/${client.clientId}/edit" class="glyphicon glyphicon-cog"></a>
                        <a href="/clients/${client.clientId}/delete" class="glyphicon glyphicon-trash"></a>
                    </div>
                    </@security.authorize>
                </div>
                <div class="panel-body">
                    ${client.registeredRedirectUri}
                </div>
            </div>
        </#list>
        </div>
    </div>
</div>
<script src="/webjars/jquery/1.11.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script>
    $(function(){
        $('.table tr[data-href]').each(function(){
            $(this).css('cursor','pointer').hover(
                    function(){
                        $(this).addClass('active');
                    },
                    function(){
                        $(this).removeClass('active');
                    }).click( function(){
                        document.location = $(this).attr('data-href');
                    }
            );
        });
    });
</script>
</body>
</html>