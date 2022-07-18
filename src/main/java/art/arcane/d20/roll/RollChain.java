package art.arcane.d20.roll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class RollChain implements Roll {
    @Singular
    private final List<Roll> rolls;
    @Builder.Default
    private final int modifier = 0;

    @Override
    public int roll() {
        int c = 0;

        for(Roll i : rolls)
        {
            c+= i.roll();
        }

        return c;
    }

    @Override
    public int getRollCount() {
        return 1;
    }

    @Override
    public String encode() {
        StringBuilder s = new StringBuilder();

        for(Roll i : rolls)
        {
            s.append("+").append(i.encode());
        }

        return s.substring(3);
    }
}
