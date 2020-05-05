import cv2
import numpy as np
import os

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

def processImg(name, num, v, h):
    result = []
    path = "/Users/max/Downloads/" + name
    save_path = "/Users/max/Documents/309/images/output/" + str(num) + "_" 
    img_pre = cv2.imread(path, 0)

    scale_percent = 200
    width = int(img_pre.shape[1] * scale_percent / 100)
    height = int(img_pre.shape[0] * scale_percent / 100)
    dim = (width, height)
    img = cv2.resize(img_pre, dim, interpolation=cv2.INTER_AREA)

    (thresh, img_bin) = cv2.threshold(img, 128, 255, cv2.THRESH_BINARY|cv2.THRESH_OTSU)

    img_bin = 255-img_bin
    cv2.imwrite(save_path + "test_bin.jpeg", img_bin)

    kernel_length = np.array(img).shape[1]//250

    vertical_kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (1, kernel_length))

    hori_kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (kernel_length, 1))

    kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))

    img_temp1 = cv2.erode(img_bin, vertical_kernel, iterations=v)
    vertical_lines_img = cv2.dilate(img_temp1, vertical_kernel, iterations=v)
    cv2.imwrite(save_path + "vertical_lines.jpg", vertical_lines_img)

    img_temp2 = cv2.erode(img_bin, hori_kernel, iterations=h)
    horizontal_lines_img = cv2.dilate(img_temp2, hori_kernel, iterations=h)
    cv2.imwrite(save_path + "horizontal_lines.jpg", horizontal_lines_img)

    alpha = 0.5
    beta = 1.0 - alpha

    img_final_bin = cv2.addWeighted(vertical_lines_img, alpha, horizontal_lines_img, beta, 0.0)
    img_final_bin = cv2.erode(~img_final_bin, kernel, iterations=2)
    (thresh, img_final_bin) = cv2.threshold(img_final_bin, 128, 255, cv2.THRESH_BINARY | cv2.THRESH_OTSU)
    cv2.imwrite(save_path + "img_final_bin.jpg", img_final_bin)

    contours, hierarchy = cv2.findContours(img_final_bin, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

    (contours, boundingBoxes) = sort_contours(contours, method="top-to-bottom")

    idx = 0
    for c in contours:
        x, y, w, h = cv2.boundingRect(c)
        if (w < img.shape[1] * 0.8 or h < img.shape[0] * 0.8) and w > img.shape[1] * 0.2 and h > img.shape[0] * 0.25:
            idx += 1
            new_img = img[y:y+h, x:x+w]
            result.append(new_img)
            cv2.imwrite("/Users/max/Documents/309/images/contours/" + name.split(".")[0] + "_" +str(idx) + ".png", new_img)
    return img_pre, result

#img = cv2.imread("/Users/max/Documents/309/hv_3/Experiments/MaxW/images/test.jpeg", 0)

path_arr = ["edge_detect_easy.png", "edge_detect_hard_1.jpg", "edge_detect_hard_2.jpg", "edge_detect_medium.jpg", "edge1.jpg", "edge2.jpg", "edge3.png", "weird.jpg", "can.jpg", "tricky.jpg", "chip.jpg", "chip2.jpg", "chip3.jpg"]
#path_arr = ["chip2.jpg", "chip3.jpg"]
i = 0
for filename in os.listdir("/Users/max/Downloads"):
    if filename.split(".")[1] != "jpg":
        continue
    name = filename
    print(name)
    img_pre, result = processImg(name, i, 6, 6)
    if len(result) == 0:
        img_pre, result = processImg(name, i, 50, 50)
    cv2.imwrite("/Users/max/Documents/309/images/result/" + str(i) + "_" + "o" + ".png", img_pre)
    
    j = 0
    for img in result:
        cv2.imwrite("/Users/max/Documents/309/images/result/" + str(i) + "_" + "r" + "_" + str(j) + ".png", img)
        j += 1
    # if len(result) == 1:
    #     cv2.imwrite("/Users/max/Documents/309/images/result/" + str(i) + "_" + "r" + ".png", result[0])
    # if len(result) > 1:
    #     cv2.imwrite("/Users/max/Documents/309/images/result/" + str(i) + "_" + "r" + ".png", result[1])
    i += 1
