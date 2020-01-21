try:
    from PIL import Image
except ImportError:
    import image
import pytesseract

#pytesseract.pytesseract.tesseract_cmd = r'/usr/local/Cellar/tesseract/4.1.1/bin/tesseract'

print(pytesseract.image_to_string(Image.open('/Users/max/Dropbox/College/Year 3/COM S 309/hv_3/Experiments/Max W/test_label.png')))
print('-----------------------------------------------')
print(pytesseract.image_to_string(Image.open('/Users/max/Dropbox/College/Year 3/COM S 309/hv_3/Experiments/Max W/test_label_segment.png')))