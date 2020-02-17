import requests
import json
URL = 'http://coms-309-hv-3.cs.iastate.edu:8080/user/auth'
r = requests.get(URL, params={"email": "tdj1@iastate.edu", "password":"password"})
print(r.status_code)
print(r.json())