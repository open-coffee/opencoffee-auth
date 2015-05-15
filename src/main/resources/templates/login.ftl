<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimal-ui, user-scalable=no"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/auth.css"/>
    <title>synyxlogin</title>
</head>
<body>
	<div class="container">
        <div class="green topic box">
            <h1> synyx login </h1>
            <img src="images/stagecornergreen.png" alt="" class="greenCorner">
        </div>
    <#if RequestParameters['error']??>
        <div class="alert alert-danger">
            Login war nicht erfolgreich. Bitte beachten Sie die Groß- und Kleinschreibung.
        </div>
    </#if>
        <div class="white box">
            <div class="whiteContent">
                <#if !RequestParameters['logout']??>
                    <form role="form" action="login" method="post">
                        <div class="form-group">
                            <label for="username">Benutzername:</label>
                            <input type="text" class="form-control" id="username" name="username" autofocus/>
                        </div>
                        <div class="form-group">
                            <label for="password">Passwort:</label>
                            <input type="password" class="form-control" id="password" name="password"/>
                        </div>
                        <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-success glyphicon glyphicon-log-in">
                            Login
                        </button>
                    </form>
                </#if>
                <#if RequestParameters['logout']??>
                    <div>
                        <h2>Willkommen bei synyx!</h2>
                        <p>Sie sind eingeloggt.</p>
                    </div>
                    <form id="logoutForm" name="logoutForm" action="/uaa/logout" method="post">
                        <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button class="btn btn-success" type="submit">Logout</button>
                    </form>
                </#if>
			</div>
		</div>
        <div class="green box"></div>
	</div>
    <script src="https://code.jquery.com/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>