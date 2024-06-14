# Notes for Kevin

(I'm the software mentor for 16750/20403 and have picked up ownership of TechnLib from Alex Stedman,
the original author, as he's doing silly things like "attending college")

## Hardware details to keep in mind

The analog sticks are vertically inverted: When you push the stick up/away, the Y value decreases.

The shoulder triggers' range is from 0 to 1, not -1 to 1.

I'm not currently sure what orientation the SDK 8.1+ IMU calls 0 degrees.

## Simulation

This has been in my head since my first year (2019, SkyStone)

There ought to be a simulator for this stuff. It would let programmers work more independently to
validate basic stuff. I've never built a simulator, but I'm a semi competent developer, so (famous
last words) how hard can it be? ftcsim and vrs both lack some amount of flexibility, why doing a lot
more than what I really need/want for a team who's building a real bot.

The core things that would need simulated:

- Motors (with encoders)
- Servos (in both CR and Servo mode)
- Sensors (pick the order to get them going)
  - IMU
  - Bump
  - Color
  - 2M range
  - Range

Probably not worth simulating:

- Vision. I could support connecting a webcam so the students could use an actual webcam, but I'm
  not about to deal with AprilTags and TFOD. ML sucks. It doesn't work at level level of determinism
  that makes me thing I could do anything useful with it. It's quantum physics, if the probability
  of weird stuff happening is 10^-2 instead of 10^-20000000. Thanks, but no thanks.
- Controllers. Just wire them up from the computer

Where do things get messy? I'd need some mechanism to translate rotational motion. I don't have the
interest to do a full physics simulator, so I could potentially make this pretty rudimentary.

### What's next?

Get through this season, then maybe start horsing around with this? Maybe learn Kotlin, since it
looks _much_ less verbose than Java (Seriously, Java, you've had almost 30 years. Why do you still
hate developers so much?)
