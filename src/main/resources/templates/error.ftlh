<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <style>
        body {
            background-color: #f9f9f9;
            font-family: Arial, sans-serif;
        }
        .message-container {
            text-align: center;
            margin-top: 50px;
        }
        .redirect{
            text-align: center;
            margin-top: 30px;
        }
        .message {
            display: inline-block;
            padding: 20px;
            border: 1px solid #f44336;
            color: #f44336;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="message-container">
    <div class="message">${errorMessage}</div>
</div>
<div class="redirect">
    <a href="/business-owner/service/web/home">Go to Homepage</a>
</div>
</body>
</html>
