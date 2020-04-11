    idx += 1
        new_img = img[y:y+h, x:x+w]
        cv2.imwrite("/Users/max/Documents/309/hv_3/Experiments/MaxW/images/contours/"+str(idx) + '.png', new_img)