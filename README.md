![Logo](https://bundabrg.github.io/DebugScanner/img/title.png)

[![MIT license](https://img.shields.io/badge/License-MIT-blue.svg)](https://lbesson.mit-license.org/)
[![GitHub release](https://img.shields.io/github/release/Bundabrg/DebugScanner)](https://GitHub.com/Bundabrg/DebugScanner/releases/)
[![GitHub commits](https://img.shields.io/github/commits-since/Bundabrg/DebugScanner/latest)](https://GitHub.com/Bundabrg/DebugScanner/commit/)
[![Github all releases](https://img.shields.io/github/downloads/Bundabrg/DebugScanner/total.svg)](https://GitHub.com/Bundabrg/DebugScanner/releases/)
![HitCount](http://hits.dwyl.com/bundabrg/portalnetwork.svg)

![Workflow](https://github.com/bundabrg/DebugScanner/workflows/build/badge.svg)
[![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://GitHub.com/Bundabrg/DebugScanner/graphs/commit-activity)
[![GitHub contributors](https://img.shields.io/github/contributors/Bundabrg/DebugScanner)](https://GitHub.com/Bundabrg/DebugScanner/graphs/contributors/)
[![GitHub issues](https://img.shields.io/github/issues/Bundabrg/DebugScanner)](https://GitHub.com/Bundabrg/DebugScanner/issues/)
[![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/Bundabrg/DebugScanner.svg)](http://isitmaintained.com/project/Bundabrg/DebugScanner "Average time to resolve an issue")
[![GitHub pull-requests](https://img.shields.io/github/issues-pr/Bundabrg/DebugScanner)](https://GitHub.com/Bundabrg/DebugScanner/pull/)
 

---

[**Documentation**](https://bundabrg.github.io/DebugScanner/)

[**Source Code**](https://github.com/bundabrg/DebugScanner/)

---

DebugScanner is really only of use to those involved with the GeyserMC project.  It is to be used with a Debug world and
provides tools to allow testing block states between various editions of Minecraft.

Auto scan will will put the player into creative mode and teleport them at a configured interface to each block in succession with the
block number shown in the action bar. 

Detector Mode will show the block a player is looking at (from the debug blocks) which allows a flyaround for any issues.

It will start with the block at (1 70 1) and will continue till it cannot find any more blocks. The first block is
always assumed to be air.

[Video](https://www.youtube.com/watch?v=G1b7M5fv0Dk)

## How to Use

1. Generate a Debug world by creating a single player world and holding shift when selecting type. One of the options will
be Debug.

2. Issue the following command:

    ```
    /gamerule randomTickSpeed 0
    ```
   
2. Import the world into spigot by copying the save file and renaming to world. You will also need to rename level_old.dat to level.dat
or copy a level.dat from a non debug world (note if you do this you'll need to set the gamerule again on first load).

3. Start up the server and join the world

4. To enable Auto Scan Use the following command to start:

    ``` 
    /debugscanner auto start [-start {block number}] [-interval {ticks}] [-pitch {pitch}] [-yaw {yaw}] [player]
    ```

5. To stop the auto scanner early use the following command:

    ```
    /debugscanner auto stop [player]
    ```


## Detector Mode

Detector mode will show what debug block a player is looking at.

To start:

    ```
    /debugscanner detect start
    ```
        
To stop:

    ```
    /debugscanner detect stop
    ```
