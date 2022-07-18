package art.arcane.d20.roll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class Roll implements Rollable {
    @Singular
    private final List<Rollable> rolls;

    public Roll() {
        this.rolls = new ArrayList<>();
    }

    public static Roll decode(String roll) {
        roll = roll.replaceAll("\\Q \\E", "");
        List<Rollable> rolls = new ArrayList<>();

        if(roll.contains("+")) {
            String[] comp = roll.replaceAll("\\Q \\E", "").split("\\Q+\\E");
            String buf = "";

            for(String i : comp)
            {
                if(i.contains("d")) {
                    if(buf.isNotEmpty()) {
                        rolls.add(BasicRoll.decode(buf));
                        buf = "";
                    }

                    buf += i;
                }

                else if(buf.isNotEmpty()) {
                    buf += "+" + i;
                }
            }

            if(buf.isNotEmpty()) {
                rolls.add(BasicRoll.decode(buf));
            }
        }

        else {
            rolls.add(BasicRoll.decode(roll));
        }

        return new Roll(rolls);
    }

    public Roll roll(BasicRoll.BasicRollBuilder b) {
        rolls.add(b.build());
        return this;
    }

    public static BasicRoll.BasicRollBuilder d(int f){
        return BasicRoll.builder().sides(f);
    }

    public static BasicRoll.BasicRollBuilder d20(){
        return d(20);
    }

    public static BasicRoll.BasicRollBuilder d10(){
        return d(10);
    }

    public static BasicRoll.BasicRollBuilder d8(){
        return d(8);
    }

    public static BasicRoll.BasicRollBuilder d2(){
        return d(2);
    }

    public static BasicRoll.BasicRollBuilder d4(){
        return d(4);
    }

    public static BasicRoll.BasicRollBuilder d6(){
        return d(6);
    }

    public static BasicRoll.BasicRollBuilder d100(){
        return d(100);
    }

    public RollResult roll() {
        RollResult buf = new RollResult(List.of(), 0);

        for(Rollable i : rolls) {
            buf = buf.add(i.roll());
        }

        return buf;
    }

    @Override
    public String encode() {
        StringBuilder sb = new StringBuilder();

        for(Rollable i : rolls) {
            sb.append(" + ").append(i.encode());
        }

        return sb.substring(3);
    }

    @Override
    public int getMaxResult() {
        int m = 0;

        for(Rollable i : rolls) {
            m+= i.getMaxResult();
        }

        return m;
    }
}
