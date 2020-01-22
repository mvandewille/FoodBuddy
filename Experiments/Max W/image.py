try:
    from PIL import Image
except ImportError:
    import image
import pytesseract, datetime

#pytesseract.pytesseract.tesseract_cmd = r'/usr/local/Cellar/tesseract/4.1.1/bin/tesseract'
tT = 0
for num in range(100):
    startTime = datetime.datetime.now()
    pytesseract.image_to_string(Image.open('test_label.png'))
    endTime = datetime.datetime.now()
    tT += (endTime-startTime).total_seconds()

print('Single scan average time:', tT / 100)
tT = 0

for num in range(100):
    startTime = datetime.datetime.now()
    label = Image.open('test_label.png')
    width, height = label.size

    left = 0
    right = width
    top = 0
    bottom = height / 4
    quarter = bottom
    #crops = []

    #now we crop the image into 4 sections
    for num in range(4):
        pytesseract.image_to_string(label.crop((left, top, right, bottom)))
        top = bottom
        bottom = bottom + quarter
    endTime = datetime.datetime.now()
    tT += (endTime-startTime).total_seconds()

print('4 segment scan average time:', tT / 100)
#print(pytesseract.image_to_string(Image.open('/Users/max/Dropbox/College/Year 3/COM S 309/hv_3/Experiments/Max W/test_label_segment.png')))

#pdf = pytesseract.image_to_pdf_or_hocr('/Users/max/Dropbox/College/Year 3/COM S 309/hv_3/Experiments/Max W/test_label.png', extension='pdf')
#with open('/Users/max/Dropbox/College/Year 3/COM S 309/hv_3/Experiments/Max W/test.pdf', 'w+b') as f:
#    f.write(pdf) # pdf type is bytes by default