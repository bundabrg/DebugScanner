![Debug Scanner](img/title.png)

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

    !!! example
        /gamerule randomTickSpeed 0
   
2. Import the world into spigot by copying the save file and renaming to world. You will also need to rename level_old.dat to level.dat
or copy a level.dat from a non debug world (note if you do this you'll need to set the gamerule again on first load).

3. Start up the server and join the world

### Auto Scanner

The auto scanner will teleport the player to every block in the debug world in sequence, showing the block state and block number
in an actionbar message

To start:

    !!! example
        /debugscanner auto start [-start {block number}] [-interval {ticks}] [-pitch {pitch}] [-yaw {yaw}] 
            [-direction {north|south|east|west|all}] [player]

To stop:

    !!! example
        /debugscanner auto stop [player]

## Detector Mode

Detector mode will show what debug block a player is looking at as well as the block number in an action bar message.

To start:

    !!! example
        /debugscanner detect start
        
To stop:

    !!! example
        /debugscanner detect stop
        
## Watcher Mode

Watcher mode is a workaround for Bedrock not having proper spectator mode. It will make a player view everything another
player is doing and make both players invisible to each other.  Used with Scan mode or Detect mode will allow simultaneous
viewing of blocks by multiple players running different editions of minecraft.

To start:

    !!! example
        /debugscanner watch {player to watch} [watching player]
        
To stop, press `sneak`.
