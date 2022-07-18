package art.arcane.d20.roll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NaturalResult {
    @Builder.Default
    private final int sides = 20;
    @Builder.Default
    private final int result = 1;

    public boolean isNaturalMax() {
        return result == sides;
    }

    public boolean isNaturalMin() {
        return result == 1;
    }
}
