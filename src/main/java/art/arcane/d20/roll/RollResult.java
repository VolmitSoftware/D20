package art.arcane.d20.roll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class RollResult
{
    @Singular
    private final List<NaturalResult> naturals;
    private final int result;

    public boolean check(int atOrAbove) {
        return result >= atOrAbove;
    }

    public RollResult add(RollResult result) {
        List<NaturalResult> results = new ArrayList<>();
        results.addAll(getNaturals());
        results.addAll(result.getNaturals());
        return new RollResult(results,result.getResult() + getResult());
    }

    public RollResult max(RollResult result) {
        List<NaturalResult> results = new ArrayList<>();
        results.addAll(getNaturals());
        results.addAll(result.getNaturals());
        return new RollResult(results,Math.max(result.getResult(), getResult()));
    }

    public RollResult min(RollResult result) {
        List<NaturalResult> results = new ArrayList<>();
        results.addAll(getNaturals());
        results.addAll(result.getNaturals());
        return new RollResult(results,Math.min(result.getResult(), getResult()));
    }

    public int countNaturalMaxes() {
        int m = 0;
        for(NaturalResult i : naturals) {
            if(i.isNaturalMax()) {
                m++;
            }
        }

        return m;
    }

    public int computeNaturalMaxPower() {
        int m = 1;
        for(NaturalResult i : naturals) {
            if(i.isNaturalMax()) {
                m*=i.getSides();
            }
        }

        return m;
    }

    public int computeNaturalMinPower() {
        int m = 1;
        for(NaturalResult i : naturals) {
            if(i.isNaturalMin()) {
                m*=i.getSides();
            }
        }

        return m;
    }

    public int countNaturalMins() {
        int m = 0;
        for(NaturalResult i : naturals) {
            if(i.isNaturalMin()) {
                m++;
            }
        }

        return m;
    }
}
