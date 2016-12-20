#!/usr/bin/env bash

#####
# With this script you can generate a list of users in the sldif format
# to use while creating the ldap docker file for testing purposes
#####

> coffeenet-data-auto-generated-people.ldif
echo "username,password" > loadTestCredentials.csv;

for i in {1..2000};
do
ldapUsers="# user
dn: uid=user$i,ou=People,dc=synyx,dc=coffee
givenName: user$i
sn: user$i
cn: user$i $i
mail: user$i@synyx.coffee
uid: user$i
userPassword: user$i
objectClass: top
objectClass: inetorgperson
objectClass: person
objectClass: coffeeNetUser
";
echo "$ldapUsers" >> coffeenet-data-auto-generated-people.ldif;

loadTestUsers="user$i,user$i";
echo "$loadTestUsers" >> loadTestCredentials.csv;
done