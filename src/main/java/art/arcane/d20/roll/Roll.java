package art.arcane.d20.roll;

public interface Roll {
    int roll();

    int getRollCount();

    int getModifier();

    String encode();

    default Roll decode(String s)
    {
        return null;
    }

    default RollChain.RollChainBuilder and(Roll r)
    {
        return RollChain.builder().roll(this).roll(r);
    }

    default RollChain.RollChainBuilder and(SimpleRoll.SimpleRollBuilder r)
    {
        return RollChain.builder().roll(this).roll(r.build());
    }

    static SimpleRoll.SimpleRollBuilder d20()
    {
        return SimpleRoll.builder().die(() -> 20);
    }

    static SimpleRoll.SimpleRollBuilder d10()
    {
        return SimpleRoll.builder().die(() -> 10);
    }

    static SimpleRoll.SimpleRollBuilder d4()
    {
        return SimpleRoll.builder().die(() -> 4);
    }

    static SimpleRoll.SimpleRollBuilder d6()
    {
        return SimpleRoll.builder().die(() -> 6);
    }

    static SimpleRoll.SimpleRollBuilder d100()
    {
        return SimpleRoll.builder().die(() -> 100);
    }
}
