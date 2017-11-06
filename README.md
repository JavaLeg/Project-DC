# Project-DC

**Setting up**

1. Clone the github repo  
2. Initialse an eclipse workspace (default is fine, as long as the workspace is NOT the github repo)  
3. File -> Import -> Gradle (Existing Gradle Project)  
4. Set the project root directory to the github repo, and take it one directory further. So ~/.\*/Project-DC/LibGDXGradle<p>
  
**Run the project**

  1. Right click DC-desktop -> "Run as" -> select desktop launcher  
  2. If you get a compile error https://github.com/JavaLeg/Project-DC/issues/2  
  
 
## Overview

  A simple, lightweight game engine/editor in libGDX.  
  The overall feel of the created worlds was to mirror very early retro (dungeon styles in particular) games, from an othographic point of view similar to Zelda or Pokemon.  
  
  ### Features
  
  **Navigation**

  ![Navigation GUI](docs/main.png?raw=true)
  ![Options menu](docs/options.png?raw=true)
  ![Library menu](docs/library.png?raw=true)
  
  - Main Menu for navigation  
  - Options menu for adjusting resolution  
  - Library menu for displaying/loading/running stored maps  
  
  **Editor**

  [Editor GUI](docs/editor.png?raw=true)
  
  - GUI interface consisting of an editor tab, toolbar, and preview grid  
  - Object placement logic for playable maps  
  - Auto-save when exiting  
  - Direct edit/save of *DynamicObjects*  
  - Direct edit/save of maps  

  **Game**

  - Key input for player movement
  - Key input for attacking
  - ESC quick exit
  - TODO (make a nice map)



## CONTROLS

**Editor Controls**

	```
	Left-Click : General selection  
	Right-Click + (HOLD) : Drags the map in editor mode  
	ESC : Exit back to main-menu  
	```

**Game Controls**

	```
	W : Move up
	A : Move left
	S : Mode down
	D : Move right

	Arrow-up : Attack up
	Arrow-left : Attack left
	Arrow-down : Attack down
	Arrow-right : Attack right

	ESC : Quick exit
	```

  
