package art.arcane.d20.roll;

public interface Rollable {
    RollResult roll();

    String encode();

    int getMaxResult();
}
