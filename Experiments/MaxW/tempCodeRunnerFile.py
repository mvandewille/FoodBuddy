     # Convert to other file format like jpeg
                s = io.BytesIO()
                pi = Image.frombytes(
                    mode=i.mode, size=i.size, data=i.data)

                pi.save(s, format="jpeg")