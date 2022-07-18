package art.arcane.d20.roll;

import java.util.Random;

public interface Die {
    int getSides();

    default int roll()
    {
        return Random.r().i(1, getSides());
    }

    default int roll(int multiple)
    {
        if(multiple == 1)
        {
            return roll();
        }

        if(multiple <= 0)
        {
            return 0;
        }

        int t = 0;

        for(int i = 0; i < multiple; i++)
        {
            t+= roll();
        }

        return t;
    }
}
