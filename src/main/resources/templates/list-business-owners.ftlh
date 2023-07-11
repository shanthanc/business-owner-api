<!DOCTYPE html>
<html>
<head>
    <title>Business Owners</title>
    <style>
        .businessOwner {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<h1>Business Owners</h1>
<#list businessOwners as businessOwner>
    <div class="businessOwner">
        <h2>${businessOwner.firstName} ${businessOwner.lastName}</h2>
        <p>Phone Number: ###-###-${businessOwner.phoneNumber?substring(businessOwner.phoneNumber?length - 4)}</p>
        <p>Date of Birth: ${businessOwner.dateOfBirth}</p>
        <p>Social security: ###-##-${businessOwner.ssn?substring(businessOwner.ssn?length - 4)}</p>
        <p>Address: </p>
        <p>${businessOwner.address.addressLine1},
            <#if businessOwner.address.addressLine2?has_content>${businessOwner.address.addressLine2},</#if>
            ${businessOwner.address.city}, ${businessOwner.address.state}, ${businessOwner.address.zipcode}
        </p>
    </div>
</#list>
<div class="separation"></div>
<div class="redirect">
    <a href="/business-owner/service/web/home">Go to Homepage</a>
</div>
</body>
</html>
