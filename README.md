# Selyux Style Menu

Fabric client mod for Minecraft 1.21.11.

## What it does
- Press **M** to open the menu.
- Uses a two-column layout similar to the reference screenshot.
- **Friends is replaced by Shop**.
- Shop runs `/shop`.
- Other buttons run the corresponding server commands.

## Important
The exact server command names can differ. Edit `SelyuxMenuScreen.java` if your server uses different commands.

Example:
`addButton(leftX, topY + 2 * (BUTTON_H + GAP), "Shop", "/shop");`

## Build
Use Java 21 and run:

`./gradlew build`

The resulting jar will be in `build/libs/`.


Teleport now opens TPA and TPAHERE choices. Selecting one pre-fills the chat command so you can enter the target player's name.
