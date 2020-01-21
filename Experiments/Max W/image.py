try:
    from PIL import Image
except ImportError:
    import image
import pytesseract

pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'

print(pytesseract.image_to_string(Image.open('test_label.png')))
print('-----------------------------------------------')
print(pytesseract.image_to_string(Image.open('test_label_segment.png')))