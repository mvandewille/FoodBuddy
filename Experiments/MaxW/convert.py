import whatimage
import pyheif
from PIL import Image


def decodeImage(bytesIo):
    fmt = whatimage.identify_image(bytesIo)
    if fmt in ['heic', 'avif']:
            i = pyheif.read_heif(bytesIo)

            # Extract metadata etc
            for metadata in i.metadata or []:
                if metadata['type']=='Exif':
                    # do whatever
                    print("s")

            # Convert to other file format like jpeg
            s = io.BytesIO()
            pi = Image.frombytes(
                mode=i.mode, size=i.size, data=i.data)

            pi.save(s, format="jpeg")