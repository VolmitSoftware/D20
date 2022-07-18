package art.arcane.d20.roll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SimpleRoll implements Roll{
    private final Die die;
    @Builder.Default
    private final int rollCount = 1;
    @Builder.Default
    private final int modifier = 0;

    @Override
    public int roll() {
        return die.roll(rollCount) + modifier;
    }

    @Override
    public String encode() {
        return (rollCount == 1 ? "" : rollCount) + "d" + die.getSides() + (modifier == 0 ? "" : modifier > 0 ? "+" + modifier : modifier + "");
    }
}
