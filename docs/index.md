# TechnoLib documentation

- [TechnoLib](https://technototes.github.io/TechnoLib/TechnoLib)
- [Path](https://technototes.github.io/TechnoLib/Path)
- [Vision](https://technototes.github.io/TechnoLib/Vision)
- [Source Code](https://github.com/technototes/TechnoLib/)

> TechnoLib is an FTC Library for everyone:

- WPILib inspired command structure
- Tons of simple implementations to provide abstractions, and teach you the basics
- EasyOpenCV integration for vision
- RoadRunner integration for path-following
- Annotation based Telemetry

**The goal of TechnoLib is not only to provide versatile resources that assist in software
development and strengthen code structure, but to also abstract out redundancy.**

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
  `implementation 'com.github.technototes:TechnoLib:1.1.2'`  
  **(replace 1.1.2 with the latest release)**
- Build the code and you are good to go

## Ease of Use

But if this library is so powerful, won't it be hard to use? Nope, in fact its easier than without
the library

- Complex subsystems have already been written for you
- Plenty of simple things to make your life easier
- Test implementation so you can see how to make this work

## Documentation

There are not currently any good 'stand alone' examples. The best place
for you to get an idea of how this stuff works is in our robot code. It's all public on github:
idea of how this stuff works is in our robot code. It's all public on github:

- Freight Frenzy (2021-2022)' s
  [16750 robot source code](https://github.com/technototes/FreightFrenzy2021/tree/master/OspreyCode/src/main/java/org/firstinspires/ftc/teamcode)
  ( this is a more advanced usage, written by the library's primary autho)
- Freight Frenzy (2021-2022)' s
  [20403 robot source code](https://github.com/technototes/FreightFrenzy2021/tree/master/SeagullCode/src/main/java/org/firstinspires/ftc/teamcode)
  ( This is a simpler usage, written by students who mostly learned by example)
- And here's a PowerPlay (2022-2023) based example
  ["learning" bot](https://github.com/technototes/PowerPlay2022/tree/main/ForTeaching/src/main/java/org/firstinspires/ftc/forteaching/TechnoBot)
  ( This is a very basic example of a few concepts, include vision)

Good luck!
