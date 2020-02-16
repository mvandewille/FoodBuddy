import requests
import json
URL = 'http://coms-309-hv-3.cs.iastate.edu:8080/user/find/all'
r = requests.get(URL)
print(r.status_code)
print(r.text())