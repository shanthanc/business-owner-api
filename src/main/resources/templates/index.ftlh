<!DOCTYPE html>
<html>
<head>
    <title>Business Owner Administration</title>
    <style>
        body {
            background-color: #f9f9f9;
            font-family: Arial, sans-serif;
        }
        h1 {
            text-align: center;
            color: #333;
            padding: 20px 0;
        }
        .menu {
            width: 100%;
            padding: 10px;
            background-color: #555;
            color: white;
            text-align: center;
            margin-bottom: 20px;
        }
        .menu a {
            color: white;
            margin: 0 10px;
            text-decoration: none;
        }
        .menu a:hover {
            color: #ddd;
        }
    </style>
</head>
<body>
<h1>Hi Administrator, Welcome to Business Owner Administration Page</h1>
<div class="menu">
    <a href="/business-owner/service/web/addBusinessOwner">Add Business Owner</a>
    <a href="/business-owner/service/web/updateBusinessOwner">Update Business Owner</a>
    <a href="/business-owner/service/web/getBusinessOwnerById">Business Owner details by Id</a>
    <a href="/business-owner/service/web/getBusinessOwnersByFirstName">List Business Owners by First Name</a>
    <a href="/business-owner/service/web/getBusinessOwnersByLastName">List Business Owners by LastName</a>
    <a href="/business-owner/service/web/deleteBusinessOwnerById">Delete Business Owner</a>
</div>
</body>
</html>
