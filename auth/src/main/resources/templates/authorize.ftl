<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimal-ui, user-scalable=no"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../css/auth.css"/>
	<title>Zugriff erlauben</title>
</head>
<body>
	<div class="container">
        <div class="green topic box">
            <h1> Zugriff erlauben </h1>
            <img src="../images/stagecornergreen.png" alt="" class="greenCorner">
        </div>
        <div class="white box">
            <div class="whiteContent">
                <p class="lead">
                    Soll der Anwendung "${authorizationRequest.clientId}" der Zugriff auf Ihre Ressourcen
                    gestattet werden?
                </p>
				<ul>
					<li>URL: ${authorizationRequest.redirectUri}</li>
                    <li>scope: ${authorizationRequest.scope?join(", ")}</li>
                </ul>
				<div class="form-container" align="right">
                <form id="confirmationForm" name="confirmationForm"
                      action="../oauth/authorize" method="post">
                    <input name="user_oauth_approval" value="true" type="hidden" />
                    <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" id="scope.openid" name="scope.openid" value="true">
                    <input type="hidden" id="authorize" name="authorize" value="Authorize">
                    <button class="btn btn-success" type="submit">Ja</button>
                </form>
                <form id="denyForm" name="confirmationForm"
                      action="../oauth/authorize" method="post">
                    <input name="user_oauth_approval" value="true" type="hidden" />
                    <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" id="scope.openid" name="scope.openid" value="false">
                    <input type="hidden" id="authorize" name="authorize" value="Authorize">
                    <button class="btn btn-danger" type="submit">Nein</button>
                </form>
                </div>
            </div>
        </div>
        <div class="green box"></div>


	</div>
    <script src="https://code.jquery.com/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>