import cv2
import numpy as np

#created using: https://medium.com/coinmonks/a-box-detection-algorithm-for-any-image-containing-boxes-756c15d7ed26

#function from: https://www.pyimagesearch.com/2015/04/20/sorting-contours-using-python-and-opencv/
def sort_contours(cnts, method="left-to-right"):
	# initialize the reverse flag and sort index
	reverse = False
	i = 0
	# handle if we need to sort in reverse
	if method == "right-to-left" or method == "bottom-to-top":
		reverse = True
	# handle if we are sorting against the y-coordinate rather than
	# the x-coordinate of the bounding box
	if method == "top-to-bottom" or method == "bottom-to-top":
		i = 1
	# construct the list of bounding boxes and sort them from top to
	# bottom
	boundingBoxes = [cv2.boundingRect(c) for c in cnts]
	(cnts, boundingBoxes) = zip(*sorted(zip(cnts, boundingBoxes),
		key=lambda b:b[1][i], reverse=reverse))
	# return the list of sorted contours and bounding boxes
	return (cnts, boundingBoxes)

#img = cv2.imread("/Users/max/Documents/309/hv_3/Experiments/MaxW/images/test.jpeg", 0)

img_pre = cv2.imread("/Users/max/Documents/309/hv_3/Experiments/MaxW/images/edge3.png", 0)

scale_percent = 400
width = int(img_pre.shape[1] * scale_percent / 100)
height = int(img_pre.shape[0] * scale_percent / 100)
dim = (width, height)
img = cv2.resize(img_pre, dim, interpolation=cv2.INTER_AREA)

(thresh, img_bin) = cv2.threshold(img, 128, 255, cv2.THRESH_BINARY|cv2.THRESH_OTSU)

img_bin = 255-img_bin
cv2.imwrite("/Users/max/Documents/309/hv_3/Experiments/MaxW/images/test_bin.jpeg", img_bin)

kernel_length = np.array(img).shape[1]//200

vertical_kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (1, kernel_length))

hori_kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (kernel_length, 1))

kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))

img_temp1 = cv2.erode(img_bin, vertical_kernel, iterations=6)
vertical_lines_img = cv2.dilate(img_temp1, vertical_kernel, iterations=6)
cv2.imwrite("/Users/max/Documents/309/hv_3/Experiments/MaxW/images/vertical_lines.jpg", vertical_lines_img)

img_temp2 = cv2.erode(img_bin, hori_kernel, iterations=6)
horizontal_lines_img = cv2.dilate(img_temp2, hori_kernel, iterations=6)
cv2.imwrite("/Users/max/Documents/309/hv_3/Experiments/MaxW/images/horizontal_lines.jpg", horizontal_lines_img)

alpha = 0.5
beta = 1.0 - alpha

img_final_bin = cv2.addWeighted(vertical_lines_img, alpha, horizontal_lines_img, beta, 0.0)
img_final_bin = cv2.erode(~img_final_bin, kernel, iterations=2)
(thresh, img_final_bin) = cv2.threshold(img_final_bin, 128, 255, cv2.THRESH_BINARY | cv2.THRESH_OTSU)
cv2.imwrite("/Users/max/Documents/309/hv_3/Experiments/MaxW/images/img_final_bin.jpg", img_final_bin)

contours, hierarchy = cv2.findContours(img_final_bin, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

(contours, boundingBoxes) = sort_contours(contours, method="top-to-bottom")

idx = 0
for c in contours:
    x, y, w, h = cv2.boundingRect(c)
    if (w > 1000 and h > 1000):
        idx += 1
        new_img = img[y:y+h, x:x+w]
        cv2.imwrite("/Users/max/Documents/309/hv_3/Experiments/MaxW/images/contours/"+str(idx) + '.png', new_img)