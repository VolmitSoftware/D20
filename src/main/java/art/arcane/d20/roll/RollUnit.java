package art.arcane.d20.roll;

import java.util.Random;

public interface RollUnit extends Rollable {
    default int getSides() {
        return 20;
    }

    default int getModifier() {
        return 0;
    }

    default int getCount() {
        return 1;
    }

    default int getAdvantage() {
        return 0;
    }

    default int naturalRoll()
    {
        if(getAdvantage() == 1) {
            return Math.max(Random.r().i(1, getSides()), Random.r().i(1, getSides()));
        } else if(getAdvantage() == -1) {
            return Math.min(Random.r().i(1, getSides()), Random.r().i(1, getSides()));
        }

        return Random.r().i(1, getSides());
    }

    default int getMaxResult() {
        return getSides() + getModifier();
    }

    default RollResult roll() {
        int m = 0;
        int a;
        RollResult.RollResultBuilder r = RollResult.builder();

        for(int i = 0; i < getCount(); i++)
        {
            a = naturalRoll();
            r.natural(new NaturalResult(getSides(), a));
            m += a;
        }

        r.result(m);
        return r.build();
    }

    default String encode() {
        return (getAdvantage() == 1 ? "ADV " : getAdvantage() == -1 ? "DSV " : "") + (getCount() == 1 ? "" : getCount()) + "d" + getSides() + (getModifier() == 0 ? "" : getModifier() > 0 ? "+" + getModifier() : getModifier());
    }
}
