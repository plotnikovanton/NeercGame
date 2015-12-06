#!/bin/python3

import cgi
import hashlib
import re
import psycopg2 as pg
from http.server import HTTPServer, BaseHTTPRequestHandler


conn = pg.connect(dbname="neerc", user="neerc", password="5584245")
cur = conn.cursor()


def verify(postvars):
    if (len(postvars) < 4 or
            not set(postvars.keys()) <=
            set({b"score", b"time", b"name", b"md5"})):
        return False

    score, = postvars[b"score"]
    time, = postvars[b"time"]
    name, = postvars[b"name"]
    md5, = postvars[b"md5"]

    if not (re.match(r"\d+\,\d+", score.decode()) and
            re.match(r"\d+", time.decode()) and
            len(name) <= 20):
        return False

    salted_data = b"kaliweproductioninaction" + score + time + name
    checksum = hashlib.md5(salted_data).hexdigest()

    return(checksum == md5.decode())


def save(score, time, name):
    cur.execute(
        "INSERT INTO ScoreBoard (name, score, time) VALUES (%s, %s, %s)",
        (name, score, time))
    conn.commit()


class MyHandler(BaseHTTPRequestHandler):
    def do_POST(self):
        ctype, pdict = cgi.parse_header(self.headers['content-type'])
        if ctype == 'multipart/form-data':
            postvars = cgi.parse_multipart(self.rfile, pdict)
        elif ctype == 'application/x-www-form-urlencoded':
            length = int(self.headers['content-length'])
            postvars = cgi.parse_qs(self.rfile.read(length),
                                    keep_blank_values=1)
        else:
            postvars = {}

        if verify(postvars):
            score = float(postvars[b"score"][0].decode().replace(',', '.'))
            time = postvars[b"time"][0].decode()
            name = postvars[b"name"][0].decode()
            save(score, time, name)


if __name__ == "__main__":
    server_address = ("", 1488)
    httpd = HTTPServer(server_address, MyHandler)
    httpd.serve_forever()
