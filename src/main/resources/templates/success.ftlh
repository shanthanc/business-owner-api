<!DOCTYPE html>
<html>
<head>
    <title>Success</title>
    <style>
        body {
            background-color: #f9f9f9;
            font-family: Arial, sans-serif;
        }
        .message-container {
            text-align: center;
            margin-top: 20px;
        }
        .message {
            display: inline-block;
            padding: 20px;
            border: 1px solid #4caf50;
            color: #4caf50;
            margin-bottom: 20px;
        }
        a {
            display: block;
            width: 200px;
            height: 40px;
            margin: 20px auto;
            background-color: #4caf50;
            color: white;
            text-align: center;
            line-height: 40px;
            text-decoration: none;
            border-radius: 3px;
        }
        a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<div class="message-container">
    <div class="message">${successMessage}</div>
    <a href="/business-owner/service/web/home">Go to Homepage</a>
</div>
</body>
</html>
