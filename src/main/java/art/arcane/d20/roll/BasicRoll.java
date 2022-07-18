package art.arcane.d20.roll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BasicRoll implements RollUnit {
    @Builder.Default
    private final int sides = 20;
    @Builder.Default
    private final int modifier = 0;
    @Builder.Default
    private final int count = 1;
    @Builder.Default
    private final int advantage = 0;

    public static BasicRoll decode(String roll)
    {
        int count = 1;
        int modifier = 0;
        int sides = 20;
        int advantage = 0;

        if (roll.startsWith("ADV")) {
            advantage = 1;
            roll = roll.substring(4);
        } else if (roll.startsWith("DSV") || roll.startsWith("DAV") || roll.startsWith("DIS")) {
            advantage = -1;
            roll = roll.substring(4);
        }

        if(roll.contains("-"))
        {
            StringBuilder cb = new StringBuilder();
            char last = ' ';

            for(char i : roll.toCharArray()) {
                if(i == '-') {
                    if(last != '+') {
                        cb.append('+');
                    }
                }

                last = i;
                cb.append(i);
            }

            roll = cb.toString();
        }

        String[] dc = roll.split("\\Qd\\E");

        if(dc.length == 2) {
            if(dc[0].isNotEmpty()) {
                count = Integer.parseInt(dc[0]);
            }

            if(dc[1].contains("+")) {
                dc[1] = dc[1].split("\\Q+\\E")[0];
            }

            sides = Integer.parseInt(dc[1]);
        }

        else {
            if(dc[0].contains("+")) {
                dc[0] = dc[0].split("\\Q+\\E")[0];
            }

            sides = Integer.parseInt(dc[0]);
        }

        if(roll.contains("+"))
        {
            String[] p = roll.replaceAll("\\Q \\E", "").split("\\Q+\\E");
            modifier = Integer.parseInt(p[1]);
        }

        return new BasicRoll(sides, modifier, count, advantage);
    }
}
