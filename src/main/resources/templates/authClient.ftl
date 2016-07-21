<#import "/spring.ftl" as spring/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimal-ui, user-scalable=no"/>
    <title>AuthClient</title>

    <link rel="icon" href="/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="/webjars/font-awesome/4.6.3/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/auth.css"/>
</head>

<body>
<div class="container">

${authClient.id}
${authClient.clientId}
${authClient.clientSecret}

</div>

<script src="/webjars/jquery/1.11.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>