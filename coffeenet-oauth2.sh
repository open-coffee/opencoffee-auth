#!/bin/bash

# A simple cURL CoffeeNet OAuth2 authenticator
# depends on Python's built-in json module to prettify output
#
# Usage:
#	./coffeenet-oauth2.sh token client - to return all token informaton of a client
#	./coffeenet-oauth2.sh token user - to return all token informaton of a user

DEFAULT_OAUTH_TOKEN_URL="https://auth-test.synyx.coffee/oauth/token"

while getopts u:d:p:f: option
do
	case "${option}"
		in
		u) OAUTH_TOKEN_URL==${OPTARG:-$DEFAULT_OAUTH_TOKEN_URL};;
		s) SCOPE=${OPTARG:openid};;
	esac
done

OAUTH_TOKEN_URL="https://auth-test.synyx.coffee/oauth/token"

set -e

if [ "$1" == "token" ] && [ "$2" == "client" ]; then

	echo "Type the client id, followed by [ENTER]:"
	read CLIENT_ID

	echo "Type the client secret, followed by [ENTER]:"
	read -s CLIENT_SECRET

	RESPONSE=`curl --request POST --header 'Content-Type: application/x-www-form-urlencoded' -u $CLIENT_ID:$CLIENT_SECRET --silent $OAUTH_TOKEN_URL --data "grant_type=client_credentials&scope=$SCOPE"`

	RESPONSE_JSON=`echo "$RESPONSE" | python -mjson.tool`
	ACCESS_TOKEN=`echo "$RESPONSE" | python -mjson.tool | grep -oP 'access_token"\s*:\s*"\K(.*)"' | sed 's/"//'`

	echo ""
	echo "> RESPONSE:"
	echo "$RESPONSE_JSON"
	echo ""

	echo "------------"
	echo ""
	echo "> Access Token:"
	echo "$ACCESS_TOKEN"

elif [ "$1" == "token" ] && [ "$2" == "user" ]; then

	echo "Type the client id, followed by [ENTER]:"
	read CLIENT_ID

	echo "Type the client secret, followed by [ENTER]:"
	read -s CLIENT_SECRET

	echo "Type your username, followed by [ENTER]:"
	read USERNAME

	echo "Type your password, followed by [ENTER]:"
	read -s  PASSWORD

	RESPONSE=`curl --request POST --header 'Content-Type: application/x-www-form-urlencoded' -u $CLIENT_ID:$CLIENT_SECRET --silent $OAUTH_TOKEN_URL --data "grant_type=password&username=$USERNAME&password=$PASSWORD&scope=$SCOPE"`

	RESPONSE_JSON=`echo "$RESPONSE" | python -mjson.tool`
	ACCESS_TOKEN=`echo "$RESPONSE" | python -mjson.tool | grep -oP 'access_token"\s*:\s*"\K(.*)"' | sed 's/"//'`

	echo ""
	echo "> RESPONSE:"
	echo "$RESPONSE_JSON"
	echo ""

	echo "------------"
	echo ""
	echo "> Access Token:"
	echo "$ACCESS_TOKEN"
fi
