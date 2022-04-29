Run in android studio 
Program developed and tested with Pixel 3 XL API 30 Android Emulator
JSON files are in assets folder

To add new animations:
    add xml files with animation content to res/anim folder
    add the animation name to json file
    add animation name to MainActivity.getEnterAnim and MainActivity.getExitAnim and return the 
    R.anim.<animation_file_name>

To add new layouts:
    add xml files with layout content to res/layout folder
    add the layout name to json file
    add layout name to MainActivity.getLayoutID and return the
    R.layout.<layout_file_name>
    
If new layouts include buttons add buttonID to MainActivity.getButtonID
and return the R.id.<buttonID> correlated with the layout that has that button

