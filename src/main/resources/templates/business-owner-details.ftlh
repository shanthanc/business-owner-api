<!DOCTYPE html>
<html>
<head>
    <title>Business Owner Details</title>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
</head>
<body>
<h1>Business Owner Details</h1>
<table>
    <thead>
    <tr>
        <th>Business ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Address</th>
        <th>City</th>
        <th>State</th>
        <th>Zipcode</th>
        <th>Date of Birth</th>
        <th>Social Security</th>
        <th>Phone Number</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${businessOwner.businessId}</td>
        <td>${businessOwner.firstName}</td>
        <td>${businessOwner.lastName}</td>
        <td>
            ${businessOwner.address.addressLine1} <br/>
            <#if businessOwner.address.addressLine2?has_content>
                ${businessOwner.address.addressLine2} <br/>
            </#if>
        </td>
        <td>${businessOwner.address.city}</td>
        <td>${businessOwner.address.state}</td>
        <td>${businessOwner.address.zipcode}</td>
        <td>${businessOwner.dateOfBirth}</td>
        <td>###-##-${businessOwner.ssn?substring(businessOwner.ssn?length - 4)}</td>
        <td>###-###-${businessOwner.phoneNumber?substring(businessOwner.phoneNumber?length - 4)}</td>
    </tr>
    </tbody>
</table>
<a href="/business-owner/service/web/home">Back to Home</a>
</body>
</html>
