# Gesture Demonstration

Adds a new action `GestureWindow` (default keybinding `Ctrl+M`), that creates a new window. On this window, an image follows the user's cursor along the window. Initially starting at a quarter of a side and increasing in scale as the user moves away from where they entered, and vice-versa.

Also demonstrates the loading of a more complex panel in the background. By simulating a small processing time, then changing the component that was currently being displayed.

![GestureDemo](https://github.com/user-attachments/assets/d2e1cc0f-6c6f-4ef9-a1ce-4f93ff74a8f1)

[Alternative Link to Demos (Imgur)](https://imgur.com/a/1Xj2JqM)

## Usage
The highlighting can be triggered by activating the `GestureWindow` action (default keymap is `Ctrl+M`) to open the gesture window, and pressing again to close it.

Alternatively:
This can be edited by going to `File | Settings | Keymap | Plugins -> IntellijGestureDemo`

## Installation

- Download the [latest release](https://github.com/SushantPulavarthi/IntellijGesturesDemo/releases) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Clone the repository, and run the gradle task `buildPlugin`. This will build the distribution to `build/distribution`, which you can use to install the plugin to Intellij
```
git clone https://github.com/SushantPulavarthi/IntellijGesturesDemo.git
cd IntellijGesturesDemo
./gradlew buildPlugin
```
