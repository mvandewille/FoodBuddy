try:
    from PIL import Image
except ImportError:
    import image
import pytesseract, datetime
#PIL, python imaging library
#pytesseract, python wrapper for google's OCR CLI
#datetime, used to time execution

def isDark(arr):
    if len(arr) != 3:
        return 0
    else:
        grey = (arr[0] + arr[1] + arr[2]) / 3 #convert to greyscale
        if grey <= 100: #I arbitrarily chose this value for now
            return 1
        else:
            return 0

def testRows(img):
    width, height = img.size
    img = img.convert('RGB')
    pixels = img.load()
    slices = []
    count = 0
    buffer = 0
    for line in range(height):
        if buffer > 0:
            buffer -= 1
            continue
        total = 0
        for w in range(width):
            total += isDark(pixels[w, line])
        if total / width > 0.8:
            count += 1
            if count > 3:
                slices.append(line)
                buffer = 10
                count = 0
    print(slices)
    return slices

def split(img, slices):
    width, height = img.size

    left = 0
    right = width
    top = 0
    bottom = 0
    quarter = bottom
    #crops = []

    startTime = datetime.datetime.now()
    #now we crop the image
    for num in slices:
        if num > 10:
            top = bottom
            bottom = num
            #img.crop((left, top, right, bottom)).show()
            print(pytesseract.image_to_string(img.crop((left, top, right, bottom))))
            print("---------------------------------------------------")
    endTime = datetime.datetime.now()
    return (endTime - startTime).total_seconds()
            



toggle = 1 #0 = run single and multi test
           #1 = run line detection
if toggle == 0:
    #accumulate excecution time for 100 trials
    tT = 0
    #loop runs single scan test 100 times and measures execution time
    for num in range(1): 
        startTime = datetime.datetime.now()
        #Here I'm using pytesseract to extract text from the image as a string
        print(pytesseract.image_to_string(Image.open('/Users/max/Dropbox/College/Year 3/COM S 309/hv_3/Experiments/Max W/test_label.png'))) 
        endTime = datetime.datetime.now()
        tT += (endTime-startTime).total_seconds()

    print('Single scan average time:', tT / 100)
    tT = 0 

    #loop runs multi scan test 100 times to test avg time
    for num in range(1): 
        startTime = datetime.datetime.now()
        label = Image.open('/Users/max/Dropbox/College/Year 3/COM S 309/hv_3/Experiments/Max W/test_label.png')
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
else:
    img = Image.open('/Users/max/Dropbox/College/Year 3/COM S 309/hv_3/Experiments/MaxW/test_label.png')
    total = 0
    for num in range(1):
        print(num)
        slices = testRows(img)
        total += split(img, slices)
    print(total / 1) #avg 4.9 
                     #avg 3.6

#short write up on findings so far
'''
For these tests I am using test_label.png in the Max W experiment folder.
First, I went through the image myself and found a total of 18 pieces of information 
that can be extracted. Then, using the pytesseract library I ran a scan on the entire label.
This scan returned 10 out of 18 pieces. 

Disclaimer: I have not parsed the string yet this number is based off me looking through the 
string that is returned. Also, there are a few spelling errors in the results. For now we'll 
ignore that as long as they're not egregious. 

So for the single scan trial the text extraction is 55.6% effective. It is important to note 
that the result does not change when run again and it takes on average 0.706 seconds 

Now let's get crafty.

The first thing that came to my mind was strategically cropping the image to get a subsection 
of the data in one scan. For this test I split the image into fourths from top to bottom and 
ran each scan separately. This test pulled out 17 out of 18 peices of info missing the Total 
Carbohydrate section of the label. This means for this specific label, this strategy is 94.4%
effective. While this is quite a significant improvement over 55.6%, the average time to scan 
was 1.54 seconds, 2.18x the time for single scan.

While this is a pretty rudementary test I have some ideas for future methods. We need to look 
into detecting the horizontal black bars on the label as these could be used as good place to 
crop. Further, doing multiple layers of scan such as one where you split the label 4 ways and 
one splitting 3 could be another way to maximize information extracted. 

However, this is only one segment of our problem. We also need to find a way to isolate the label
in the image. Maybe something to do with edge detection to find a box in the image and crop out 
excess. Second, we must parse the text we get out of tesseract. I don't have a whole lot of ideas 
for this yet but we just need to look for keywords and split lines into segments.
'''