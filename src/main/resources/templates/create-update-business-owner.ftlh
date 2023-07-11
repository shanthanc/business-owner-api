<#ftl encoding="UTF-8">
<!DOCTYPE html>
<html>
<head>
    <title>Add business owner into the system</title>
</head>
<body>
<div class="container">
    <h1>
        <#if !isUpdate>Add</#if>
        <#if isUpdate>Update</#if>
        Business Owner
    </h1>
</div>

<div>
    <form action="<#if isUpdate>/business-owner/service/web/update</#if>
<#if !isUpdate>/business-owner/service/web/create</#if>" method="post"
          name="businessOwner">
        <table class="createUpdatetable">
            <tbody>
            <thead>
            <tr>
                <th>Field</th>
                <th>Value</th>
            </tr>
            </thead>
            </tbody>
            <#if isUpdate>
                <tr>
                    <th>Business-Id</th>
                    <td><input name="businessId" type="number" pattern="^\d+$"
                               value="<#if businessOwner.businessId??>${businessOwner.businessId}</#if>"/>
                    </td>
                </tr>
            </#if>

            <tr>
                <th>First Name</th>
                <td><input name="firstName" type="text"
                            <#if !isUpdate >pattern="^[a-zA-Z\s'-]+$"</#if>
                            <#if isUpdate >pattern="^[a-zA-Z\s'-]*$"</#if>
                           aria-errormessage="Should only contain alphabets, hypen or apostrophe"
                           value="<#if businessOwner.firstName??>${businessOwner.firstName}</#if>"/>
                </td>
            </tr>
            <tr>
                <th>Last Name</th>
                <td><input name="lastName" type="text"
                           <#if !isUpdate >pattern="^[a-zA-Z\s'-]+$"</#if>
                            <#if isUpdate >pattern="^[a-zA-Z\s'-]*$"</#if>
                           value="<#if businessOwner.lastName??>${businessOwner.lastName}</#if>"/>
                </td>
            </tr>
            <#if isUpdate>
            <tr>
                <th>If updating address, please enter entire address </th>
            </tr>
            </#if>
            <tr>
                <th>Address Line 1</th>
                <td><input name="addressLine1" , type="text"
                           value="<#if businessOwner.address.addressLine1??>${businessOwner.address.addressline1}</#if>"
                    /></td>
            </tr>
            <tr>
                <th>Address Line 2</th>
                <td><input name="addressLine2" , type="text"
                           value="<#if businessOwner.address.addressLine2??>${businessOwner.address.addressline2}</#if>"
                    /></td>
            </tr>
            <tr>
                <th>City</th>
                <td><input name="city" , type="text"
                           <#if !isUpdate >pattern="^[a-zA-Z\s-]*$"</#if>
                            <#if isUpdate >pattern="^[a-zA-Z\s-]*$"</#if>
                           value="<#if businessOwner.address.city??>${businessOwner.address.city}</#if>"/>
                </td>
            </tr>

            <tr>
                <th>State</th>
                <td><select name="state">
                        <option value="">None</option>
                        <option value="AL">Alabama</option>
                        <option value="AK">Alaska</option>
                        <option value="AZ">Arizona</option>
                        <option value="AR">Arkansas</option>
                        <option value="CA">California</option>
                        <option value="CO">Colorado</option>
                        <option value="CT">Connecticut</option>
                        <option value="DE">Delaware</option>
                        <option value="FL">Florida</option>
                        <option value="GA">Georgia</option>
                        <option value="HI">Hawaii</option>
                        <option value="ID">Idaho</option>
                        <option value="IL">Illinois</option>
                        <option value="IN">Indiana</option>
                        <option value="IA">Iowa</option>
                        <option value="KS">Kansas</option>
                        <option value="KY">Kentucky</option>
                        <option value="LA">Louisiana</option>
                        <option value="ME">Maine</option>
                        <option value="MD">Maryland</option>
                        <option value="MA">Massachusetts</option>
                        <option value="MI">Michigan</option>
                        <option value="MN">Minnesota</option>
                        <option value="MS">Mississippi</option>
                        <option value="MO">Missouri</option>
                        <option value="MT">Montana</option>
                        <option value="NE">Nebraska</option>
                        <option value="NV">Nevada</option>
                        <option value="NH">New Hampshire</option>
                        <option value="NJ">New Jersey</option>
                        <option value="NM">New Mexico</option>
                        <option value="NY">New York</option>
                        <option value="NC">North Carolina</option>
                        <option value="ND">North Dakota</option>
                        <option value="OH">Ohio</option>
                        <option value="OK">Oklahoma</option>
                        <option value="OR">Oregon</option>
                        <option value="PA">Pennsylvania</option>
                        <option value="RI">Rhode Island</option>
                        <option value="SC">South Carolina</option>
                        <option value="SD">South Dakota</option>
                        <option value="TN">Tennessee</option>
                        <option value="TX">Texas</option>
                        <option value="UT">Utah</option>
                        <option value="VT">Vermont</option>
                        <option value="VA">Virginia</option>
                        <option value="WA">Washington</option>
                        <option value="WV">West Virginia</option>
                        <option value="WI">Wisconsin</option>
                        <option value="WY">Wyoming</option>
                        <option value="DC">District Of Columbia</option>
                        <option value="AS">American Samoa</option>
                        <option value="GU">Guam</option>
                        <option value="MP">Northern Mariana Islands</option>
                        <option value="PR">Puerto Rico</option>
                        <option value="UM">U.S. Minor Outlying Islands</option>
                        <option value="VI">U.S. Virgin Islands</option>
                    </select>
                </td>
            </tr>

            <tr>
                <th>zipcode</th>
                <td><input name="zipcode" , type="text" pattern="^\d{5}(-\d{4})?$"
                           value="<#if businessOwner.address.zipcode??>${businessOwner.address.zipcode}</#if>"/></td>
            </tr>

            <tr>
                <th>Date Of Birth</th>
                <td><input name="dob" , type="date" pattern="^(0[1-9]|1[0-2])-((0[1-9]|1\d|2[0-8])|(29-02-(19|20)([02468][048]|[13579][26]))|(29-(0[13-9]|1[0-2])-(19|20)\d\d)|(30-(0[13-9]|1[0-2])-(19|20)\d\d)|(31-(0[13578]|1[02])-(19|20)\d\d))$"
                           value="<#if businessOwner.dob??>${businessOwner.dob}</#if>"/>
                </td>
            </tr>

            <tr>
                <th>Social Security (XXX-XX-XXXX)</th>
                <td><input type="text" id="ssn" name="ssn" pattern="\d{9}"
                           value="<#if businessOwner.ssn??>${businessOwner.ssn}</#if>"/>
                </td>
            </tr>

            <tr>
                <th>Phone number +1</th>
                <td><input type="tel" id="phoneNumber" name="phoneNumber" pattern="[0-9]{10}"
                           value="<#if businessOwner.phoneNumber??>${businessOwner.phoneNumber}</#if>"/>
                </td>
            </tr>
            <tr>
                <th><input type="submit" value="Submit"></th>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
