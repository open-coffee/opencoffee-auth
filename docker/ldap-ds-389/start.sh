#!/bin/bash
setup-ds-admin -s -f /tmp/ds-setup.inf 
#schema cannot be added via ldapadd -> put it in the schema folder of the conf-dir
cp /tmp/coffeenet-schema.ldif /etc/dirsrv/slapd-ldap/schema/98coffeenet.ldif

/usr/sbin/ns-slapd -D /etc/dirsrv/slapd-ldap -d 16384 &
LDAP_PID=$!
sleep 5

# dump data
ldapadd -D "cn=Directory Manager" -w $DS_PASSWORD -h localhost -p 9389 -f /tmp/coffeenet-data-people.ldif -c
ldapadd -D "cn=Directory Manager" -w $DS_PASSWORD -h localhost -p 9389 -f /tmp/coffeenet-data-auto-generated-people.ldif -c
ldapadd -D "cn=Directory Manager" -w $DS_PASSWORD -h localhost -p 9389 -f /tmp/coffeenet-data-roles.ldif -c

# Switch port
ldapmodify -D "cn=Directory Manager" -w $DS_PASSWORD -h localhost -p 9389 <<EOS
dn: cn=config
replace: nsslapd-port
nsslapd-port: 389
EOS

echo >&2 "Data imported, restarting LDAP"

# Restart on exposed port
kill $LDAP_PID
wait


/usr/sbin/ns-slapd -D /etc/dirsrv/slapd-ldap -d 16384 &
LDAP_PID=$!
sleep 2
echo >&2 "LDAP ready"
wait $LDAP_PID
