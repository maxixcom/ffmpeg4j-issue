# Example for ffmpeg4j issue

Running `example.Main.class` get error:

```shell

[mov,mp4,m4a,3gp,3g2,mj2 @ 0x103007df0] stream 0, offset 0x30: partial file
[mov,mp4,m4a,3gp,3g2,mj2 @ 0x103007df0] Could not find codec parameters for stream 0 (Video: h264 (avc1 / 0x31637661), none(tv, bt709), 1920x960, 4215 kb/s): unspecified pixel format
Consider increasing the value for the 'analyzeduration' (0) and 'probesize' (5000000) options
Assertion desc failed at libswscale/swscale_internal.h:725

Process finished with exit code 134 (interrupted by signal 6:SIGABRT)

```

Sample file is here: `tmp/sample-0.mp4`.
Empty output file is created. 