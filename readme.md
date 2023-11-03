## TechnoLib

TechnoLib is a FTC Library for everyone:

- WPILib inspired command structure
- Tons of simple implementations to provide abstractions, and teach you the basics
- EasyOpenCV integration for vision
- RoadRunner integration for path-following
- Annotation based Telemetry
- And much much more

### The goal of this library is not only to provide versatile resources that assist in software development and strengthen code structure, but to also abstract out redundancy.

## Installation

But if this library is so good, it must be hard to install right? wrong:

- Add this right before the dependencies{} block in TeamCode/build.gradle:
  ```
     repositories{
      ` maven { url = 'https://maven.brott.dev/' }
        maven { url 'https://jitpack.io' }
     }
  ```
- And add this to the dependencies block in TeamCode/build.gradle:
  `implementation 'com.github.technototes:TechnoLib:1.3.0'` **(replace 1.3.0 with the latest
  release)**
- Build the code and you are good to go

### Both versions 1.2.0 and 1.2.0 have breaking changes.

See below for details

## Ease of Use

But if this library is so powerful, won't it be hard to use? Nope, in fact its easier than without
the library

- Complex subsystems have already been written for you
- Plenty of simple things to make your life easier
- Test implementation so you can see how to make this work

## Documentation

Documentation can be [found here](https://technototes.github.io/)

There are not currently any good 'stand alone' examples. The best place for you to get an idea of
how this stuff works is in our robot code. It's all public on github:

- Freight Frenzy (2021-2022)'s
  [16750 robot source code](https://github.com/technototes/FreightFrenzy2021/tree/master/OspreyCode/src/main/java/org/firstinspires/ftc/teamcode)
  (this is a more advanced usage, written by the library's primary author)
- Freight Frenzy (2021-2022)'s
  [20403 robot source code](https://github.com/technototes/FreightFrenzy2021/tree/master/SeagullCode/src/main/java/org/firstinspires/ftc/teamcode)
  (This is a simpler usage, written by students who mostly learned by example)
- And here's a PowerPlay (2022-2023) based example
  ["learning" bot](https://github.com/technototes/PowerPlay2022/tree/main/ForTeaching/src/main/java/org/firstinspires/ftc/forteaching/TechnoBot)
  (This is a very basic example of a few concepts, include vision)

## Breaking changes

## Version 1.3.0:

- The two conflicting `MecanumDrivebaseSubsystem` classes, on in `RobotLibrary`, and one in `Path`,
  are now differentiated: `SimpleMecanumDrivebaseSubsystem` and `PathingMecanumDrivebaseSubsystem`.
  Check which module you were importing to and change your code to use the appropriate drivebase
  subsystem.

- The button names are now prefixed with `xbox_` or `ps_` depending on if the button is on an XBox
  controller, or a PlayStation controller. This is particularly helpful when you're trying to use
  the "X" buttons. On the XBox controller, it was called `x` and now it's called `xbox_x`, while the
  button with an "X" on a PlayStation controller was called `cross` and is now called `ps_cross`.

## Version 1.2.0:

For SDK 8.1 and later, there's a new IMU class that handles a bunch of stuff that was handled by the
BNO055 IMU internally. This resulted in changes in TechnoLib's IMU class. You will not _likely_ need
to use the remapAxes function (which has been renamed) and the constructor for the IMU now requires
that you specify the orientation of your control hub. The SDK also changed the axes' orientation on
the hub. You'll be happier by just switching your mental model to the new IMU class (but it's weird
that FIRST changed it :/ )

# Notes from Kevin Frei

Hi, the original author of this libary (Alex Stedman) graduated and went off to college. So now I'm
maintaining it.

I'm an FTC mentor, not an FRC mentor. My goals are to enable capable programmers (often times coming
from Python) to be able to create a functional, _reliable_ robot quickly and easily. Java, and
particularly some Java conventions however, are not always the most conducive language to these
goals. I've made a few minor edits/improvements in the 1.3.0 release, but I'm also prototyping a new
2.0 release during the 2023/2024 season (our students are still using 1.3.0). The point of me
working on the prototype during build/competition season is primarily because I'm watching what
students struggle with, what I'm stuck explaining over &amp; over, etc... and am trying to adjust
the library to be more approachable and easier to interact with.

## Small Ideas

Initially, I found the hardware wrapper types to be mostly just a waste of effort. Now, however, I'm
considering building out MUCH more capability in them. The 2 primary things I'd like to add are:

1. Resilience to disconnected hardware
2. Easy, code-free logging opt-in/opt-out

For item 1, we have a typical setup where we have flags in the "robot" object where we can
'disconnect' a subsystem, thus allowing the bot to continue to function even when specific hardware
isn't connected. This could be done without too much effort, and could potentially also be a nice
stepping stone to some level of hardware simulation.

For item 2, for most of the hardware types, it's often useful to log data from those types (current
power/velocity, current tick values, etc...) but doing so is somewhat clunky. It would be nice if
you could set a flag in the dashboard, or perhaps just add a top-level to the constructor or a
subsystem that would enable/disable logging with little-to-no code changes.

## Big Ideas

Full simulation of a robot would be _tremendously_ helpful. Initially just being able to simulate
all the hardware would be useful. Being able to configure motors, servos, and sensors in a 3
dimensional model, with connections to parts would be MUCH more work (but also MUCH cooler)
