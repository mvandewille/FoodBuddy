try:
    from PIL import Image
except ImportError:
    import image

import base64
from io import BytesIO
import requests

#img = Image.open('C:/Users/Amish Cyborg/Dropbox/College/Year 3/COM S 309/hv_3/Experiments/MaxW/images/test_label.png')
img = Image.open('/Users/max/Dropbox/College/Year 3/COM S 309/hv_3/Experiments/MaxW/images/test_label.png')

buffered = BytesIO()

img.save(buffered, format="PNG")
img_str = base64.b64encode(buffered.getvalue()).decode('utf-8')

URL = """http://localhost:8080/user/image"""
data = {'img': img_str}
r = requests.post(URL, json= data)
print(r.text)
print(img.load()[0,0])