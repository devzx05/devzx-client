# devzx client — Minecraft Java 1.21.1

A client-side Fabric starter project with a dark/neon ClickGUI, custom HUD elements, keystrokes, CPS counter, armor display, and a neon crosshair overlay.

## Easiest build method (no Java or coding setup on your Mac)

This project includes a GitHub Actions workflow that builds the mod remotely using Java 21 and Gradle 8.10.2. Your Mac does not need to run Gradle to produce the JAR.

1. Download and extract `devzx-client-source.zip`.
2. Open https://github.com and sign in or create a free account.
3. Create a new repository named `devzx-client`. A public repository is easiest for following GitHub's available free usage.
4. In the repository, choose **Add file → Upload files** and upload the *contents* of the extracted `devzx-client` folder, including the hidden `.github` folder. If the website doesn't include hidden files, use GitHub's web editor to create `.github/workflows/build.yml` and paste the workflow from this project.
5. Commit the files to the `main` branch.
6. Open the repository's **Actions** tab. Select **Build devzx client** and press **Run workflow** if it has not started automatically.
7. Wait for the run to finish. A green check means the Gradle build step succeeded. Open that run and download the artifact named `devzx-client-minecraft-1.21.1`.
8. Extract the artifact ZIP. It should contain `devzx-client-1.0.0.jar`.

A successful compilation is not the same as a successful in-game test. This project has not yet been independently verified in Minecraft; after building, test it in a separate MultiMC instance first.

## Install in MultiMC

1. Create or back up a Minecraft **1.21.1 + Fabric** instance.
2. Open that instance's folder from MultiMC.
3. Put `devzx-client-1.0.0.jar` in its `mods` folder. Make sure Fabric API for Minecraft 1.21.1 is also installed if the mod metadata requests it.
4. Launch and check the game log if it crashes. Keep the original instance untouched until testing succeeds.

## Controls

- Right Shift: open ClickGUI.
- Right Control: open the devzx client menu.

## Implemented in source

- Branding and FPS display.
- ClickGUI toggles for HUD elements.
- Keystrokes display.
- Left-click CPS counter.
- Armor slots display.
- Neon crosshair overlay.
- Simple custom menu screen.

## Not yet fully implemented

- The FPS helper toggle is only a UI toggle; it does not optimize game performance.
- Animation and cosmetics toggles are placeholders, not full player animations or cosmetic models.
- The custom menu is opened with Right Control; it does not replace the default Minecraft title screen.
- No visual/in-game test has been completed yet.

## Compatibility warning for the old Mac

Minecraft 1.21.1 targets Java 21. You have reported that Minecraft 1.21.1 already launches in your MultiMC setup using Java 25, so try the mod in that same instance after remote compilation. Java 25 compatibility with this mod and all dependencies still needs to be tested. High Sierra and a 2011 Intel Mac are outside the supported range for many current Java distributions and graphics drivers; if the mod builds but crashes at launch, share the full MultiMC log.

## Build locally (optional)

On a computer with Java 21 and internet access, use Gradle 8.10.2 and run:

```bash
gradle --no-daemon clean build
```

The distributable JAR should be in `build/libs/`; do not use the `-sources.jar` file.
