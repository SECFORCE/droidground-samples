# DroidGround Samples

A simple collection of dummy applications to showcase [DroidGround](https://github.com/SECFORCE/droidground).

The following applications are currently available:

| Application     | Description                                                         |
|-----------------|---------------------------------------------------------------------|
| Hidden Activity | A simple application with an hidden activity that contains the flag |

Every sample app has the following:
- a placeholder flag set to `DROIDGROUND_FLAG_PLACEHOLDER`
- a `config.json` file in the root of the app directory that specifies:
    1. The file(s) that contain the placeholder flag
    2. The actual flag

Using this info the *GitHub action* can build **two versions** of each app (one with the placeholder flag and one with the actual flag).
This simulates what should happen in a real CTF event, where the player are given the placeholder version while the real one is installed on the device accessible through *DroidGround*.

If you want to build them on your own you can use the `build.sh` script provided here in the root of the repo.